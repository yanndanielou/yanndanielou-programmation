#include "tempo.h"
#include <p18f6722.h>


void tempo_100ms(void)
{
	tempo_20ms;
	tempo_20ms;
	tempo_20ms;
	tempo_20ms;
	tempo_20ms;
}

void tempo_942ms(void)
{
	//900
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	
	//+ 40
	tempo_20ms;
	tempo_20ms;
	
	// + 2
	tempo_1ms;
	tempo_1ms;

}
