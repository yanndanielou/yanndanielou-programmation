#include "Container.h"

#include "Case.h"

Container::Container(void)
{
}

Container::~Container(void)
{
}


void Container::ajouterCase(Case* toAdd)
{
	_cases.push_back(toAdd);

}

bool Container::isValid(void)
{
	bool result = true;

	for(int i=1; i<=VALUE_MAX && result; i++)
	{
		int compteur = 0;
		for(unsigned int j=0; j<_cases.size() && result; j++)
		{
			if(_cases[j]->getValue() == i)
			{
				compteur++;
				if(compteur >1)
					result = false;
			}
		}
	}

	return result;
}

