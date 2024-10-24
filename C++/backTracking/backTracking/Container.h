#pragma once

#include <vector>

class Case;


const int VALUE_MAX = 9;

class Container
{
public:
	Container(void);
	~Container(void);

	bool isValid();
	bool Container::isPresent(int test);

	virtual void ajouterCase(Case* toAdd);



private:
	std::vector<Case*> _cases;
};
