#include "lcd.h"
#include "usart.h"



void main (void);

void main ()
{
lcd_init();
init_usart();
	while(1)
		{Delay10KTCYx(200);
		lcd_gotoxy(1,1);
		}
	 
}

