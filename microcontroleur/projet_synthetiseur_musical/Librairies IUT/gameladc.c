#include "gameladc.h"
#include <delays.h>

///////////////////////////////////////////////////////////////////////////////////
//                                                                               //
// sources pour l'utilisation des entrées analogiques sur la carte               //
// microcontroleur gamel_trophy                                                  // 
//                              version 1.1 février 05                           //
// pour plus de details, cf Datasheet ch.19                                      //
//                                                                               //
// fonctions disponibles :                                                       //
//         adc_init(char numero_lastchannel_used)  initialisation du convertisseur/
//                                                 ADC pour le nombre de channels//
//                                                 voulu                         //
//                                                                               //
//         adc_read(char numero_channel)  lecture de la valeur analogique d'un   //
//                                        channel                                //
//                                                                               //
// pas d'utilisation de l'interruption ADC et references de conversion 0-5V      // 
// les broches configurées en analogique par adc_init(...) restent en analogique //
// Temps de conversion : environ 40 us                                           //
//                                                                               //
// brochage : Microcontroleur TOR -  channel	                                 //
//                          A0    -  AN0                                         //
//                          A1    -  AN1                                         //
//                          A2    -  AN2                                         //
//                          A3    -  AN3                                         //
//                          A5    -  AN4                                         //
//                          E0    -  AN5                                         //
//                          E1    -  AN6                                         //
//                          E2    -  AN7                                         //
//                                                                               //
//                                                                               //
///////////////////////////////////////////////////////////////////////////////////


/********************************************************************
*    Function Name:  adc_init(char numero_lastchannel_used)         *
*    Return Value:   void                                           *
*    Parameters:     numero_lastchannel_used : nombre de ports      *
*                    analogiques utilises                           * 
*    Description:    configure l'horloge du convertisseur, le temps *
*                    d'acquisition, invalide les interruptions,     *
*                    choisit 0-5V pour reference et configure le    *
*                    nombre de canaux utilisés                      *
********************************************************************/
void adc_init(char numero_lastchannel_used)
{
    switch(numero_lastchannel_used)      // configure ADCON1 suivant le numero du dernier
                                         // channel analogique que l'on souhaite utiliser et
                                         // met en entree les broches associees
    {
        case(0) : ADCON1 = 0xE;
                  TRISA = TRISA|0b00000001; 
                   break;
        case(1) : ADCON2 = 0xD;
                  TRISA = TRISA|0b00000011; 
                   break; 
        case(2) : ADCON1 = 0xC;
                  TRISA = TRISA|0b00000111; 
                   break;
        case(3) : ADCON1 = 0xB;
                  TRISA = TRISA|0b00001111; 
                   break;
        case(4) : ADCON1 = 0xA;
                  TRISA = TRISA|0b00101111;
                   break;
        case(5) : ADCON1 = 0x9;
                  TRISA = TRISA|0b00101111;
                  TRISE = TRISE|0b00000001;
                   break;
        case(6) : ADCON1 = 0x8;
                  TRISA = TRISA|0b00101111;
                  TRISE = TRISE|0b00000011; 
                   break;
        case(7) : ADCON1 = 0x7;
                  TRISA = TRISA|0b00101111;
                  TRISE = TRISE|0b00000111; 
                   break;
        default : ADCON1 = 0xF;
    }

    ADCON2 = 0b10100101;       //configure la frequence de conversion (Fhorloge/16)
                               //et le temps d'acquisition (8 periodes de conversion).
                               //configure aussi la justification a droite du resultat
                               //pour une utilisation des 10 bits.
        
    ADCON0bits.ADON = 1;   // Enable le convertisseur ADC
}

/********************************************************************
*    Function Name:  adc_read(char numero_channel)                  *
*    Return Value:   int : la valeur lue sur le channel analogique  *
*    Parameters:     numero_channel : numero du channel analogique  *
*                    sur lequel sera lue la valeur                  *
*    Description:    selectionne une entree anaologique (channel),  *
*                    lance la conversion et lorsque celle-ci est    *
*                    finie, renvoie la valeur lue (10 bits) en int  *
********************************************************************/
int adc_read(char numero_channel)  
{
  int result =0 ;
  
  ADCON0 = ((numero_channel << 2) & 0b00111100) |
           (ADCON0  & 0b11000011);                // selection du channel
  
  ADCON0bits.GO = 1;  // Set the GO bit to start a conversion
  
  while(ADCON0bits.GO);  // attend la fin de la conversion

  result = (int)ADRESH;  // Read ADRESL into the lower byte
  result = result<<8 | (int)ADRESL;  // Read ADRESH into the high byte

  return (result);     // Return the long variable
}
