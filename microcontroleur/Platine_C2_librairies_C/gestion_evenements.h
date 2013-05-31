#ifndef __GESTION_EVENEMENTS_H_
#define __GESTION_EVENEMENTS_H



struct save_intBits
  {
	unsigned SAVE_GIE:1;     /* mémorise la valeur de GIE */
	unsigned save_intBits_UNUSED:6;
  };

/*--- save_int bits union ---*/

typedef union
  {
  struct save_intBits Bits; 
  unsigned char data;
  }save_int;
  


#ifdef VAR_GLOBALES_GESTION_EVENEMENTS
	save_int save_intBits;
//	save_intBits.data = 0;	
#else
	extern save_int save_intBits;
#endif




void ENTER_CRITICAL (void);
void END_CRITICAL (void);

#endif