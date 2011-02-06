#include <iostream>

#include "Jeu.h"

using namespace std;

int main()
{
	bool continuer = true;
	int choix = 0;

	Jeu * jeu = new Jeu();

	jeu->build();
	jeu->afficher();

	std::cout << std::endl;

	jeu->solveRecursive();
	jeu->afficher();

	system("pause");
    return 0;
}
