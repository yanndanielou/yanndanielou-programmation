#include <p18f4550.h>
///////////////////////////////////////////////////////////////////////////////////
//                                                                               //
// declarations des fonctions pour l'utilisation des sorties PWM1 et PWM2 de la  //
// carte microcontroleur gamel_trophy_v3                                         //
//                          version 1.1 mars 06                                  //
//                                                                               //
///////////////////////////////////////////////////////////////////////////////////


/* used to hold the 10-bit duty cycle */
union PWMDC
{
    unsigned int lpwm;
    char bpwm[2];
};

/* pwm_init : initialisation des sorties pwm */
void pwm_init(unsigned char period, char nb_canaux );

/* pwm_setdc1 : definition du rapport cyclique de la sortie pwm1 */
void pwm_setdc1(unsigned int dutycycle1);

/* pwm_setdc2 : definition du rapport cyclique de la sortie pwm2 */
void pwm_setdc2(unsigned int dutycycle2);

