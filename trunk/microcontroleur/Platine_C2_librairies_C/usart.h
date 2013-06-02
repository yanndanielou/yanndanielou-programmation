#ifndef __USART_H_
#define __USART_H_ 1

#include <p18f6722.h>


/***************************************
;***  Mappage du detecteur de cable  ***
;***************************************
; 0 : le cable PC est présent dans le connecteur
; 1 : pas de cable PC connecté
*/
#define	DETECT_CABLE	PORTF,0	

#define CONFIG_RCSTA1			0b10010000
#define CONFIG_TXSTA1			0b10100100
#define CONFIG_SPBRG1_57600		21
#define CONFIG_SPBRG1_9600		129


#define LEN_BUFFER	200


#ifdef VAR_GLOBALES_USART
	unsigned char BUFFER_USART1[LEN_BUFFER];
	unsigned char NB_BYTE_BUFFER_USART1 = 0;
#else
	extern unsigned char BUFFER_USART1[LEN_BUFFER];
	extern unsigned char NB_BYTE_BUFFER_USART1;
#endif


void envoyer_donnees_usart (char a_envoyer[],int longueur);
void usart1_putc(char a_envoyer);
void usart1_send_at(const char at_command[]);
void init_usart (int bprg);
void init_usar1_9600(void);
void init_usar1_57600(void);
void RECEPTION_USART1_intrc1(void);


#endif