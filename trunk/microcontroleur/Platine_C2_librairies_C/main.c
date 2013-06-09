#include <p18f6722.h>
#include "lcd.h"
#include "usart.h"
#include "modem_si2457.h"
#include "interruptions.h"
#include "config_pic.h"
#include "gestion_evenements.h"
#include "c2_pass.h"

//#include	"p18f6722.inc"

//CONFIGUration bits

#pragma	config OSC = HS
#pragma	config FCMEN = OFF
#pragma	config IESO = OFF
#pragma	config PWRT = OFF
#pragma	config BOREN = SBORDIS//3d7 OFF problème d'afficheur 
#pragma	config BORV = 0
#pragma	config WDT = OFF
#pragma	config WDTPS = 1024
#pragma	config MCLRE = ON
#pragma	config LPT1OSC = OFF
#pragma	config CCP2MX = PORTC//3b7 commentaire mis ;commentaire enlevé
#pragma	config STVREN = OFF
#pragma	config LVP = OFF
#pragma	config BBSIZ = BB2K
#pragma	config XINST = OFF
#pragma	config DEBUG = ON
#pragma	config CP0 = OFF
#pragma	config CP1 = OFF
#pragma	config CP2 = OFF
#pragma	config CP3 = OFF
#pragma	config CP4 = OFF
#pragma	config CP5 = OFF
#pragma	config CP6 = OFF
#pragma	config CP7 = OFF
#pragma	config CPB = OFF
#pragma	config CPD = OFF
#pragma	config WRT0 = OFF
#pragma	config WRT1 = OFF
#pragma	config WRT2 = OFF
#pragma	config WRT3 = OFF
#pragma	config WRT4 = OFF
#pragma	config WRT5 = OFF
#pragma	config WRT6 = OFF
#pragma	config WRT7 = OFF
#pragma	config WRTB = OFF
#pragma	config WRTC = OFF
#pragma	config WRTD = OFF
#pragma	config EBTR0 = OFF
#pragma	config EBTR1 = OFF
#pragma	config EBTR2 = OFF
#pragma	config EBTR3 = OFF
#pragma	config EBTR4 = OFF
#pragma	config EBTR5 = OFF
#pragma	config EBTR6 = OFF
#pragma	config EBTR7 = OFF
#pragma	config EBTRB = OFF


void main (void);
void init_registres(void);
void init(void);


void main ()
{
	const char AT_INIT[] = "ATE0V0S0=0X0\\V2+GCI=3D\r";

	init();
	
	//lcd_putrs("ok");

	//Delay10KTCYx(200);

	//usart1_send_at(AT_INIT);

	while(1)
	{
		
		/*********************************************
		;***  Initialisation du modem en Identitel ***
		;*********************************************/
		if(	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET_HARD_SYNC 	== 0	&&
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_ATA_ENCOURS 		== 0	&&
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_CONNECTE 		== 0	&&
			FLAG_MODEM_SI2457Bits.Bits.f_RING_EN_COURS			== 0	&&
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_INIT				== 1	&&
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET			== 0)
		{
			INIT_MODEM_IDCALLER();
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_INIT = 0;
		}
			
		/****************************************
		;***  Phase de synchronisation modem  ***
		;****************************************/
		// phase de synchro = reset + reinitialisation du modem + envoie d'"ATA\r"

		if(	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET_HARD_SYNC 	== 1	&&	//Est on en phase de synchro ?
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET			== 0)		//Est ce que le RESET hard est terminé ?
		{
			INIT_MODEM_SYNCHRO();
		}
		
		
		/*******************************************
		;*** Ouverture des porte via Identitel  ***
		;*******************************************/
		AUTORISATION_IDENTITEL(); //maitre

		if(	FLAG_MODEM_SI2457_3Bits.Bits.f_CHECK_NB_RING 	== 1)
		{
			DECRO_RACRO_MAX_SONNERIE();
		}

		
		Delay10KTCYx(200);
	}
	 
}



//Point de démarrage
void init(void)
{
	//  Initialisation de toutes la RAM (GPRs, pas SFR) à zéro

	//	Mémorisation de RCON,T0 (car éffacé au permier clrwdt)
	
	//	Allumer le Watchdog
	
	//Initialisation des registres
	init_registres();
	
	//Initialisation uart pour modem
	init_usar1_9600();
	
	//
	gestionEvenementsInit();
	
	//Initialisation des TIMER
	//init_timer(); : déja fait dans init_registres
	
	//Initialisation de l'ecran LCD
	lcd_init();
	lcd_gotoxy(1,1);
	
	// Initialisation de l'EEPROM
	//avec les données portes
	
	//Initialisation de la mémoire Flash
	
	//Initialisation Horloge Temps Reel
	
	//Détection du modem
	DETECT_MODEM();	
	
	//Initialisation pour C2pass
	PRISE_DE_LIGNE = 0;
	MICRO = 0;
	HAUT_PARLEUR;
	
	//Initialisation du type de produit
	
	//affichage message initialisation
	//nombre de fiches
	
	// Affichage Date et Heure
	
	//Autorisation des interruptions
	ENABLE_INTERRUPTS;
	
}






void init_registres(void)
{
	PORTA = 0;
	PORTB = 0;
	PORTC = 0;
	PORTD = 0;
	PORTE = 0;
	PORTF = 0;
	PORTG = 0;

	CMCON = CONFIGURE_CMCON;
	ADCON0 = CONFIGURE_ADCON0;
	ADCON1 = CONFIGURE_ADCON1;

	TRISA=CONFIGURE_PORTA;	
	TRISB=CONFIGURE_PORTB;					//initialise les E/S
	TRISC=CONFIGURE_PORTC;			//initialise les E/S
	TRISD=CONFIGURE_PORTD;		//initialise les E/S
	TRISE=CONFIGURE_PORTE;
	TRISF=CONFIGURE_PORTF;
	TRISG=CONFIGURE_PORTG;
	
	//config INTERRUPTION
	RCONbits.IPEN=0;					//enable les priorités sur les interruptions
	INTCON = INTERRUPTIONS_INTCON;
	INTCON2 = INTERRUPTIONS_INTCON2;

	PIE1=INTERRUPTIONS_PIE1;
	PIE2=INTERRUPTIONS_PIE2;
	PIE3=INTERRUPTIONS_PIE3;


//timers
	//timer0
	T0CON =	CONFIGURATION_T0CON;
	TMR0H =	CHARGEMENT_TMR0H;
	TMR0L =	CHARGEMENT_TMR0L;

	// timer2
	T2CON = CONFIGURATION_T2CON;
	TMR2 = CHARGEMENT_TMR2;

	//timer 3
	T3CON = 0;

	// timer 4
	T4CON =	CONFIGURATION_T4CON;
	TMR4 = 	CHARGEMENT_TMR4;
}

