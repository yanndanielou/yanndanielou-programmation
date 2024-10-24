#pragma once

#include "Container.h"

class Case;
class Ligne : public Container
{
public:
	Ligne(void);
	~Ligne(void);
	
	virtual void ajouterCase(Case* toAdd);
};
