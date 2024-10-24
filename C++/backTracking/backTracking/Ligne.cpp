#include "Ligne.h"

#include "Case.h"
Ligne::Ligne(void)
{
}

Ligne::~Ligne(void)
{
}

void Ligne::ajouterCase(Case* toAdd)
{
	toAdd->setLigne(this);
	Container::ajouterCase(toAdd);
}