#include <p18f4320.h>
#include "gamelpwm.h"

/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
// sources pour l'utilisation des sorties PWM1 et PWM2 sur la carte microcontroleur    //
// gamel_trophy                                                                        // 
//                              version 1.0 novembre 04                                //
// pour plus de details, cf Datasheet ch.15                                            //
//                                                                                     //
// fonctions disponibles :                                                             //
//                                                                                     //
//         pwm_init(char period, char nb_canaux) initialisation des sorties PWM        //
//                                                                                     //
//      nb_canaux=1 pour utiliser juste PWM1 et nb_canaux=2 pour utiliser PWM1 et PWM2 //
//      period est la periode de la PWM, en nombre de cycle instruction (TOSCx4 = 0,5 us)
//                                                                                     //
//         pwm_setdc1(unsigned int dutycycle1) definition du rapport cyclique de PWM1  //
//         pwm_setdc2(unsigned int dutycycle2) definition du rapport cyclique de PWM2  //
//                                                                                     //
//      dutycycle est le temps pendant lequel PWM est haut,                            //
//                                             en nombre de cycle horloge (TOSC=0,125 us)
//                                                                                     //
// les broches configur�es en PWM par pwm_init(...) restent en PWM tout le programme   //
//                                                                                     //
// brochage : Microcontroleur TOR -  cannal	                                       //
//                             C2 -  PWM1                                              //
//                             C1 -  PWM2                                              //
//                                                                                     //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////


/********************************************************************
*    Function Name:  pwm_init( char period, char nb_canaux )        *
*    Return Value:   void                                           *
*    Parameters:     period: PWM period  nb_canaux: valide la voie2 *
*    Description:    Cette fonction initialise les sorties PWM      *
*         periode de la pwm =[(period ) + 1] x 4 x TOSC             *
********************************************************************/
void pwm_init( char period, char nb_canaux )
{
  T2CON = 0; // prescaler du timer2 est initialise � 1, le timer est arrete
  CCP1CON=0b00001100;    //ccpxm3:ccpxm0 11xx=pwm mode
  if (nb_canaux==2) CCP2CON=0b00001100;    //ccpxm3:ccpxm0 11xx=pwm mode

  PR2 = period;          // initialisation de la periode
  TRISCbits.TRISC2=0;    //configure la broche C2 en sortie (pour PWM1)
  if (nb_canaux==2) TRISCbits.TRISC1=0;    //configure la broche C1 en sortie (pour PWM1)
  T2CONbits.TMR2ON = 1;  // D�marrage de PWM1 et PWM2 si demand�
}


/**********************************************************************
*    Function Name:  pwm_setdc1 et pwm_setdc2 (unsigned int dutycycle)*
*    Return Value:   void                                             *
*    Parameters:     dutycycle: rapport cyclique 10-bit               *
*    Description:    Ces fonction permettent de d�finir les rapports  *
*                    cycliques des sorties pwm1 et pwm2               *
*                    rapportcylique=dutycycle x Tosc                  *
**********************************************************************/
void pwm_setdc1(unsigned int dutycycle1)
{
  union PWMDC DCycle;

  // Save the dutycycle value in the union
  DCycle.lpwm = dutycycle1 << 6;

  // Write the high byte into CCPR1L
  CCPR1L = DCycle.bpwm[1];

  // Write the low byte into CCP1CON5:4
  CCP1CON = (CCP1CON & 0xCF) | ((DCycle.bpwm[0] >> 2) & 0x30);
}


void pwm_setdc2(unsigned int dutycycle2)
{
  union PWMDC DCycle;

  // Save the dutycycle value in the union
  DCycle.lpwm = dutycycle2 << 6;

  // Write the high byte into CCPR2L
  CCPR2L = DCycle.bpwm[1];

  // Write the low byte into CCP2CON5:4
  CCP2CON = (CCP2CON & 0xCF) | ((DCycle.bpwm[0] >> 2) & 0x30);
}

