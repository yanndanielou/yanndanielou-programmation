#ifndef __LCD_H_
#define __LCD_H_ 1

#include	<p18f6722.h>
#include 	<stdlib.h>
#include <string.h>

#define lcd_RW	PORTDbits.RD5
#define lcd_E	PORTDbits.RD4
#define lcd_RS	PORTDbits.RD6

#define LCD_LINE_SIZE 16

#define LCD_CLEAR_FIRST_LINE lcd_clear_line(1)
#define LCD_CLEAR_SECOND_LINE lcd_clear_line(1)

void lcd_init (void);
void lcd_putc(char);
void lcd_puts (const char chaine[]);
void lcd_putrs(const rom char *buffer);
void lcd_puti (int nombre);
void lcd_gotoxy(int x, int y);
void ecrire_quartet_sans_busy (int a,int b,int c, int d);
void ecrire_demi_instruction(char demi_instruction);
void ecrire_instruction (char instruction);
void attendre_busy_flag (void);
void lcd_clear_line(int line_nb);


// #define val_max	150

#endif