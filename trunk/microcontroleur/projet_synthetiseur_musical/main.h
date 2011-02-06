#include <p18f4320.h>
#include "gamelinit.h"
#include "gamelcd.h"
#include <timers.h>

#define BP0 PORTBbits.RB3
#define BP1 PORTBbits.RB4
#define BP2 PORTBbits.RB5
#define LED PORTAbits.RA6
#define duree_bp	3
#define nbre_menus	3

#define	blanc	"                "

void auclairdelalune(void);
void gamme (void);
void frerejacques (void);
void surlepontdavignon (void);





char	anti_rebond_bp0=0;
char 	etat_bp0=99;
char	old_bp0=0;
char	disable_bp0=0;

char	anti_rebond_bp1=0;
char 	etat_bp1=99;
char	old_bp1=0;
char	disable_bp1=0;

char	anti_rebond_bp2=0;
char 	etat_bp2=99;
char	old_bp2=0;
char	disable_bp2=0;
