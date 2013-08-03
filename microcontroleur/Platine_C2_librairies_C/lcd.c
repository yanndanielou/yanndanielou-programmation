#define VAR_GLOBALES_LCD
#include "lcd.h"
#include "tempo.h"
#include "modem_si2457.h"
/*
********************************************************************************************************
****    CE FICHIER A ETE CONCU POUR UNE AUTRE CARTE, CERTAINES FONCTIONS N'ONT PAS ETE VERIFIEES   *****
********************************************************************************************************
*/

void lcd_init (void)
{
	TRISD=0x00;				//Tout le PORTD en sortie


	//INITIALISATION lcd
	lcd_RS=0;				//indique qu'on écrit une instruction
	lcd_RW=0;				//indique que l'on écrit

	/*
	********************************************************************************************************
	********    On envoie les instructions succéssives nécessaires à l'initialisation du lcd    ************
	********    Pendant cette initialisation, le bit Busy Flag ne peut être lu,			    	************
	********    on fait donc des tempo de 20ms dans la fonction  ecrire_quartet_sans_busy		************
	********    Le LCD étant connécté pour marché en mode 4 bits, toutes les instructions   	************
	********    sont coupées en deux par l'envoi de deux quartets successifs					************
	********************************************************************************************************
	*/

	//mode_8bit
	ecrire_demi_instruction(0x03);

	//mode_8bit bis
	ecrire_demi_instruction(0x03);


	//mode_8bit	ter
	ecrire_demi_instruction(0x03);


	//mode 4 bits
	ecrire_demi_instruction(0x02);


	//deux lignes 1/2
	ecrire_instruction(0x28);
	//curseur
	ecrire_instruction(0x10);
	//effacer_ecran
	ecrire_instruction(0x01);
	//entry set
	ecrire_instruction(0x06);
	//display on
	ecrire_instruction(0x0f);				//fin de l'initialisation
}


void lcd_putc (char lettre)				//affichage d'un caractere
{
	lcd_RS=1;

	//premiere partie de l'octet
	attendre_busy_flag();
	lcd_E=1;

	PORTD=PORTD&0xf0;
	PORTD=PORTD|lettre>>4&0x0f;

	lcd_E=0;	//c'est le front descendant de E qui valide l'instruction ou la donnée

	//deuxieme partie de l'octet
	attendre_busy_flag();
	lcd_E=1;
	PORTD=PORTD&0xf0;
	PORTD=PORTD|lettre&0x0f;
	lcd_E=0;
	attendre_busy_flag();
	tempo_1ms;
}

void lcd_puts(const char chaine[])
{
int i;

	for(i=0;chaine[i] !='\0';++i)
	{
	//	Delay10KTCYx(200);
 		lcd_putc(chaine[i]);
	}
}

void lcd_putrs(const rom char *buffer)
{
	while(*buffer)                  // Write data to LCD up to null
	{
		lcd_putc(*buffer); // Write character to LCD
		buffer++;               // Increment buffer
	}
	return;
}

void lcd_puti(int nombre)
{
	char chaine[LCD_LINE_SIZE];
	itoa(nombre,chaine);						//pour afficher un int, il faut en fait afficher la chaine de caractere de ce nombre
	lcd_puts(chaine);							//ex pour 123 on affiche "123" <-- la chaine
}


void lcd_gotoxy(int x, int y)
{
	int adresse=0;

	lcd_RS=0;
	lcd_RW=0;

	if (y==2)
	 adresse = adresse + 0x40;			//la deuxieme ligne commence à l'adresse 0x40

	adresse=adresse + x -1;				// -1 car quand x=1 il doit commencer a l'adresse 0

	//premiere partie de l'octet
	attendre_busy_flag();

	lcd_E=1;

	PORTD=PORTD&0xf0;
	PORTD=PORTD|(((adresse|0x80)>>4)&0x0f);

	lcd_E=0;

	attendre_busy_flag();
	lcd_E=1;
	PORTD=PORTD&0xf0;
	PORTD=PORTD|adresse&0x0f;
	lcd_E=0;
	attendre_busy_flag();
}



void ecrire_demi_instruction (char demi_instruction)
{
	// On écrit que quatre bits
	tempo_20ms;
	demi_instruction=demi_instruction&0x0f;
	lcd_E=1;
	PORTD=PORTD&0xf0;
	PORTD=PORTD|demi_instruction;
	lcd_E=0;
}

void ecrire_instruction (char instruction)
{
	ecrire_demi_instruction(instruction>>4&0x0f);
	ecrire_demi_instruction(instruction&0x0f);
}


void attendre_busy_flag (void)
{
	int old_rs,old_rw;
	old_rs=lcd_RS;						//sauvegarde des valeurs de RS et RW
	old_rw=lcd_RW;

	lcd_RW=1;
	lcd_RS=0;
	//PORTDbits.RD3=0;
	TRISD = TRISD | 0b00001000;			//broche D7 en entrée pour lire BUSY FLAG
	lcd_RW=1;
	lcd_RS=0;
	while (!PORTD&0b00001000);			//on attend que BF soit à 1 donc pret a écrire

	TRISD=TRISD&0b11110111;				//on remet D7 en sortie

	lcd_RS=old_rs;						//on remet a LCD_RS et LCD_RW les valeurs d'avant la fonction
	lcd_RW=old_rw;
}


void lcd_clear_line(int line_nb)
{
	int i = 0;
	lcd_gotoxy(1,line_nb);
	
	for(i = 0; i < LCD_LINE_SIZE; ++i)
		lcd_putc(' ');
		
	lcd_gotoxy(1,line_nb);
}


void update_lcd_display()
{
	char i = 0;
	
	switch(premiereLigneLCD)
	{
		case RIEN_A_AFFICHER:
			break;
		case ECHO_RECU:
			premiereLigneLCD = RIEN_A_AFFICHER;
			break;
		case RESULT_CODE_RECU:
			premiereLigneLCD = RIEN_A_AFFICHER;
			break;
		case DATE_RECU:
			//ID_DATE_RECU
			LCD_CLEAR_FIRST_LINE;
			lcd_putrs("DATE RECUE");	
			premiereLigneLCD = RIEN_A_AFFICHER;
			break;
		case TIME_RECU:
			//ID_DATE_RECU
			LCD_CLEAR_FIRST_LINE;
			lcd_putrs("TIME RECUE");
			premiereLigneLCD = RIEN_A_AFFICHER;
			break;
		case NMBR_RECU:
			//ID_NMBR_RECU
			LCD_CLEAR_FIRST_LINE;
			for(i=0; i< CALLER_PHONE_NUMBER_LENGTH; ++i)
			{
				lcd_putc(CALLER_PHONE_NUMBER[i]);
			}
			premiereLigneLCD = RIEN_A_AFFICHER;				
			break;
		case NAME_RECU:
			//ID_NAME_RECU
			LCD_CLEAR_FIRST_LINE;
			lcd_putrs("NAME RECU");
			premiereLigneLCD = RIEN_A_AFFICHER;
			break;
		case MESG_RECU:
			//ID_MESG_RECU
			LCD_CLEAR_FIRST_LINE;
			lcd_putrs("MESG RECU");
			premiereLigneLCD = RIEN_A_AFFICHER;
			break;
		case CHAMP_INCONNU_RECU:
			i = 0;
			premiereLigneLCD = RIEN_A_AFFICHER;
			break;
	}
	
	
	
	switch(deuxiemeLigneLCD)
	{
		case LCD_RIEN_A_AFFICHER:
			break;
		case LCD_ANS_CONNECT:
			LCD_CLEAR_SECOND_LINE;
			lcd_putrs("ANS CONNECT");
			deuxiemeLigneLCD = RIEN_A_AFFICHER;
			break;
		case LCD_ANS_RING:
			LCD_CLEAR_SECOND_LINE;
			lcd_putrs("ANS RING");
			deuxiemeLigneLCD = RIEN_A_AFFICHER;
			break;
		case LCD_ANS_NO_CARRIER:
			LCD_CLEAR_SECOND_LINE;
			lcd_putrs("ANS NO CARRIER");
			deuxiemeLigneLCD = RIEN_A_AFFICHER;
			break;
		case LCD_ANS_CIDM:
			LCD_CLEAR_SECOND_LINE;
			lcd_putrs("ANS CIDM");
			deuxiemeLigneLCD = RIEN_A_AFFICHER;
			break;
	}
}