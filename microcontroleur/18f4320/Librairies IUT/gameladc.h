#include <p18f4320.h>

///////////////////////////////////////////////////////////////////////////////////
//                                                       	                     //
// declarations des fonctions pour l'utilisation des entrées analogiques sur la  //
// carte microcontroleur gamel_trophy                                            //
//                              version 1.0 octobre 04                           //
//                                                                               //
///////////////////////////////////////////////////////////////////////////////////

// initialisation du convertisseur ADC 
void adc_init(char numero_lastchannel_used);

// lecture de la valeur analogique d'un channel
int adc_read(char numero_channel);
