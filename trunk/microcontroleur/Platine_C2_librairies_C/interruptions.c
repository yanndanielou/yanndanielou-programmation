#include <p18f6722.h>

#include "interruptions.h"
#include "config_pic.h"
#include "modem_si2457.h"
#include "usart.h"

void interruptions_init (void)
{

}


void inter_tmr0(void)
{
	INIT_MODEM_int1s();
}


void inter_tmr2(void)
{
	PIR1bits.TMR2IF = 0;
	TMR2 = CHARGEMENT_TMR2;
	
//	if(f_PAS_DE_CABLE_PC == 0)
	if(1 == 0)
	{
		SHUTDOWN_MODEM();
	}
	RESET_MODEM_HARD_int10ms();
	
/*********************************
;***  Appel spécifique C2pass  ***
;*********************************/
//	GESTION_MT3271_int10ms();
//	GESTION_C2_PASS_int10ms();
}

//----------------------------------------------------------------------------
// High priority interrupt vector

#pragma code InterruptVectorHigh = 0x08
void
InterruptVectorHigh (void)
{
  _asm
    goto InterruptHandlerHigh //jump to interrupt routine
  _endasm
}

//----------------------------------------------------------------------------
// High priority interrupt routine

#pragma code
#pragma interrupt InterruptHandlerHigh

void InterruptHandlerHigh ()
{
//Désactivation des interruptions
	INTCONbits.GIE = 0;


//---------- TEST USART1 -------------------
	if(PIE1bits.RCIE)
	{
		//traitement interupt lecture usart 1
		if(PIR1bits.RC1IF == 1)
		{		
			 RECEPTION_USART1_intrc1();							//on réccupere l'octet envoyé
		}
		if(RCSTA1bits.OERR)
		{
			RCSTA1bits.CREN = 0;
			RCSTA1bits.CREN = 1;
		}
	}
//---------- FIN TEST USART1 ---------------


//------------ test USART2 --------------
	//RAF en C2PASS
//-----------fin usart2-------------------
	

//---------- TEST TIMER 0 -----------------
	if(	INTCONbits.TMR0IE == 1 &&
		INTCONbits.TMR0IF == 1)
	{
		//Toutes les 1 seconde
		INTCONbits.TMR0IF = 0;
		TMR0H = CHARGEMENT_TMR0H;
		TMR0L = CHARGEMENT_TMR0L;
		
		
		//C2PASS_TEMPO_int1s
		inter_tmr0();
	}
//---------- FIN TIMER 0 -----------------

//---------- TEST TIMER 2 -----------------
	if(	PIE1bits.TMR2IE == 1 &&
		PIR1bits.TMR2IF == 1)
	{
		inter_tmr2();	
	}
//------------- FIN TIMER 2 ---------------


//---------- TEST TIMER 4 -----------------
	if(PIR3bits.TMR4IF == 1)
	{
		TMR4 = CHARGEMENT_TMR4;
		PIR3bits.TMR4IF = 0;
	}
//------------- FIN TIMER 4 ---------------

//Réactivation des interruptions
	INTCONbits.GIE = 1;

}




//----------------------------------------------------------------------------
// Low priority interrupt vector

#pragma code InterruptVectorLow = 0x18
void
InterruptVectorLow (void)
{
  _asm
    goto InterruptHandlerLow //jump to interrupt routine
  _endasm
}

//----------------------------------------------------------------------------
// Low priority interrupt routine

#pragma code
#pragma interrupt InterruptHandlerLow

void InterruptHandlerLow ()
{
	int pause;
	pause = 1;
}
