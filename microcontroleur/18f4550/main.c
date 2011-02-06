#include <p18f4550.h>
#include "gamelinit_v3.h"
#include "gamelcd_v3.h"
#include <delays.h>


void main(void)
{
//OSCCON = OSCCON|0x70;
TRISA = 0xBF;
TRISB = 0xFF;

lcd_init();

lcd_gotoyx(1,1);
lcd_putrs("Bonjour!");

while(1)
{


if(PORTBbits.RB5==0)
{
	PORTAbits.RA6 =0;
	Delay10KTCYx(100);
	Delay10KTCYx(100);
	PORTAbits.RA6 =1;
	Delay10KTCYx(100);
	Delay10KTCYx(100);
}


} //end while
} //end main

