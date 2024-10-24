#include "Carre.h"

#include "Case.h"

Carre::Carre(void)
{
}

Carre::~Carre(void)
{
}

void Carre::ajouterCase(Case* toAdd)
{
	toAdd->setCarre(this);
	Container::ajouterCase(toAdd);
}

