#include <p18f6722.h>
#include "lcd.h"
#include "usart.h"
#include "modem_si2457.h"
#include "interruptions.h"

//Configuration bits
#pragma	CONFIG OSC = HS
#pragma	CONFIG FCMEN = OFF
#pragma	CONFIG IESO = OFF
#pragma	CONFIG PWRT = OFF
#pragma	CONFIG BOREN = SBORDIS//3d7 OFF problème d'afficheur 
#pragma	CONFIG BORV = 0
#pragma	CONFIG WDT = OFF
#pragma	CONFIG WDTPS = 1024
#pragma	CONFIG MCLRE = ON
#pragma	CONFIG LPT1OSC = OFF
#pragma	CONFIG CCP2MX = PORTC//3b7 commentaire mis ;commentaire enlevé
#pragma	CONFIG STVREN = OFF
#pragma	CONFIG LVP = OFF
#pragma	CONFIG BBSIZ = BB2K
#pragma	CONFIG XINST = OFF
#pragma	CONFIG DEBUG = ON
#pragma	CONFIG CP0 = OFF
#pragma	CONFIG CP1 = OFF
#pragma	CONFIG CP2 = OFF
#pragma	CONFIG CP3 = OFF
#pragma	CONFIG CP4 = OFF
#pragma	CONFIG CP5 = OFF
#pragma	CONFIG CP6 = OFF
#pragma	CONFIG CP7 = OFF
#pragma	CONFIG CPB = OFF
#pragma	CONFIG CPD = OFF
#pragma	CONFIG WRT0 = OFF
#pragma	CONFIG WRT1 = OFF
#pragma	CONFIG WRT2 = OFF
#pragma	CONFIG WRT3 = OFF
#pragma	CONFIG WRT4 = OFF
#pragma	CONFIG WRT5 = OFF
#pragma	CONFIG WRT6 = OFF
#pragma	CONFIG WRT7 = OFF
#pragma	CONFIG WRTB = OFF
#pragma	CONFIG WRTC = OFF
#pragma	CONFIG WRTD = OFF
#pragma	CONFIG EBTR0 = OFF
#pragma	CONFIG EBTR1 = OFF
#pragma	CONFIG EBTR2 = OFF
#pragma	CONFIG EBTR3 = OFF
#pragma	CONFIG EBTR4 = OFF
#pragma	CONFIG EBTR5 = OFF
#pragma	CONFIG EBTR6 = OFF
#pragma	CONFIG EBTR7 = OFF
#pragma	CONFIG EBTRB = OFF




void main (void);
void init_registres(void);



void main ()
{
const char AT_INIT[] = "ATE0V0S0=0X0\\V2+GCI=3D\r";


init_registres();

lcd_init();
init_usar1_9600();
lcd_gotoxy(1,1);
//lcd_putrs("ok");

//Delay10KTCYx(200);

usart1_send_at(AT_INIT);

	while(1)
	{
		Delay10KTCYx(200);
	}
	 
}




void init_registres(void)
{
	PORTA = 0;
	PORTB = 0;
	PORTC = 0;
	PORTD = 0;
	PORTE = 0;
	PORTF = 0;
	PORTG = 0;

	CMCON = CONFIGURE_CMCON;
	ADCON0 = CONFIGURE_ADCON0;
	ADCON1 = CONFIGURE_ADCON1;

	TRISA=CONFIGURE_PORTA;	
	TRISB=CONFIGURE_PORTB;					//initialise les E/S
	TRISC=CONFIGURE_PORTC;			//initialise les E/S
	TRISD=CONFIGURE_PORTD;		//initialise les E/S
	TRISE=CONFIGURE_PORTE;
	TRISF=CONFIGURE_PORTF;
	TRISG=CONFIGURE_PORTG;
	
	//CONFIG INTERRUPTION
	RCONbits.IPEN=0;					//enable les priorités sur les interruptions
	INTCON = INTERRUPTIONS_INTCON;
	INTCON2 = INTERRUPTIONS_INTCON2;

	PIE1=INTERRUPTIONS_PIE1;
	PIE2=INTERRUPTIONS_PIE2;
	PIE3=INTERRUPTIONS_PIE3;


//timers
	//timer0
	T0CON =	CONFIGURATION_T0CON;
	TMR0H =	CHARGEMENT_TMR0H;
	TMR0L =	CHARGEMENT_TMR0L;

	// timer2
	T2CON = CONFIGURATION_T2CON;
	TMR2 = CHARGEMENT_TMR2;

	//timer 3
	T3CON = 0;

	// timer 4
	T4CON =	CONFIGURATION_T4CON;
	TMR4 = 	CHARGEMENT_TMR4;
}

