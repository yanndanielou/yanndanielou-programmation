#include <p18f6722.h>
#include <delays.h>

char octet;
char chaine[4];
char i;

void main (void);
void InterruptHandlerHigh (void);

void main ()
{
i=0;


Delay10KTCYx(200);
TRISA=0b11111101;
TRISC=0b10000000;
TRISF=0b11110111;

PORTFbits.RF3=0;
PORTAbits.RA1=0;

RCONbits.IPEN=1;
INTCONbits.GIEH=1;
INTCONbits.GIEL=1;

IPR1bits.RC1IP=1;
IPR3bits.RC2IP=1;

PIE1bits.RC1IE=1;
PIE3bits.RC2IE=1;

	
	RCSTA1=0b10010000;	// SPEN=1 valide le port série, CREN=1 valide la réception de donnée
	TXSTA1=0b10100100; 	// high speed mode brgh1 , TXEN 1 autorise émission de donnée
	SPBRG1=123;

	while(1)
	 {
		if((chaine[0]=='D')&&(chaine[1]=='P'))
			{
				PORTAbits.RA1=0;
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				PORTAbits.RA1=1;
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				PORTAbits.RA1=0;
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				PORTAbits.RA1=1;
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				Delay10KTCYx(200);
	
				chaine[1]=32;
			}
		if((chaine[0]=='D')&&(chaine[1]=='L'))
			{
				PORTFbits.RF3=0;
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				PORTFbits.RF3=1;
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				PORTFbits.RF3=0;
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				PORTFbits.RF3=1;
				Delay10KTCYx(200);
				Delay10KTCYx(200);
				Delay10KTCYx(200);
	
				chaine[1]=32;
			}
				
	 }

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
				octet=RCREG1;


				if(octet=='D')
				  {
					chaine[0]=octet;
					chaine[1]=chaine[2]=chaine[3]=0x32;
					i=1;
				  }
				else
				   {
						if(chaine[0]=='D')
						  {
							chaine[i]=octet;
							i++;
				 		 }		
					}
	
						
		}

}


