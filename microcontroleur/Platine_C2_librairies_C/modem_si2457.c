#define VAR_GLOBALES_MODEM_SI2457
#include "modem_si2457.h"
#include "tempo.h"
#include "c2_pass.h"

unsigned char TIMER_INIT_MODEM = 0;
unsigned char TIMER_RESET_MODEM = 0;	// pour générer le reset sur la broche RESET_SI2457
unsigned char TIMER_CONNEXION_MODEM = 0;



void modem_si2457_init (void)
{

}

void f_PAS_DE_CABLE_PC(void)
{
	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET = 1;	//Ce flag annonce que le modem effecue un RESET
	
	RESET_SI2457 = 0; //Etat bas 3b7
	
	TIMER_RESET_MODEM = 55;
}

void RESET_MODEM_HARD_int10ms(void)
{
	// Est-ce qu'on doit gérer la broche RESET du modem ?
	if(FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET == 1)
	{
		TIMER_RESET_MODEM--;
		
		//Attendre 50ms , RESET_SI2457 = 0
		if(TIMER_RESET_MODEM < 50)
		{
			//200ms écoulé,
			//On passe RESET_SI2457 à l'état haut, redémarrage du MODEM
			RESET_SI2457 = 1; //3b7
		
			//Attendre 500 ms restante, pour son init, la ligne est toujours décroché pour éviter les "RING"
			if(TIMER_RESET_MODEM == 0)
			{
				//Le modem à terminé son RESET
				FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET = 0;
			}
		}
	}
}

void RESET_MODEM_HARD_noint(void)
{
	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET = 1;
	PRISE_DE_LIGNE = 1;	//décrochage, pour eviter les "RING" sur le bus
	RESET_SI2457 = 0;	//3b7

	//Attendre environ 200 ms
	tempo_100ms();
	tempo_100ms();
	
	RESET_SI2457 = 1; //3B7
	
	//Attendre 500 ms
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	tempo_100ms();
	
	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET = 0;
	PRISE_DE_LIGNE = 1;	//raccrochage. FIXME: devrait être 0 (bug dans asm)
}

void INIT_MODEM_int1s(void)
{
	//appeler toutes les secondes
	//INIT_MODEM_int1s
	if(FLAG_MODEM_SI2457Bits.Bits.f_MODEM_CONNECTE == 0)
	{
		//no_conect
		if(TIMER_INIT_MODEM > 0)
			TIMER_INIT_MODEM--;
			
		if(TIMER_INIT_MODEM == 0)
		{
			/* Est ce qu'on doit faire une INIT ?
			oui si :
			-  un modem à été detecté
				&& le cable PC n'est pas branché
				&& le modem n'est pas connecté
				&& on n'est pas en train de recevoir un appel
				&& le modem n'est pas dans la phase de reinitialisation pour une communication avec un modem distant
				&& le modem n'est en train de se synchroniser
			*/
			
			if(	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_DETECTE == 1			&&
			//	FLAG_MODEM_SI2457Bits.Bits.f_PAS_DE_CABLE_PC == 1 			&&
				FLAG_MODEM_SI2457Bits.Bits.f_MODEM_CONNECTE == 0			&&
				FLAG_MODEM_SI2457Bits.Bits.f_RING_EN_COURS == 0			&&
				FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET_HARD_SYNC == 0	&&
				FLAG_MODEM_SI2457Bits.Bits.f_MODEM_ATA_ENCOURS == 0)
			{
				FLAG_MODEM_SI2457Bits.Bits.f_MODEM_INIT = 1;
				reset_modem_hard();				
			}
			
		}
			
	}
	else if(TIMER_CONNEXION_MODEM == 1)
	{
		//timeout
		//reset
	}
	else
	{
		//no_timeout
		if(TIMER_CONNEXION_MODEM > 0)
			TIMER_CONNEXION_MODEM--;
	}
}


void SHUTDOWN_MODEM(void)
{
	RESET_SI2457 = 0; //3B7
	
	// RESET 2 secondes après avoir retirer le cable
	START_RESET_MODEM(2);
}

void START_RESET_MODEM(char seconds)
{
	TIMER_INIT_MODEM = seconds;
}

void reset_modem_hard(void)
{
	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET = 1;
	RESET_SI2457 = 0;
	TIMER_RESET_MODEM = 55;
}

