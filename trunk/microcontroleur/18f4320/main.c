#include <p18f4320.h>
#include "gamelinit.h"


#define BP0  PORTBbits.RB3
#define BP1  PORTBbits.RB4
#define BP2  PORTBbits.RB5



void main (void)
{
OSCCON = OSCCON|0x70;
TRISA=0xbf;
TRISB=0xff;

while(1)
PORTAbits.RA6=!PORTBbits.RB3;

}

