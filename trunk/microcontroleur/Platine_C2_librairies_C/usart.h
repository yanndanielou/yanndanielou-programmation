#ifndef __USART_H_
#define __USART_H_ 1

#include <p18f6722.h>

#define CONFIGURE_ADCON0		0b00000000
#define CONFIGURE_ADCON1		0b00001111
#define CONFIGURE_CMCON			0x07

#define CONFIG_RCSTA1			0b10010000
#define CONFIG_TXSTA1			0b10100100
#define CONFIG_SPBRG1_57600		21
#define CONFIG_SPBRG1_9600		129

#define INTERRUPTIONS_INTCON	0b11100000	//0b01100000		//Active Interruption général INTCON REGISTER, TMR2 ACTIVE
#define INTERRUPTIONS_INTCON2	0b00000000		//INTCON2 REGISTER
#define INTERRUPTIONS_PIE1		0b00100010		//INTERRUPTION SUR RECEPTION DE CARACTERE DE l'UART1
#define INTERRUPTIONS_PIE2		0b00000000
#define INTERRUPTIONS_PIE3		0b00000000


#define CONFIGURE_PORTA			0b00011000
#define CONFIGURE_PORTB			0b01001111
#define CONFIGURE_PORTC			0b10010010
#define CONFIGURE_PORTD			0b00000000
#define CONFIGURE_PORTE			0b11111111	// mis a jour par la fonction int_clavier
#define CONFIGURE_PORTF			0b01010001
#define CONFIGURE_PORTG			0b00010110


/****************
;***  TIMERS  ***
;****************/

#define CONFIGURATION_T0CON			0b10000110
#define CHARGEMENT_TMR0H			0x67	// HIGH 26473
#define CHARGEMENT_TMR0L			0x69	//LOW	26473

#define CONFIGURATION_T2CON			0b01100110
#define CHARGEMENT_TMR2				16

#define CONFIGURATION_T4CON			0b00000110
#define CHARGEMENT_TMR4				(0xFF - 160)


void InterruptHandlerHigh (void);

void envoyer_donnees_usart (char a_envoyer[],int longueur);
void usart1_putc(char a_envoyer);
void usart1_send_at(const char at_command[]);
void init_usart (int bprg);
void init_usar1_9600(void);
void init_usar1_57600(void);


#endif