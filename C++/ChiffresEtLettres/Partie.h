#pragma once

#include <iostream>

#include "Lettres.h"
#include "Chiffres.h"

const int CHIFFRES=0;
const int LETTRES=1;
const int QUITTER=2;

class Partie
{
public:
	Partie(void);
	~Partie(void);

	int demanderChoixJeu();
	void jouer();
};
