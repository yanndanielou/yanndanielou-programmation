#include <p18f4550.h>

///////////////////////////////////////////////////////////////////////////////////
//                                                       	                     //
// declarations des fonctions pour l'utilisation des entrées analogiques sur la  //
// carte microcontroleur gamel_trophy v3                                         //
//                              version 1.1 mars 06                              //
//                                                                               //
///////////////////////////////////////////////////////////////////////////////////

// initialisation du convertisseur ADC 
void adc_init(char numero_lastchannel_used);

// lecture de la valeur analogique d'un channel
int adc_read(char numero_channel);
