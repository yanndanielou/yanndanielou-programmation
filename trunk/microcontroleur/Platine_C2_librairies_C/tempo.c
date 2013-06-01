#include "tempo.h"
#include <p18f6722.h>


void tempo_100ms(void)
{
	tempo_20ms();
	tempo_20ms();
	tempo_20ms();
	tempo_20ms();
	tempo_20ms();
}

void tempo_1ms(void)
{
	// A 20 Mhz, 5 000 cycles = 1ms
	Delay1KTCYx(5);
}

void tempo_20ms(void)
{
	// A 20 Mhz, 100 000 cycles = 20ms
	Delay10KTCYx(10);
	return;
}