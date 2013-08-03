#define VAR_GLOBALES_MODEM_SI2457
#include "modem_si2457.h"
#include "tempo.h"
#include "c2_pass.h"
#include "gestion_evenements.h"
#include "usart.h"
#include "lcd.h"

unsigned char TIMER_INIT_MODEM = 0;
unsigned char TIMER_RESET_MODEM = 0;	// pour générer le reset sur la broche RESET_SI2457
unsigned char TIMER_CONNEXION_MODEM = 0;
unsigned char NB_RING = 0;

const char AT_INIT[]="ATE0V0S0=0X0\\V2+GCI=3D\r";
const char AT_IDCALLER_ON[]="AT+VCDT=1\r";
const char AT_IDCALLER_FORMAT[]="AT+VCID=1\r";
const char AT_U67[]="AT:U67,0008\r";		//Parametre de la ligne
const char AT_ATA[]="ATA\r"	;				// Décrocher et synchroniser avec le modem distant
const char AT_FCLASS[]="AT+FCLASS=256\r";		// Prepares the modem for handling SMS calls.
const char AT_DT[]="ATDT;\r";


void modem_si2457_init (void)
{
	char i;
	
	FLAG_MODEM_SI2457Bits.data = 0;
	FLAG_MODEM_SI2457_3Bits.data = 0;
	
	for(i=0; i<CALLER_PHONE_NUMBER_LENGTH; ++i)
	{
		CALLER_PHONE_NUMBER[i] = 'x';
	}
	
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
		{
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
					RESET_MODEM_HARD();				
				}
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

void RESET_MODEM_HARD(void)
{
	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET = 1;
	RESET_SI2457 = 0;
	TIMER_RESET_MODEM = 55;
}

void INIT_MODEM_IDCALLER(void)
{
//	FLAG_MODEM_SI2457Bits.Bits.f_SMS_FRM_ENVOYE = 0;
//	FLAG_MODEM_SI2457Bits.Bits.f_SMS_ATDT_EN_COURS = 0;	
	
	
	//AT_INIT
	//On attend un echo puis un result code
	LCD_CLEAR_FIRST_LINE;
	lcd_putrs("AT_INIT");
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 0;
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 0;
	MODEM_RESULT_CODE_ATTENDU = 0;
	MODEM_ECHO_ATTENDU = 1;
	ENTER_CRITICAL();	
	usart1_send_at(AT_INIT);
	EXIT_CRITICAL();
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO == 0);
	
	MODEM_RESULT_CODE_ATTENDU = 1;
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE == 0);
	
	//AT_IDCALLER_ON
	//On attend un result code
//	LCD_CLEAR_FIRST_LINE;
//	lcd_putrs("AT_IDCALLER_ON");
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 0;
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 0;
	MODEM_RESULT_CODE_ATTENDU = 1;
	MODEM_ECHO_ATTENDU = 0;
	ENTER_CRITICAL();	
	usart1_send_at(AT_IDCALLER_ON);
	EXIT_CRITICAL();
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE == 0);

	//AT_IDCALLER_FORMAT
	//On attend un result code
//	LCD_CLEAR_FIRST_LINE;
//	lcd_putrs("AT_IDCALLER_FOR");
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 0;
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 0;
	MODEM_RESULT_CODE_ATTENDU = 1;
	MODEM_ECHO_ATTENDU = 0;
	ENTER_CRITICAL();	
	usart1_send_at(AT_IDCALLER_FORMAT);
	EXIT_CRITICAL();
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE == 0);

	
	//AT_FCLASS
	//On attend un result code
/*	LCD_CLEAR_FIRST_LINE;
	lcd_putrs("AT_FCLASS");
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 0;
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 0;
	MODEM_RESULT_CODE_ATTENDU = 1;
	MODEM_ECHO_ATTENDU = 0;
	ENTER_CRITICAL();	
	usart1_send_at(AT_FCLASS);
	EXIT_CRITICAL();
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE == 0);
*/
	
	
	//AT_DT
	//On attend un result code
/*	LCD_CLEAR_FIRST_LINE;
	lcd_putrs("AT_DT");
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 0;
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 0;
	MODEM_RESULT_CODE_ATTENDU = 1;
	MODEM_ECHO_ATTENDU = 0;
	ENTER_CRITICAL();	
	usart1_send_at(AT_DT);
	EXIT_CRITICAL();
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE == 0);

	*/
	
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 0;
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 0;
	
}

/***********************************
;***  Acces modem via Identitel  ***
;***********************************/
void acces_modem(void)
{
	DECROCHE_MODEM();
}

/*****************************************************
;***  Décrocher / Racrocher au bout de x sonnerie  ***
;****************************************************/
void DECRO_RACRO_MAX_SONNERIE(void)
{
	ENTER_CRITICAL();
	
	if(NB_RING >= nbr_sonneries_max)
	{
		decroche_racroche();
	}
	EXIT_CRITICAL();

}


void decroche_racroche(void)
{
	FLAG_MODEM_SI2457_3Bits.Bits.f_CHECK_NB_RING 	= 0;

	//Décroché 
	PRISE_DE_LIGNE = 1;
	tempo_942ms();
	PRISE_DE_LIGNE = 0;	
}

/****************************
;***  Décrocher le modem  ***
;****************************/
void DECROCHE_MODEM(void)
{
	NB_RING = 0;
	
	//passer en phase de synchro
	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET_HARD_SYNC = 1;
	
	//demarrer le reset pour la ré-initialisation du modem
	RESET_MODEM_HARD();
}


void INIT_MODEM_SYNCHRO(void)
{
	FLAG_MODEM_SI2457Bits.Bits.f_RING_EN_COURS = 0;
	
	//AT_INIT
	//On attend un echo puis un result code
//	LCD_CLEAR_FIRST_LINE;
//	lcd_putrs("AT_INIT");
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 0;
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 0;
	MODEM_RESULT_CODE_ATTENDU = 0;
	MODEM_ECHO_ATTENDU = 1;
	ENTER_CRITICAL();	
	usart1_send_at(AT_INIT);
	EXIT_CRITICAL();
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO == 0);
	
	MODEM_RESULT_CODE_ATTENDU = 1;
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE == 0);
	
	//AT_U67
	//On attend un result code
//	LCD_CLEAR_FIRST_LINE;
//	lcd_putrs("AT_U67");
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 0;
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 0;
	MODEM_RESULT_CODE_ATTENDU = 0;
	MODEM_ECHO_ATTENDU = 1;
	ENTER_CRITICAL();	
	usart1_send_at(AT_U67);
	EXIT_CRITICAL();
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO == 0);
	
	//AT_ATA
	//On attend un result code
//	LCD_CLEAR_FIRST_LINE;
//	lcd_putrs("AT_ATA");
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 0;
	FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 0;
	MODEM_RESULT_CODE_ATTENDU = 0;
	MODEM_ECHO_ATTENDU = 1;
	ENTER_CRITICAL();	
	usart1_send_at(AT_ATA);
	EXIT_CRITICAL();
	while(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO == 0);
	

	
	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_ATA_ENCOURS = 1;
	FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET_HARD_SYNC = 0;
}

/****************************
;***  Detection du modem  ***
;****************************
Appeler seulement au demarrage de la platine
 Méthode :
 - Effectuer un RESET hard
 - Envoyer "\r"
 - Recevoir l'echo avec timeout de 200 ms (reponse mesuré à 54ms)
 - Si aucun echo reçu, il n'y a pas de modem */
void DETECT_MODEM(void)
{
	unsigned char vidage_buffer = 0;
	unsigned char i = 200;	//200 ms de timeout
	unsigned char caractere_recu = 0;
	unsigned char modem_detecte = 0;
	
	modem_si2457_init();

	//RESET hard du modem
	ENTER_CRITICAL();	
	RESET_MODEM_HARD_noint();
	EXIT_CRITICAL();
	
	//Vider le bufer de réception
	vidage_buffer = RCREG1;
	
	ENTER_CRITICAL();	
	//Envoyer le \r
	usart1_putc('\r');
	
	//loop
	while(	i-- >= 0 && PIR1bits.RCIF == 0)
	{
		tempo_1ms;
	}
	EXIT_CRITICAL();
	
	if(PIR1bits.RCIF == 1)
	{
		//char_recu
		caractere_recu = RCREG1;
		if(caractere_recu == '\r')
		{
			//OK le modem est là
			modem_detecte = 1;
		}
	}
	

	if(modem_detecte == 0)
	{
		// Pas de modem sur la carte
		FLAG_MODEM_SI2457Bits.Bits.f_MODEM_DETECTE = 0;
		RESET_SI2457 = 0; //3b7
	}
	else
	{
		FLAG_MODEM_SI2457Bits.Bits.f_MODEM_DETECTE = 1;

		//Eteindre le modem pour le moment (ne pas recevoir de "RING\r")
		RESET_SI2457 = 0; //3b7
			
		START_RESET_MODEM(2);	
	}
}

void SCAN_RESULT_CODE_MODEM(unsigned char res_code)
{
	//A t'on reçu un RESULT_CODE ?
	if(FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE == 1)
	{
		//Recu ANS_CONNECT ?
		if(res_code  == ANS_CONNECT)
		{
			deuxiemeLigneLCD = LCD_ANS_CONNECT;
		
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_CONNECTE = 1;
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_RESET_HARD_SYNC = 0;
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_ATA_ENCOURS = 0;
		}
		//Recu ANS_RING ?
		else if(res_code  == ANS_RING)
		{			
			deuxiemeLigneLCD = LCD_ANS_RING;
			
			FLAG_MODEM_SI2457Bits.Bits.f_RING_EN_COURS = 1;
			//bcf		f_RESULT_CODE
			
			//incremente le nombre de sonnerie
			NB_RING ++;		
			
			FLAG_MODEM_SI2457_3Bits.Bits.f_CHECK_NB_RING = 1;
		}
		//Recu ANS_NO_CARRIER ?
		else if(res_code  == ANS_NO_CARRIER)
		{
			deuxiemeLigneLCD = LCD_ANS_NO_CARRIER;
			//reset;
		
		}
		//Recu ANS_CIDM ?
		else if(res_code  == ANS_CIDM)
		{
			deuxiemeLigneLCD = LCD_ANS_CIDM;
			
			FLAG_MODEM_SI2457_3Bits.Bits.f_CIDM_RECU = 1;
			//bcf		f_RESULT_CODE
			FLAG_MODEM_SI2457Bits.Bits.f_MODEM_ATA_ENCOURS = 0;
		}
	
	}
}

void MODEM_PARSE_BUFFER(void)
{
	char i= 0, result_code = 0, temp = 0;
	char begin_caller_number_in_buffer = 0;
	if(MODEM_ECHO_ATTENDU == 1)
	{
		premiereLigneLCD = ECHO_RECU;
		
		FLAG_MODEM_SI2457Bits.Bits.f_RESULT_ECHO = 1;
	}
	else if(MODEM_RESULT_CODE_ATTENDU == 1)
	{
		
		premiereLigneLCD = RESULT_CODE_RECU;
		
		temp = convert_result_code();
		if(temp != -1)
		{
			result_code = temp;
		//	lcd_gotoxy(1,2);
		//	lcd_putrs("Res: ");
		//	lcd_puti(result_code);
			FLAG_MODEM_SI2457Bits.Bits.f_RESULT_CODE = 1;
			
			SCAN_RESULT_CODE_MODEM(result_code);
		}
	}
	else
	{
	
		//"DATE = 0901\r" (1er septembre)
		if( BUFFER_USART1[0] == 'D' &&
			BUFFER_USART1[1] == 'A' &&
			BUFFER_USART1[2] == 'T' &&
			BUFFER_USART1[3] == 'E')
		{
			premiereLigneLCD = DATE_RECU;		
		}
		//"TIME = 1725\r" (17 h 25)
		else if( BUFFER_USART1[0] == 'T' &&
			BUFFER_USART1[1] == 'I' &&
			BUFFER_USART1[2] == 'M' &&
			BUFFER_USART1[3] == 'E')
		{
			premiereLigneLCD = TIME_RECU;
		}
		//"NMBR = 0671943190\r"
		else if( BUFFER_USART1[0] == 'N' &&
			BUFFER_USART1[1] == 'M' &&
			BUFFER_USART1[2] == 'B' &&
			BUFFER_USART1[3] == 'R')
		{
			premiereLigneLCD = NMBR_RECU;

			//Cas des téléphones au format internationnal (++33...) : ex +33689201132
			if(BUFFER_USART1[7] == '+')
			{
				i = 0;
				CALLER_PHONE_NUMBER[i++] = '0';
				begin_caller_number_in_buffer = 9;
			}
			
			//Cas des téléphones au format internationnal (0033...) : ex 0033689201132
			else if(BUFFER_USART1[7] == '0' && BUFFER_USART1[8] == '0')
			{
				i = 0;
				CALLER_PHONE_NUMBER[i++] = '0';
				begin_caller_number_in_buffer = 10;
			}
			
			
			//Cas des numéros de téléphones au format francais: (0......) : ex 0689201223
			else if(BUFFER_USART1[7] == '0')
			{
				i = 0;
				begin_caller_number_in_buffer = 7;
			}
			
			
			for(; i < CALLER_PHONE_NUMBER_LENGTH; ++i)
			{
				CALLER_PHONE_NUMBER[i] = BUFFER_USART1[begin_caller_number_in_buffer+i];
			}			
		}
		//"NAME = ...\r"
		else if( BUFFER_USART1[0] == 'N' &&
			BUFFER_USART1[1] == 'A' &&
			BUFFER_USART1[2] == 'M' &&
			BUFFER_USART1[3] == 'E')
		{
			premiereLigneLCD = NAME_RECU;
		}
		//"MESG = ...\r"
		else if( BUFFER_USART1[0] == 'M' &&
			BUFFER_USART1[1] == 'E' &&
			BUFFER_USART1[2] == 'S' &&
			BUFFER_USART1[3] == 'G')
		{
			premiereLigneLCD = MESG_RECU;
		}
		else
		{
			premiereLigneLCD = CHAMP_INCONNU_RECU;
			/*	LCD_CLEAR_FIRST_LINE;
			for(i=0;i<NB_BYTE_BUFFER_USART1;i++)
			{
			/	lcd_putc(BUFFER_USART1[i]);
			}
			*/
			
			//YDA TEST TEMP
			MODEM_RESULT_CODE_ATTENDU = 1;
			MODEM_PARSE_BUFFER();
		}
	}
	MODEM_ECHO_ATTENDU = 0;
	MODEM_RESULT_CODE_ATTENDU = 0;
	NB_BYTE_BUFFER_USART1 = 0;
}

/*
 ici, NB_BYTE_BUFFER_USART1 peut etre egal à 1,2 ou 3
 1 = on a reçu seulement \r, on ignore
 2 = ex "0\r" (OK)
 3 = ex "30\r" (Caller ID mark detected)
*/
	
//	Conversion du result code ASCII -> binaire naturel
char convert_result_code(void)
{
	char result = -1;
	unsigned char dizaines = 0, unites = 0;
	if(NB_BYTE_BUFFER_USART1 == 1)
	{
		//on l'ignore
	//	LCD_CLEAR_FIRST_LINE;
	//	lcd_putrs("Res code ignore");
	}
	else if(NB_BYTE_BUFFER_USART1 == 2)
	{
		result = BUFFER_USART1[0] - 0x30;
	}
	else if(NB_BYTE_BUFFER_USART1 == 3)
	{
		//D'abord dizaines
		dizaines = BUFFER_USART1[0] - 0x30;
		unites = BUFFER_USART1[1] - 0x30;
		result = 10 * dizaines + unites;
	}
	else
	{
	//	LCD_CLEAR_FIRST_LINE;
	//	lcd_putrs("Res code ignore");
	}
	return result;
}

void AUTORISATION_IDENTITEL(void)
{
	if(FLAG_MODEM_SI2457Bits.Bits.f_ID_NMBR_RECU == 1)
	{
		FLAG_MODEM_SI2457Bits.Bits.f_ID_NMBR_RECU = 0;
		
		LCD_CLEAR_FIRST_LINE;
		lcd_putrs("AUTORISATION_IDE");
	}
}