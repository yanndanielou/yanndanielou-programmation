#include <iostream>
#include <time.h>

#include "windows.h" // you need that on Windows for Sleep() 

#include "Jeu.h"

using namespace std;

int main()
{
	bool continuer = true;
	int choix = 0;

	Jeu * jeu = new Jeu();

	jeu->build();
	jeu->afficher();

	while(choix != 2)
	{
		 std::cout << "1: entrer valeur" << std::endl;
		 std::cout << "2: résoudre" << std::endl;

		std::cin >> choix;

		if(choix == 1)
		{
			int x, y, valeur;
			std::cout << "entrez x" << std::endl;
			std::cin >> x;

			std::cout << "entrez y" << std::endl;
			std::cin >> y;
			
			std::cout << "entrez valeur" << std::endl;
			std::cin >> valeur;

			jeu->getCase(x, y)->setValue(valeur, true);
			//jeu->setCase(x, y, valeur);
			
		
			jeu->afficher();
		}
	}

	clock_t start_time,elapsed;
	double elapsed_time;
	start_time = clock(); 


	jeu->solveRecursive();
	
	elapsed = clock()-start_time;

	elapsed_time = elapsed / ((double) CLOCKS_PER_SEC);
	printf("Time elapsed: %f\n",elapsed_time); 

	jeu->afficher();

	system("pause");
    return 0;
}
