#pragma once

#include "Container.h"

class Case;
class Diagonale : public Container
{
public:
	Diagonale(void);
	~Diagonale(void);
	
	virtual void ajouterCase(Case* toAdd);
};
