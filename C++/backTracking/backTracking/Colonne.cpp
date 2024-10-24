#include "Colonne.h"

#include "Case.h"
Colonne::Colonne(void)
{
}

Colonne::~Colonne(void)
{
}


void Colonne::ajouterCase(Case* toAdd)
{
	toAdd->setColonne(this);
	Container::ajouterCase(toAdd);
}