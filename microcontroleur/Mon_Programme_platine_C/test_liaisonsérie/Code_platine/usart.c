#include <p18f6722.h>
#include "lcd.h"
#include "usart.h"

char octet;
char chaine_commande[8]="        ";
char chaine_parametre[20]="        ";
char i;
char FLAG_COMMANDE=0;
char FLAG_PARAMETRE=0;
char FLAG_INSTRUCTION=0;					//contient le type d'instruction: il est lu lors du traitement du parametre

void envoyer_donnees_usart (char a_envoyer[],int longueur)		//on envoie le nombre d'octets à envoyer car avec strlen , ça ne marchait pas 
{
int i;
	
	for(i=0;i<longueur;i++)
		{
		    while (!TXSTA1bits.TRMT);		//attente de la fin d'écriture
			TXREG1=a_envoyer[i];			
		}
tempo_20ms;									//on attend entre chaque octet
}


void traiter_reception_commande (char donnees_recues[])
{
char cmd_porte[5]={0xFE,0x01,0x00,0x01,0xFF};	//contient l'instruction pour l'ouverture de porte
char cmd_led[5]={0xFE,0x02,0x00,0x02,0xFF}; 	//contient l'instruction pour la led


		if(strcmp(donnees_recues,cmd_porte)==0)	 
		{
			 PORTAbits.RA1=0;
			 Delay10KTCYx(200);
			 PORTAbits.RA1=1;
			 Delay10KTCYx(200);
			 PORTAbits.RA1=0;
			 Delay10KTCYx(200);
			 effacer_chaine(chaine_commande);
	
		 }

		if(strcmp(donnees_recues,cmd_led)==0)	 
		{
			 PORTFbits.RF3=0;
			 Delay10KTCYx(200);
			 PORTFbits.RF3=1;
			 Delay10KTCYx(200);
			 PORTFbits.RF3=0;
			 Delay10KTCYx(200);
			 effacer_chaine(chaine_commande);
	
		 }

		if((donnees_recues[0]==0xFE)&&(donnees_recues[1]==0x03)&&(donnees_recues[4]==0xFF))	 
		{
			FLAG_INSTRUCTION=0x03;	//dans le cas du lcd, on place ce flag, qui sera lu dans traiter_reception_parametre pour savoir quelle action doit être faite
		}							//0x03-->LCD

}


void traiter_reception_parametre (char donnees_recues[])
{ 
 char taille_parametre,i;
 char parametre[16]="                ";
 char blanc[16]="                ";

 taille_parametre=chaine_parametre[1];

		if(FLAG_INSTRUCTION==0x03)				//si la commande concerne le LCD
		 {
		   for(i=0;i<taille_parametre-1;i++)
	 		{
			 parametre[i]=chaine_parametre[i+3];
	 		}	


	
		  lcd_gotoxy(1,1);				//on efface les deux lignes du lcd
		  lcd_puts(blanc);
		  lcd_gotoxy(1,2);
		  lcd_puts(blanc);

		  lcd_gotoxy(1,(chaine_parametre[2]-0x30));		//le premier octet du parametre (chaine_parametre[2]) contient en valeur ascii la ligne ou doit s'afficher le message 
		  lcd_puts(parametre);
		 }	
}



void init_usart (void)
{

TRISA=0b11111101;					//initialise les E/S
TRISC=0b10000000;
TRISF=0b11110101;
TRISE=0b11111111;

PORTFbits.RF3=0;					//donne la valeur de la LED au démarage
PORTAbits.RA1=0;					//idem pour relais

RCONbits.IPEN=1;					//enable les priorités sur les interruptions
INTCONbits.GIEH=1;					//enable interr globales
INTCONbits.GIEL=1;					//enable interr périphériques


PIE1bits.RC1IE=1;					//enable interupt sur lecture USART1	
IPR1bits.RC1IP=1;					//Les interrupt en lecture sont priorité haute

	
RCSTA1=0b10010000;	// SPEN=1 valide le port série, CREN=1 valide la réception de donnée
TXSTA1=0b10101100; 	// high speed mode brgh1 , TXEN 1 autorise émission de donnée
SPBRG1=123;			//définit les 9600 Bauds
}









//----------------------------------------------------------------------------
// High priority interrupt vector

#pragma code InterruptVectorHigh = 0x08
void InterruptVectorHigh (void)
{
  _asm
    goto InterruptHandlerHigh //jump to interrupt routine
  _endasm
}

//----------------------------------------------------------------------------
// High priority interrupt routine

#pragma code
#pragma interrupt InterruptHandlerHigh

void InterruptHandlerHigh ()
{

	if(PIR1bits.RC1IF==1)
	 {		
		  octet=RCREG1;							//on réccupere l'octet envoyé

			if(octet==0xFE)
			  {
			    FLAG_COMMANDE=1;
				FLAG_PARAMETRE=0;
				effacer_chaine(chaine_commande);
				chaine_commande[0]=octet;
				i=1;
			  }

			else if(octet==0xFD)
			  {
				FLAG_COMMANDE=0;
				FLAG_PARAMETRE=1;
				effacer_chaine(chaine_commande);
				effacer_chaine(chaine_parametre);
			    chaine_parametre[0]=octet;
				i=1;
			  }

			else
			 {	
				 if((FLAG_COMMANDE==1)&&(FLAG_PARAMETRE==0)&&(chaine_commande[0]==0xFE))
			  	  {
					chaine_commande[i]=octet;
					i++;
				 		 	
					   if(i==5)
						 {
							if(valider_checksum_cmd(chaine_commande))
							   { 
								 traiter_reception_commande(chaine_commande);
							   }
							else
							   {
								effacer_chaine(chaine_commande);
								i=1;
							   }
					     }
				    }

				  if((FLAG_PARAMETRE==1)&&(FLAG_COMMANDE==0)&&(chaine_parametre[0]==0xFD))
			  		{
					  chaine_parametre[i]=octet;
					  i++;
				 		
						 if(octet==0xFF)
						  {
							 if(valider_checksum_parametre(chaine_parametre)==1)
							   {
								 traiter_reception_parametre(chaine_commande);
							   }
							else
							   {
								effacer_chaine(chaine_parametre);
								i=1;
							   }

							}
					}


			}	//fin else
				
						
	  }		//fin traitement interupt lecture usart 1

}





char valider_checksum_cmd(char commande_recue[])
{
  char cmd_ok[10]="OKCOMMANDE";
  char check_faux[7]="ERRCHEC";

	if((commande_recue[1]+commande_recue[2])==commande_recue[3])	//calul le checksum et le compare avec celui envoyé (commande_recue[3]
	  {
		envoyer_donnees_usart(cmd_ok,10);							//le checksum est bon
		return (1);
      }
	else
	  {
		envoyer_donnees_usart(check_faux,7);						//le checksum est faux
		return (0);
	  }
}


char valider_checksum_parametre(char parametre_recu[])
{
 char taille_fausse[9]="errtaille";									//messages en fonction des différentes erreurs
 char erreur_check1[9]="errcheck1";
 char erreur_check2[9]="errcheck2";
 char erreur_check3[9]="errcheck3";
 char parametre_ok[11]="okparametre";
 char longueur_chaine=0,i,check1=0,check2=0,check3=0;
 int somme=0;


	for(i=0;i<100;i++)								//calcule la longueur de la chaine
	{
	  longueur_chaine++;

  	  if(chaine_parametre[i]==0xff)					//sort de la boucle lorsque l'octet est oxFF, donc fin de l'instruction
		break;
	}


   if((longueur_chaine-6)!=chaine_parametre[1])		//on teste déja la taille par rapport à l'octet de la taille parametre_recu[1]
	{												//longueur-6 car 6 octets non comptés dans parametre_recu[1] (les deux synchro+ 3 checksum+1 longueur 
	  envoyer_donnees_usart(taille_fausse,9);		//en cas d'erreur
	  return 0;
	}
 
   for(i=2;i<(longueur_chaine-4);i++)				//on calcule la somme des octets du parametre qui vont servir à définir les 3 checksum
	{
	 somme=somme+chaine_parametre[i];
	}
 
 check1=somme/255;									//checksum 1 contient la somme/255 (les multiples de 255)

    if(check1==0xFD)								//checksum 3 sert à reperer si la valeur de check1 ou check2 a du etre décrémentée pour ne pas etre confondue avec la synchro
	 { 
	  check3=1;										
	  check1-=1;	
	 }
	
 check2=somme%255;									//checksum 2 contient les "unité"(le reste de la division par 255)

	if(check2==0xFD)
	{
	 check3=2;
	 check2-=1;
	}
 
	if(check1!=chaine_parametre[longueur_chaine-4])	//pour reperer le checksum dans la chaine, on part de la fin
	{
	 envoyer_donnees_usart(erreur_check1,9);		//le checksum 1 ne correspond pas
	 return 0;
	}
 
	if(check2!=chaine_parametre[longueur_chaine-3])
	{
	 envoyer_donnees_usart(erreur_check2,9);		//le checksum 2 ne correspond pas
	 return 0;
	}
 
	if(check3!=chaine_parametre[longueur_chaine-2])
	{
	 envoyer_donnees_usart(erreur_check3,9);		//le checksum 3 ne correspond pas
	 return 0;
	}

 envoyer_donnees_usart(parametre_ok,11);			//les trois checksum correspondent
 return 1;
}


void effacer_chaine (char chaine_a_effacer[])
{
	for(i=0;i<(strlen(chaine_a_effacer));i++)
	{
	 chaine_a_effacer[i]=0;
	}
}