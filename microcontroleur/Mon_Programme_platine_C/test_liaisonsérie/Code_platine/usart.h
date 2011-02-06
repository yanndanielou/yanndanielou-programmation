#include <p18f6722.h>



void InterruptHandlerHigh (void);

void envoyer_donnees_usart (char a_envoyer[],int longueur);
void traiter_reception_commande (char donnees_recues[]);
void traiter_reception_parametre (char donnees_recues[]);
void init_usart (void);
char valider_checksum_cmd(char donnees_recues[]);
char valider_checksum_parametre(char parametre_recu[]);
void effacer_chaine (char chaine_a_effacer[]);

