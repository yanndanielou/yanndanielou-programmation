#include "melodies.h"


void jouer_partition(char notes[],char silences[],char nbre_notes);
void jouer_partition_silence_unique(char notes[],char duree_unique_silence, char nbre_notes);

void auclairdelalune(void)
{
char notes_couplet124_auclairdelalune[]={note_do3,note_do3,note_do3,note_re3,note_mi3,note_re3,note_do3,note_mi3,note_re3,note_re3,note_do3};
char tmps_silences_couplet124_auclairdelalune[]={20,20,20,20,40,40,20,20,20,20,40};

char notes_couplet3_auclairdelalune[]={note_re3,note_re3,note_re3,note_re3,note_la2,note_la2,note_re3,note_do3,note_si2,note_la2,note_sol2};
char tmps_silences_couplet3_auclairdelalune[]={20,20,20,20,30,40,20,20,20,20,40};

jouer_partition(notes_couplet124_auclairdelalune,tmps_silences_couplet124_auclairdelalune,11);
jouer_partition(notes_couplet124_auclairdelalune,tmps_silences_couplet124_auclairdelalune,11);
jouer_partition(notes_couplet3_auclairdelalune,tmps_silences_couplet3_auclairdelalune,11);
jouer_partition(notes_couplet124_auclairdelalune,tmps_silences_couplet124_auclairdelalune,11);

}

void gamme(void)
{
char notes_gamme[]={note_do2,note_re2,note_mi2,note_fa2,note_sol2,note_la2,note_si2,note_do3,note_re3,note_mi3,note_fa3,note_sol3,note_la3,note_si3,note_do4,note_re4,note_mi4,note_fa4,note_sol4,note_la4,note_si4};

jouer_partition_silence_unique(notes_gamme,20,21);

}


void frerejacques (void)
{
//char partition_jacques[]="sol2,la2,si2,sol2,sol2,la2,si2,sol2,si2,do

	effacer_lcd();
	lcd_putrs(" FRERE JACQUES  ");
	jouer_note(note_sol2);
	lcd_gotoxy(2,1);
	lcd_putrs("Frere ");
	silence(20);
	jouer_note(note_la2);
	silence(20);
	lcd_putrs("Jacques");
	jouer_note(note_si2);
	silence(20);
	jouer_note(note_sol2);
	silence(20);
	jouer_note(note_sol2);
	effacer_lcd_bas();
	lcd_putrs("Frere ");
	silence(20);
	jouer_note(note_la2);
	lcd_putrs("Jacques");
	silence(20);
	jouer_note(note_si2);
	silence(20);
	jouer_note(note_sol2);
	silence(20);
	jouer_note(note_si2);
	effacer_lcd_bas();
	lcd_putrs("Dormez ");
	silence(20);
	jouer_note(note_do3);
	lcd_putrs("vous?");
	silence(20);
	jouer_note(note_re3);
	silence(20);
	silence(20);
	jouer_note(note_si2);
	effacer_lcd_bas();
	lcd_putrs("Dormez ");
	silence(20);
	jouer_note(note_do3);
	silence(20);
	jouer_note(note_re3);
	lcd_putrs("vous?");
	silence(20);
	silence(20);
	jouer_note(note_re3);
	effacer_lcd_bas();
	lcd_putrs("Sonnez ");
	silence(10);
	jouer_note(note_mi3);
	silence(10);
	jouer_note(note_re3);
	lcd_putrs("les ");
	silence(10);
	jouer_note(note_do3);
	effacer_lcd_bas();
	lcd_putrs("matines ");
	silence(10);
	jouer_note(note_si2);
	silence(20);
	jouer_note(note_sol2);
	silence(30);
	jouer_note(note_re3);
	effacer_lcd_bas();
	lcd_putrs("Sonnez ");
	silence(10);
	jouer_note(note_mi3);
	silence(10);
	jouer_note(note_re3);
	lcd_putrs("les ");
	silence(10);
	jouer_note(note_do3);
	effacer_lcd_bas();
	lcd_putrs("matines ");
	silence(10);
	jouer_note(note_si2);
	silence(10);
	jouer_note(note_sol2);
	silence(30);	


	jouer_note(note_sol2);
	effacer_lcd_bas();
	lcd_putrs("Ding ");
	silence(30);
	jouer_note(note_re2);
	lcd_putrs("Dung ");
	silence(30);
	jouer_note(note_sol2);
	lcd_putrs("Dong");
	silence(60);

	jouer_note(note_sol2);
	effacer_lcd_bas();
	lcd_putrs("Ding ");
	silence(30);
	jouer_note(note_re2);
	lcd_putrs("Dung ");
	silence(30);
	jouer_note(note_sol2);
	lcd_putrs("Dong");
	silence(40);

}


void surlepontdavignon(void)
{
char notes_pontavignon[]={note_sol2,note_sol2,note_sol2,note_la2,note_la2,note_la2,note_si2,note_do3,note_re3,note_sol2,note_fa2,note_sol2,note_la2,note_re2};
char silences_pontavignon[]={20,20,35,20,20,30,15,15,15,15,15,15,15,15,20};

char notes_pontavignonmilieu[]={note_la2,note_fa2,note_sol2,note_sol2,note_sol2,note_sol2,note_sol2,note_sol2,note_la2,note_sol2};
char silences_pontavignonmilieu[]={15,15,50,15,15,15,15,15,80,50};

char notes_pontavignonfin[]={note_sol2,note_sol2,note_sol2,note_sol2,note_sol2,note_sol2,note_la2,note_sol2};
char silences_pontavignonfin[]={15,15,15,15,15,15,80,10};



jouer_partition(notes_pontavignon,silences_pontavignon,14);
jouer_partition(notes_pontavignon,silences_pontavignon,10);

jouer_partition(notes_pontavignonmilieu,silences_pontavignonmilieu,10);
jouer_partition(notes_pontavignonfin,silences_pontavignonfin,8);

}


void effacer_lcd_bas (void)
{
lcd_gotoxy(2,1);
lcd_putrs("                ");
lcd_gotoxy(2,1);
}

void effacer_lcd(void)
{
lcd_gotoxy(1,1);
lcd_putrs("                ");
lcd_gotoxy(2,1);
lcd_putrs("                ");
lcd_gotoxy(1,1);
}


void jouer_partition(char notes[],char silences[], char nbre_notes)
{char i;

for(i=0;i<nbre_notes;i++)
	{
	 jouer_note(notes[i]);
	 if(silences[i])
	  silence(silences[i]);
	}
}

void jouer_partition_silence_unique(char notes[],char duree_unique_silence, char nbre_notes)
{char i;

for(i=0;i<nbre_notes;i++)
	{
	 jouer_note(notes[i]);
	 silence(duree_unique_silence);
	}
}