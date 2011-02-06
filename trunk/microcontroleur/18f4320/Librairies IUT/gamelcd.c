#include "gamelcd.h"
#include <delays.h>
#include <stdlib.h>
///////////////////////////////////////////////////////////////////////////////////
//                                                       	                     //
// sources pour l'utilisation de l'afficheur LCD cablé sur le port D de la carte //
// microcontroleur gamel_trophy                                                  //
//                          version 1.0 octobre 04                               //
//                                                                               //
// fonctions disponibles :                                                       //
//                                                                               //
//         lcd_init(void)              initialise l'afficheur                    //
//                                                                               //
//         lcd_gotoxy(char x, char y)  positionne le curseur                     //
//                                     (1,1) est le coin haut gauche             //
//                                     x correspond à la ligne, y à la colonne   //
//                                                                               //
//         lcd_puti(int nombre)        affiche un nombre entier                  //
//                                                                               //
//         lcd_putc(char lettre)       affiche un caractere y compris les speciaux/
//                                                                               //
//         lcd_puts(char* message)     affiche une chaine de caracteres y compris//
//                                     les speciaux                              //
//                                                                               //
//  caracteres speciaux disponibles                                              //
//  \n pour passer à la ligne   \f pour effacer  \b pour reculer d'une case      //
//                                                                               //
//                                                                               //
// connection : Microcontroleur	  -  LCD                                         //
//                          D0    -  LCD_E clock pin                             //
//                          D1    -  LCD_RS register select                      //
//                          D2    -  LCD_RW read/write                           //
//                          D3    -  non connectée                               //
//                          D4-D7 -  D4-D7 dataport                              //
//                                                                               //
//                                                                               //
///////////////////////////////////////////////////////////////////////////////////

#define LCD_TYPE 0x28

/* Port de donnée de l'afficheur LCD (4 bits) */
#define DATA_PORT      PORTD // données sur le port D
#define TRIS_DATA_PORT TRISD // écriture TRISD=0x00   - lecture TRISD=0xF0

/* Bits de controle de l'afficheur LCD*/
#define RW_PIN   PORTDbits.RD2   // bit LCD_RW 
#define RS_PIN   PORTDbits.RD1   // bit LCD_RS 
#define E_PIN    PORTDbits.RD0   // bit LCD_E  

/********************************************************************
*       Function Name:  lcd_init()                                  *
*       Return Value:   void                                        *
*       Parameters:     void                                        *
*       Description:    Basée sur le coontroleur d'afficheur Hitachi*
*                       HD44780 LCD, cette fonction initialise      *
*                       l'écran LCD                                 *
********************************************************************/
void lcd_init()
{
        
	PORTD = 0;		// tous les signaux à 0
	TRISD = 0xF0;	   	// signaux de contrôle en sortie et signaux de données en entrées

	DelayPORXLCD();		// Delay for 15ms to allow for LCD Power on reset
        
        
        // Setup interface to LCD

        TRIS_DATA_PORT &= 0x0f;		// Port données en écriture
        DATA_PORT &= 0x0f;		// Masque pour effacer le port de données
        DATA_PORT |= 0b00110000;        // Masque pour écrire 0011 (cmd) sur le port de données
        E_PIN = 1;                      // Clock the cmd in - Validation de l'écriture de cmd
        DelayFor18TCY();
        E_PIN = 0;
        
        // Delay for at least 4.1ms
        DelayXLCD();


        // Setup interface to LCD

        DATA_PORT &= 0x0f;              // On recommence à écrire cmd
        DATA_PORT |= 0b00110000;	//
        E_PIN = 1;                      // Clock the cmd in
        DelayFor18TCY();
        E_PIN = 0;

        // Delay for at least 100us
        DelayXLCD();

        // Setup interface to LCD

        DATA_PORT &= 0x0f;              // Function set cmd
        DATA_PORT |= 0b00110000;
        E_PIN = 1;                      // Clock cmd in
        DelayFor18TCY();
        E_PIN = 0;

        DelayXLCD();

	// Setup interface to LCD

        DATA_PORT &= 0x0f;              // Function set cmd(4-bit interface)
        DATA_PORT |= 0b00100000;
        E_PIN = 1;                      // Clock cmd in
        DelayFor18TCY();
        E_PIN = 0;

        TRIS_DATA_PORT |= 0xf0;         // Port de données en entrée


        // Set data interface width, # lines, font
        while(BusyXLCD());              // Wait if LCD busy
        WriteCmdXLCD(LCD_TYPE);          // Function set cmd

        // Turn the display on then off
        while(BusyXLCD());              // Wait if LCD busy
        WriteCmdXLCD(DOFF&CURSOR_OFF&BLINK_OFF);        // Display OFF/Blink OFF
        while(BusyXLCD());              // Wait if LCD busy
        WriteCmdXLCD(DON&CURSOR_ON&BLINK_ON);           // Display ON/Blink ON

        // Clear display
        while(BusyXLCD());              // Wait if LCD busy
        WriteCmdXLCD(0x01);             // Clear display

        // Set entry mode inc, no shift
        while(BusyXLCD());              // Wait if LCD busy
        WriteCmdXLCD(SHIFT_CUR_LEFT);   // Entry Mode

        // Set DD Ram address to 0
        while(BusyXLCD());              // Wait if LCD busy
        lcd_gotoxy(1,1);                // Set Display data ram address to 0

        return;
}




/********************************************************************
*       Function Name:  lcd_gotoxy(char x, char y)                  *
*       Return Value:   void                                        *
*       Parameters:     char x pour la ligne et char y pour colonne *
*       Description:    Permet de placer le curseur ou on le veut   * 
*                       sur l'afficheur LCD                         *
********************************************************************/
void lcd_gotoxy(char x, char y)
{
   char address;

   if(x!=1)
     address=0x40; //ligne 2
   else
     address=0;

   address=address+y-1;
   WriteCmdXLCD(0x80|address);
}

/********************************************************************
*       Function Name:  lcd_puti(int nombre)                        *
*       Return Value:   void                                        *
*       Parameters:     nombre: nombre à afficher sur le LCD        *
*       Description:    Cette fonction affiche la valeur d'un entier*
********************************************************************/
void lcd_puti(int nombre)
{
        char string[7]; //chaine de caractere pour stocker les caracteres correspondant a l'entier
        itoa(nombre,string); //conversion du int en string
        lcd_puts(string);
}

/********************************************************************
*       Function Name:  lcd_putc(char lettre)                        *
*       Return Value:   void                                        *
*       Parameters:     lettre: caractère à afficher sur le LCD     *
*       Description:    Cette fonction reprend WriteDataXLCD mais en*
*                       plus gère les caractère spéciaux \n \f \b   *
********************************************************************/
void lcd_putc(char lettre)
{
        while(BusyXLCD());
        switch(lettre){
            case '\f'   : WriteCmdXLCD(0x01);  
                          DelayXLCD();     
                          break; // effacement de l'écran
            case '\n'   : lcd_gotoxy(2,1);        
                          break;               // passage à la ligne
            case '\b'   : WriteCmdXLCD(0x10);  
                          break;                  // recule d'une position
            default     : WriteDataXLCD(lettre);
                          break;                 // écrit la lettre
   }	
}

/********************************************************************
*       Function Name:  lcd_puts issue de putsXLCD                  *
*       Return Value:   void                                        *
*       Parameters:     pointer to string                           *
*       Description:    Ecrit une chaine de caractère à partir d'un *
                        pointeur vers char, à la suite du dernier   *
                        caractère écrit. Prend en compte \n \f \b   *
********************************************************************/
void lcd_puts(char *buffer)
{
        while(*buffer)                  // Write data to LCD up to null
        {
                while(BusyXLCD());      // Wait while LCD is busy
                lcd_putc(*buffer); // Write character to LCD
                buffer++;               // Increment buffer
        }
        return;
}

////////////////////////////////////////////////////////////////////////
// Fonctions internes a la librairie      
////////////////////////////////////////////////////////////////////////

/********************************************************************
*       Function Name:  WriteCmdXLCD                                *
*       Return Value:   void                                        *
*       Parameters:     cmd: command to send to LCD                 *
*       Description:    This routine writes a command to the Hitachi*
*                       HD44780 LCD controller. The user must check *
*                       to see if the LCD controller is busy before *
*                       calling this routine.                       *
********************************************************************/
void WriteCmdXLCD(unsigned char cmd)
{
        TRIS_DATA_PORT &= 0x0f;		// Port données en écriture
        DATA_PORT &= 0x0f;		// Efface le port données
        DATA_PORT |= cmd&0xf0;		// Inscrit les bits de poids fort de cmd sur le port données
        RW_PIN = 0;                     // Set control signals for command
        RS_PIN = 0;			
        DelayFor18TCY();
        E_PIN = 1;                      // Clock command in
        DelayFor18TCY();
        E_PIN = 0;
	
	// idem, pour les bits de poids faible

        DATA_PORT &= 0x0f;
        DATA_PORT |= (cmd<<4)&0xf0;
        DelayFor18TCY();
        E_PIN = 1;                      // Clock command in
        DelayFor18TCY();
        E_PIN = 0;
        TRIS_DATA_PORT |= 0xf0;
        return;
}

/********************************************************************
*       Function Name:  WriteDataXLCD                               *
*       Return Value:   void                                        *
*       Parameters:     data: data byte to be written to LCD        *
*       Description:    This routine writes a data byte to the      *
*                       Hitachi HD44780 LCD controller. The user    *
*                       must check to see if the LCD controller is  *
*                       busy before calling this routine. The data  *
*                       is written to the display data RAM          *
********************************************************************/
void WriteDataXLCD(char data)
{
        TRIS_DATA_PORT &= 0x0f;		// Port données en écriture
        DATA_PORT &= 0x0f;		// Efface le port donnée
        DATA_PORT |= data&0xf0;		// Inscrit les bits de fpoids fort de data
        RS_PIN = 1;                     // Set control bits
        RW_PIN = 0;
        DelayFor18TCY();
        E_PIN = 1;                      // Clock nibble into LCD
        DelayFor18TCY();
        E_PIN = 0;
	
	// idem, pour les bits de poids faible
        DATA_PORT &= 0x0f;
        DATA_PORT |= ((data<<4)&0xf0);
        DelayFor18TCY();
        E_PIN = 1;                      // Clock nibble into LCD
        DelayFor18TCY();
        E_PIN = 0;

        TRIS_DATA_PORT |= 0xf0;
        return;
}

/********************************************************************
*       Function Name:  BusyXLCD                                    *
*       Return Value:   char: busy status of LCD controller         *
*       Parameters:     void                                        *
*       Description:    This routine reads the busy status of the   *
*                       Hitachi HD44780 LCD controller.             *
********************************************************************/
unsigned char BusyXLCD(void)
{
        RW_PIN = 1;                     // Set the control bits for read
        RS_PIN = 0;
        DelayFor18TCY();
        E_PIN = 1;                      // Clock in the command
        DelayFor18TCY();

        if(DATA_PORT&0x80)
        {
                E_PIN = 0;              // Reset clock line
                DelayFor18TCY();
                E_PIN = 1;              // Clock out other nibble
                DelayFor18TCY();
                E_PIN = 0;
                RW_PIN = 0;             // Reset control line
                return 1;               // Return TRUE
        }
        else                            // Busy bit is low
        {
                E_PIN = 0;              // Reset clock line
                DelayFor18TCY();
                E_PIN = 1;              // Clock out other nibble
                DelayFor18TCY();
                E_PIN = 0;
                RW_PIN = 0;             // Reset control line
                return 0;               // Return FALSE
        }
}

/********************************************************************
*       Function Name:  DelayFor18TCY DelayPORXLCD DelayXLCD        *
*       Return Value:   void                                        *
*       Parameters:     void                                        *
*       Description:    Temporisations nécessaires pour les échanges*
*                       entre le microcontroleur et l'afficheur     *
********************************************************************/
void DelayFor18TCY(){
 Delay10TCYx(2);	
}

void DelayPORXLCD() //provides at least 15ms delay *
{ Delay10KTCYx(3);
}
void DelayXLCD() //provides at least 5ms delay
{ Delay10KTCYx(1);
}

