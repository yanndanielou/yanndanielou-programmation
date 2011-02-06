/* hardware configuration of the microcontroller */
/*
//clock configuration : external clock from quartz oscillator with PLL 
// - 96 MHz for USB Block
// - 48 MHz for CPU clock

#pragma config FOSC = ECPLLIO_EC
#pragma config CPUDIV = OSC1_PLL2
#pragma config USBDIV = 2
#pragma config PLLDIV = 5

//use of debugger
#pragma config DEBUG = OFF

//no Watchdog, no low voltage programming
#pragma config WDT = OFF
#pragma config LVP = OFF
#pragma config VREGEN = ON

//PORTB<4:0> pins are configured as digital I/O on Reset
#pragma config PBADEN = OFF

//Boot Block Write Protection ON
#pragma config WRTB = ON
*/

#pragma code
//for more informations see the file C18_Config_Settings_51537e.pdf


extern void _startup (void);        // See c018i.c in your C18 compiler dir
#pragma code _RESET_INTERRUPT_VECTOR = 0x000800
void _reset (void)
{
    _asm goto _startup _endasm
}
#pragma code

#pragma code _HIGH_INTERRUPT_VECTOR = 0x000808
void _high_ISR (void)
{
    ;
}

#pragma code _LOW_INTERRUPT_VECTOR = 0x000818
void _low_ISR (void)
{
    ;
}
#pragma code
