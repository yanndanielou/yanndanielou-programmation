#include "interruptions.h"
#include <p18f6722.h>

void interruptions_init (void)
{


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

char octet_usart_recu;
int pause;
pause = 1;

	//traitement interupt lecture usart 1
	if(PIR1bits.RC1IF == 1)
	{		
		  octet_usart_recu=RCREG1;							//on réccupere l'octet envoyé
	}
	
	
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
