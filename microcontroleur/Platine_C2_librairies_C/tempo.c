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

// A vérifier
// TO CHECK
void tempo_20ms(void)
{
	char tempo1 = 0xFF;
	char tempo2;
	
	//loop_tempo3
	while(tempo1 > 0)
	{
		tempo2 = 0x81;

		//loop_tempo4
		while(tempo2 > 0)
		{
			tempo2--;			
		}
		tempo1--;	
	}
}