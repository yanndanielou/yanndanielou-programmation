#ifndef __INTERRUPTIONS_H_
#define __INTERRUPTIONS_H_ 1

//Macros
#define ENABLE_INTERRUPTS INTCONbits.GIE = 1
#define DISABLE_INTERRUPTS INTCONbits.GIE = 0


void interrupts_enable (void);
void interrupts_disable (void);

void InterruptHandlerHigh (void);
void InterruptHandlerLow (void);

#endif