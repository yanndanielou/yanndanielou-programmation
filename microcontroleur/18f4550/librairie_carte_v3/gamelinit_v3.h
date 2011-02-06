/* hardware configuration of the microcontroller */

//clock configuration : external clock from quartz oscillator with PLL 
// - 96 MHz for USB Block
// - 48 MHz for CPU clock

#pragma config FOSC = ECPLLIO_EC
#pragma config CPUDIV = OSC1_PLL2
#pragma config USBDIV = 2
#pragma config PLLDIV = 5

//use of debugger
#pragma config DEBUG = ON

//no Watchdog, no low voltage programming
#pragma config WDT = OFF
#pragma config LVP = OFF
#pragma config VREGEN = ON

//for more informations see the file C18_Config_Settings_51537e.pdf

