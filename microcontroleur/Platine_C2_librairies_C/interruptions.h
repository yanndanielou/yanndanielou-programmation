#ifndef __INTERRUPTIONS_H_
#define __INTERRUPTIONS_H_ 1


void interrupts_enable (void);
void interrupts_disable (void);

void InterruptHandlerHigh (void);
void InterruptHandlerLow (void);

#endif