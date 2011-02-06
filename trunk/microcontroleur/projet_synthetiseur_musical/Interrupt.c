#include "interrupt.h"

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

void
InterruptHandlerHigh ()
{
  if (INTCONbits.TMR0IF)
    {                
	  WriteTimer0(97);                   //check for TMR0 overflow

	  anti_rebond_BP0();
	  anti_rebond_BP1();
	  anti_rebond_BP2();


      INTCONbits.TMR0IF = 0;            //clear interrupt flag
    }
}
