#pragma once

#include "Container.h"
//#include "Case.h"

class Case;
class Colonne : public Container
{
public:
	Colonne(void);
	~Colonne(void);
	
	virtual void ajouterCase(Case* toAdd);
};
