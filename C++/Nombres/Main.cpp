#include <iostream>
#include <vector>
#include <fstream>
#include <time.h>

#include "windows.h" // you need that on Windows for Sleep() 

#include "Nombre.h"


int main()
{
		clock_t start_time,elapsed;


		Nombre n1(8);
		Nombre n3(7);

	double elapsed_time;

		
// end
	
		n3 = n3 * n1;
		n1 = n1*n1;
		Nombre n4 = n1 - n3;
		PRINT(n4);
		/*n1 = n1*n1;
		n1 = n1*n1;*/
		//n1 = n1*n1;
	/*	n1 = n1*n1;
		n1 = n1*n1;
		n1 = n1*n1;
		n1 = n1*n1;*/


		
start_time = clock(); 
		PRINT(n1/Nombre(8));

elapsed = clock()-start_time;

elapsed_time = elapsed / ((double) CLOCKS_PER_SEC);
printf("Time elapsed: %f\n",elapsed_time); 

		PRINT(n1);

		system("pause");
        return 0;
}
