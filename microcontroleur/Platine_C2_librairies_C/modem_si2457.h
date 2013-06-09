#ifndef __MODEM_SI2457_H_
#define __MODEM_SI2457_H_ 1

#include	<p18f6722.h>
#include	<delays.h>
#include 	<stdlib.h>
#include 	<string.h>



/*****************
;***  Mappage  ***
;*****************/
#define	RESET_SI2457	PORTCbits.RC0	// ;3b7

#define nbr_sonneries_max	3


/******************************
;***  Numeric Result Codes  ***
;******************************/
// Le "Result Code" est reçu en ASCII et suivi d'un \r
#define	ANS_OK			0
#define	ANS_CONNECT		1
#define	ANS_RING		2
#define	ANS_NO_CARRIER	3
#define	ANS_ERROR		4
#define	ANS_NO_ANSWER	8
#define	ANS_CIDM		30
#define	ANS_NO_LINE		42


/*
#define	f_MODEM_DETECTE			FLAG_MODEM_SI2457,0		; 1 : si le modem à été détecté au démarrage de la platine
#define	f_MODEM_INIT			FLAG_MODEM_SI2457,1		; 1 : pendant une phase d'initialisation (reset HARD + reset SOFT)
#define	f_MODEM_RESET			FLAG_MODEM_SI2457,2		; 0 : le modem n'effectue pas de RESET, 1 : le modem effectue un RESET (ne pas envoyer de commande)
#define	f_RESULT_CODE			FLAG_MODEM_SI2457,3		; 1 : si un result code à été reçu du modem, à effacé manuellement après utilisation 
#define	f_RESULT_ECHO			FLAG_MODEM_SI2457,4		; 1 : une trame est recu du modem mais n'est pas géré (typiquement, il s'agit de l'echo après ATE0V0), à effacé manuellement après utilisation 
#define	f_RING_EN_COURS			FLAG_MODEM_SI2457,5		; 1 : reception d'un appel entrant (la ligne sonne)
#define	f_MODEM_RESET_HARD_SYNC	FLAG_MODEM_SI2457,6		; 1 : la trame "ATA\r" à été envoyer, les modems se synchronisent, 0 : sinon
#define	f_MODEM_CONNECTE		FLAG_MODEM_SI2457,7		; 1 : le modem est connecté à un modem distant

#define	f_MODEM_ATA_ENCOURS		FLAG_MODEM_SI2457_2,0	; 1 : lorsque ATA à été envoyer, on attend "CONNECT"
#define	f_ID_NMBR_RECU			FLAG_MODEM_SI2457_2,2	; 1 : le numero de telephone de l'appelant est recu (ID Caller), il est stocker dans NUMERO_APPELANT_BCD, 0 : pas de numero recu ou numero masqué
#define	f_ID_NMBR_MASK_RECU		FLAG_MODEM_SI2457_2,3	; 1 : le numero recu est masqué, 0 : pas de numero recu ou le numero n'est pas masqué
#define	f_ID_DATE_RECU			FLAG_MODEM_SI2457_2,4	; 1 : date recu (ID Caller)
#define	f_ID_TIME_RECU			FLAG_MODEM_SI2457_2,5	; 1 : heure recu (ID Caller)
#define	f_GESTIO_RECONNU		FLAG_MODEM_SI2457_2,6	; 1 : si l'appel en cours est autoriser à programmer la platine par modem
#define	f_SYNC_RTC				FLAG_MODEM_SI2457_2,7	; 1 : ordre de synchroniser la RTC avec la date recu par ID Caller

#define	f_IDENTITEL_VALIDE		FLAG_MODEM_SI2457_3,0	; 1 : à partir du moment ou le numéro reçu est reconnu dans la base de donnée résidant, jusqu'au moment ou l'appelant raccroche (f_RING_EN_COURS passe à 0)	
#define	f_CHECK_NB_RING			FLAG_MODEM_SI2457_3,1	; 1 : le NB_RING a changé (en interruption), il faut verifier s'il n'a pas ateint le max de sonneire (hors interruption pour pouvoir appeler la fonction decroche_racroche)
#define	f_CIDM_RECU				FLAG_MODEM_SI2457_3,2	; 1 : ANS_CIDM recu
#define	f_IDENTITEL_MASQUE		FLAG_MODEM_SI2457_3,3	; 1 : Numéro masqué
#define	f_IDENTITEL_NON_VALIDE	FLAG_MODEM_SI2457_3,4	; 1 : numéro reçu mais non reconnu dans la BD
#define	f_SMS_ATDT_EN_COURS		FLAG_MODEM_SI2457_3,5	; 1 : numéro reçu mais non reconnu dans la BD
#define	f_SMS_FRM_ENVOYE		FLAG_MODEM_SI2457_3,6	; 1 : numéro reçu mais non reconnu dans la BD
*/


struct FLAG_MODEM_SI2457Bits
  {
  unsigned f_MODEM_DETECTE:1;     /* SEL_UNBAL relay */
  unsigned f_MODEM_INIT:1;   /* EARTHSEL relay */
  unsigned f_MODEM_RESET:1;  /* GND_RLY relay */
  unsigned f_RESULT_CODE:1;   /* Phase invert relay */
  unsigned f_RESULT_ECHO:1;  /* Mono amplifier mode */ 
  unsigned f_RING_EN_COURS:1;   /* Alignment bits */
  unsigned f_MODEM_RESET_HARD_SYNC:1;    /* MUTE relay */
  unsigned f_MODEM_CONNECTE:1;    /* MUTE relay */
  unsigned f_MODEM_ATA_ENCOURS:1;    /* MUTE relay */
  unsigned f_ID_NMBR_RECU:1;    /* MUTE relay */
  unsigned f_ID_NMBR_MASK_RECU:1;    /* MUTE relay */
  unsigned f_ID_DATE_RECU:1;    /* MUTE relay */
  unsigned f_ID_TIME_RECU:1;    /* MUTE relay */
  unsigned f_GESTIO_RECONNU:1;    /* MUTE relay */
  unsigned f_SYNC_RTC:1;    /* MUTE relay */
  };

/*--- FLAG_MODEM_SI2457 bits union ---*/

typedef union
  {
  struct FLAG_MODEM_SI2457Bits Bits; 
  unsigned int data;
  }FLAG_MODEM_SI2457;
  
  
struct FLAG_MODEM_SI2457_3Bits
  {
	  unsigned f_IDENTITEL_VALIDE:1;
	  unsigned f_CHECK_NB_RING:1;
	  unsigned f_CIDM_RECU:1;
	  unsigned f_IDENTITEL_MASQUE:1;
	  unsigned f_IDENTITEL_NON_VALIDE:1;
  };

/*--- FLAG_MODEM_SI2457_3 bits union ---*/

typedef union
  {
  struct FLAG_MODEM_SI2457_3Bits Bits; 
  unsigned char data;
  }FLAG_MODEM_SI2457_3;



#ifdef VAR_GLOBALES_MODEM_SI2457
	FLAG_MODEM_SI2457 FLAG_MODEM_SI2457Bits;
	FLAG_MODEM_SI2457_3 FLAG_MODEM_SI2457_3Bits;
	
	//TODO: remplacer par un enum
	unsigned char MODEM_RESULT_CODE_ATTENDU = 0;
	unsigned char MODEM_ECHO_ATTENDU = 0;
#else
	extern FLAG_MODEM_SI2457 FLAG_MODEM_SI2457Bits;
	extern FLAG_MODEM_SI2457_3 FLAG_MODEM_SI2457_3Bits;
	extern unsigned char MODEM_RESULT_CODE_ATTENDU;
	extern unsigned char MODEM_ECHO_ATTENDU;
#endif



void modem_si2457_init (void);
void RESET_MODEM_HARD_int10ms(void);
void RESET_MODEM_HARD_noint(void);
void RESET_MODEM_HARD(void);
void INIT_MODEM_int1s(void);
void START_RESET_MODEM(char seconds);
void SHUTDOWN_MODEM(void);
void INIT_MODEM_IDCALLER(void);
void INIT_MODEM_SYNCHRO(void);
void DETECT_MODEM(void);
void MODEM_PARSE_BUFFER(void);
char convert_result_code(void);
void DECROCHE_MODEM(void);
void acces_modem(void);
void decroche_racroche(void);
void DECRO_RACRO_MAX_SONNERIE(void);
void SCAN_RESULT_CODE_MODEM(unsigned char res_code);
void AUTORISATION_IDENTITEL(void);

#endif