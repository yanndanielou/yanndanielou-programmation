#ifndef __USART_H_
#define __USART_H_ 1

#include <p18f6722.h>

void envoyer_donnees_usart (char a_envoyer[],int longueur);
void usart1_putc(char a_envoyer);
void usart1_send_at(const char at_command[]);
void init_usart (int bprg);
void init_usar1_9600(void);
void init_usar1_57600(void);
void RECEPTION_USART1_intrc1(void);


#endif