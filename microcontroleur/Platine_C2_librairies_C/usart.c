#include <p18f6722.h>
#include "lcd.h"
#include "usart.h"

char octet;					//contient le type d'instruction: il est lu lors du traitement du parametre

void envoyer_donnees_usart (char a_envoyer[],int longueur)		//on envoie le nombre d'octets à envoyer car avec strlen , ça ne marchait pas 
{
int i;
	
	for(i=0;i<longueur;i++)
	{	
		usart1_putc(a_envoyer[i]);
		lcd_putc(a_envoyer[i]);
	}
tempo_20ms;									//on attend entre chaque octet
}


void usart1_send_at(const char at_command[])
{
	int i = 0;
	
	do
	{
		usart1_putc(at_command[i]);
		lcd_putc(at_command[i]);
		i++;
	}while(at_command[i-1] != '\r');

	tempo_20ms;									//on attend entre chaque octet
}

void usart1_putc(char a_envoyer)
{
    while (!TXSTA1bits.TRMT);		//attente de la fin d'écriture
	TXREG1=a_envoyer;		
}



void init_usart (int spbrg)
{
	TXSTA1=CONFIG_TXSTA1;	//0b10101100; 	// high speed mode brgh1 , TXEN 1 autorise émission de donnée

	SPBRG1 = spbrg;

	RCSTA1 = 0;
	RCSTA1=CONFIG_RCSTA1;	// SPEN=1 valide le port série, CREN=1 valide la réception de donnée
}


void init_usar1_9600(void)
{
	init_usart(123);//123
//	init_usart(CONFIG_SPBRG1_9600);//123
}

void init_usar1_57600(void)
{
	init_usart(CONFIG_SPBRG1_57600);
}

