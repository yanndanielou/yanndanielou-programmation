#include <p18f4320.h>
///////////////////////////////////////////////////////////////////////////////////
//                                                                               //
// declarations des fonctions pour l'utilisation de l'afficheur LCD cablé sur le // 
// port D de la carte microcontroleur gamel_trophy                               //
//                          version 1.0 octobre 04                               //
//                                                                               //
///////////////////////////////////////////////////////////////////////////////////


/* Display ON/OFF Control defines */
#define DON         0b00001111  /* Display on      */
#define DOFF        0b00001011  /* Display off     */
#define CURSOR_ON   0b00001111  /* Cursor on       */
#define CURSOR_OFF  0b00001101  /* Cursor off      */
#define BLINK_ON    0b00001111  /* Cursor Blink    */
#define BLINK_OFF   0b00001110  /* Cursor No Blink */

/* Cursor or Display Shift defines */
#define SHIFT_CUR_LEFT    0b00010011  /* Cursor shifts to the left   */
#define SHIFT_CUR_RIGHT   0b00010111  /* Cursor shifts to the right  */
#define SHIFT_DISP_LEFT   0b00011011  /* Display shifts to the left  */
#define SHIFT_DISP_RIGHT  0b00011111  /* Display shifts to the right */

#define PARAM_SCLASS auto
#define MEM_MODEL far  /* Change this to near for small memory model */

/* lcd_init() Initialisation de l'afficheur LCD */
void lcd_init(void);

/* lcd_gotoxy(char x, char y) va à la case x,y */
void lcd_gotoxy(char x, char y);

/* lcd_puti(int nombre) : ecriture d'un entier */
void lcd_puti(int nombre);

/* lcd_putc(char lettre) : écriture d'un caractère*/
void lcd_putc(char lettre);

/* lcd_puts Writes a string of characters to the LCD */
void lcd_puts(char *message);

/* lcd_puts Writes a string of characters from program memory to the LCD */
void lcd_putrs(const rom char *message);

/* WriteCmdXLCD Writes a command to the LCD */
void WriteCmdXLCD(PARAM_SCLASS unsigned char);

/* WriteDataXLCD Writes a data byte to the LCD */
void WriteDataXLCD(PARAM_SCLASS char);

/* BusyXLCD Returns the busy status of the LCD */
unsigned char BusyXLCD(void);

/* routines according to the oscillator frequency */
void DelayFor18TCY(void);
void DelayPORXLCD(void);
void DelayXLCD(void);
