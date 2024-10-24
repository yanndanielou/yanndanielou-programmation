#pragma once

#include "Container.h"

class Case;
class Carre : public Container
{
public:
	Carre(void);
	~Carre(void);
	
	virtual void ajouterCase(Case* toAdd);
};
