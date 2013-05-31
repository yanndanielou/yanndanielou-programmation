#include <p18f6722.h>
#define VAR_GLOBALES_GESTION_EVENEMENTS
#include "gestion_evenements.h"



void gestionEvenementsInit(void)
{
	save_intBits.data = 0;
}

void ENTER_CRITICAL (void)
{
	save_intBits.Bits.SAVE_GIE = INTCONbits.GIE;
	INTCONbits.GIE = 0;
}

void EXIT_CRITICAL (void)
{
	INTCONbits.GIE = 0;
	if(save_intBits.Bits.SAVE_GIE == 1)
	{
		INTCONbits.GIE = 1;
	}
}

