#include	<p18f6722.h>
#include	<delays.h>
#include 	<stdlib.h>
#include <string.h>

#define	tempo_20ms	Delay10KTCYx(50)
#define lcd_RW	PORTDbits.RD5
#define lcd_E	PORTDbits.RD4
#define lcd_RS	PORTDbits.RD6

void lcd_init (void);
void lcd_putc(char);
void lcd_puts (char chaine[]);
void lcd_puti (int nombre);
void lcd_gotoxy(int x, int y);
void ecrire_quartet_sans_busy (int a,int b,int c, int d);
void ecrire_demi_instruction(char demi_instruction);
void ecrire_instruction (char instruction);
void attendre_busy_flag (void);


#define val_max	150

