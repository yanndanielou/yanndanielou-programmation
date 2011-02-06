#include "main.h"


void main (void)
{  
char menu=0,current_affichage=99;

 OSCCON = OSCCON|0x70;

 lcd_init();

 TRISA=0xBF;
 TRISA=TRISA&0b11110111;


OpenTimer0( TIMER_INT_ON &
T0_8BIT &
T0_SOURCE_INT &
T0_PS_1_128); //config timer

T1CON=0b11001101;

INTCONbits.GIE=1;

PORTAbits.RA6 =0;


 while(1)
  {
	if(etat_bp0==0)
	  {
		if(menu>0)
   		  menu--;
  		else
          menu=nbre_menus;
		
	   etat_bp0=2;
      }

	if(etat_bp2==0)
	  {
		if(menu<nbre_menus)
   		  menu++;
  		else
          menu=0;
		
	   etat_bp2=2;
      }


	if(etat_bp1==0)
	  {
	    if(menu==0)
	     gamme();

 		if(menu==1)
   	     auclairdelalune();

  		if(menu==2)
         frerejacques();

  		if(menu==3)
         surlepontdavignon();

	   etat_bp1=2;
      }






if (menu==0)
  {
   if(current_affichage!=0)
    { lcd_gotoxy(1,1);
      lcd_putrs(" JOUER LA GAMME ");
      lcd_gotoxy(2,1);
      lcd_putrs(blanc);
      current_affichage=0;	
    }
  }


if (menu==1)
  {
     if(current_affichage!=1)
      { lcd_gotoxy(1,1);
        lcd_putrs(" JOUER AU CLAIR ");
        lcd_gotoxy(2,1);
        lcd_putrs("   DE LA LUNE   ");
        current_affichage=1;
      }
  }


if (menu==2)
  {
     if(current_affichage!=2)
      { lcd_gotoxy(1,1);
        lcd_putrs("     JOUER      ");
        lcd_gotoxy(2,1);
        lcd_putrs(" FRERE JACQUES  ");
        current_affichage=2;
      }
  }

if (menu==3)
  {
     if(current_affichage!=3)
      { lcd_gotoxy(1,1);
        lcd_putrs("  JOUER SUR LE  ");
        lcd_gotoxy(2,1);
        lcd_putrs(" PONT D'AVIGNON ");
        current_affichage=3;
      }
  }


   } //end while

}



//////////////////////////////////////////
// ANTI REBOND BP0

void anti_rebond_BP0(void)
{
  if(BP0==0)
    {
       if(old_bp0==1)
 	 	 {
		   old_bp0=0;
		   anti_rebond_bp0=0;
		 }
	
        if(old_bp0==0)
	      {
		    anti_rebond_bp0++;
		     if((anti_rebond_bp0>=duree_bp)&&(disable_bp0==0))
		       {
				 anti_rebond_bp0=0;
			 	 etat_bp0=0;
				 disable_bp0=1;
			   }

		  }

    }

   if(BP0==1)
    {
       if(old_bp0==0)
 	 	 {
		   old_bp0=1;
		   anti_rebond_bp0=0;
		 }
	
        if(old_bp0==1)
	      {
		    anti_rebond_bp0++;
		     if(anti_rebond_bp0>=duree_bp)
		       {
				 anti_rebond_bp0=0;
			 	 etat_bp0=1;
				 disable_bp0=0;
			   }

		  }

    }	//end if bp0=1

}//end anti_rebond_BP0


//////////////////////////////////////////
// ANTI REBOND BP1

void anti_rebond_BP1(void)
{
  if(BP1==0)
    {
       if(old_bp1==1)
 	 	 {
		   old_bp1=0;
		   anti_rebond_bp1=0;
		 }
	
        if(old_bp1==0)
	      {
		    anti_rebond_bp1++;
		     if((anti_rebond_bp1>=duree_bp)&&(disable_bp1==0))
		       {
				 anti_rebond_bp1=0;
			 	 etat_bp1=0;
				 disable_bp1=1;
			   }

		  }

    }

   if(BP1==1)
    {
       if(old_bp1==0)
 	 	 {
		   old_bp1=1;
		   anti_rebond_bp1=0;
		 }
	
        if(old_bp1==1)
	      {
		    anti_rebond_bp1++;
		     if(anti_rebond_bp1>=duree_bp)
		       {
				 anti_rebond_bp1=0;
			 	 etat_bp1=1;
				 disable_bp1=0;
			   }

		  }

    }	//end if bp1=1

}//end anti_rebond_BP1


//////////////////////////////////////////
// ANTI REBOND BP2

void anti_rebond_BP2(void)
{
  if(BP2==0)
    {
       if(old_bp2==1)
 	 	 {
		   old_bp2=0;
		   anti_rebond_bp2=0;
		 }
	
        if(old_bp2==0)
	      {
		    anti_rebond_bp2++;
		     if((anti_rebond_bp2>=duree_bp)&&(disable_bp2==0))
		       {
				 anti_rebond_bp2=0;
			 	 etat_bp2=0;
				 disable_bp2=1;
			   }

		  }

    }

   if(BP2==1)
    {
       if(old_bp2==0)
 	 	 {
		   old_bp2=1;
		   anti_rebond_bp2=0;
		 }
	
        if(old_bp2==1)
	      {
		    anti_rebond_bp2++;
		     if(anti_rebond_bp2>=duree_bp)
		       {
				 anti_rebond_bp2=0;
			 	 etat_bp2=1;
				 disable_bp2=0;
			   }

		  }

    }	//end if bp2=1

}





