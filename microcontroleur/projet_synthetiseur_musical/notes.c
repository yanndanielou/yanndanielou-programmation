#include "notes.h"


void silence(char temps)
{
Delay10KTCYx(temps);
}




void jouer_note(char note)
{
char i,repetition;
long valeur_timer1;


switch (note)
{
case note_do2:
  repetition=repetitiondo2;
  valeur_timer1=timerdo2;
  break;
case note_re2:
  repetition=repetitionre2;
  valeur_timer1=timerre2;
  break;
case note_mi2:
  repetition=repetitionmi2;
  valeur_timer1=timermi2;
  break;
case note_fa2:
  repetition=repetitionfa2;
  valeur_timer1=timerfa2;
  break;
case note_sol2:
  repetition=repetitionsol2;
  valeur_timer1=timersol2;
  break;
case note_la2:
  repetition=repetitionla2;
  valeur_timer1=timerla2;
  break;
case note_si2:
  repetition=repetitionsi2;
  valeur_timer1=timersi2;
  break;

case note_do3:
  repetition=repetitiondo3;
  valeur_timer1=timerdo3;
  break;
case note_re3:
  repetition=repetitionre3;
  valeur_timer1=timerre3;
  break;
case note_mi3:
  repetition=repetitionmi3;
  valeur_timer1=timermi3;
  break;
case note_fa3:
  repetition=repetitionfa3;
  valeur_timer1=timerfa3;
  break;
case note_sol3:
  repetition=repetitionsol3;
  valeur_timer1=timersol3;
  break;
case note_la3:
  repetition=repetitionla3;
  valeur_timer1=timerla3;
  break;
case note_si3:
  repetition=repetitionsi3;
  valeur_timer1=timersi3;
  break;

case note_do4:
  repetition=repetitiondo4;
  valeur_timer1=timerdo4;
  break;
case note_re4:
  repetition=repetitionre4;
  valeur_timer1=timerre4;
  break;
case note_mi4:
  repetition=repetitionmi4;
  valeur_timer1=timermi4;
  break;
case note_fa4:
  repetition=repetitionfa4;
  valeur_timer1=timerfa4;
  break;
case note_sol4:
  repetition=repetitionsol4;
  valeur_timer1=timersol4;
  break;
case note_la4:
  repetition=repetitionla4;
  valeur_timer1=timerla4;
  break;
case note_si4:
  repetition=repetitionsi4;
  valeur_timer1=timersi4;
  break;

default:
  break;
}

	for(i=0;i<repetition;i++)
	{
		WriteTimer1(0);
		while(ReadTimer1()<valeur_timer1); //attente 0.5ms
		PORTAbits.RA3=0;
		WriteTimer1(0);
		while(ReadTimer1()<valeur_timer1); //attente 0.5ms
		PORTAbits.RA3=1;
	}

}