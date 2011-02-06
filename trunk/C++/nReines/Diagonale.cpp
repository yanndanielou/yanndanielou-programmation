#include "Diagonale.h"

#include "Case.h"

Diagonale::Diagonale(void)
{
}

Diagonale::~Diagonale(void)
{
}

void Diagonale::ajouterCase(Case* toAdd)
{
	toAdd->setDiagonale(this);
	Container::ajouterCase(toAdd);
}

