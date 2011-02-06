#include "MyContainer.h"

#include "Case.h"

MyContainer::MyContainer(void)
{
	static int statId = 0;
	_id = statId;
	statId++;

}

MyContainer::~MyContainer(void)
{
	_cases.clear();
}


void MyContainer::addCase(Case* toAdd)
{
	_cases.push_back(toAdd);

}
/*
bool MyContainer::isValid(void)
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
}*/


bool MyContainer::canAdd(int value)
{
	bool result = true;

	for(unsigned int i=0; i<_cases.size() && result; i++)
	{
		if(_cases[i]->getValue() == value)
		{
			result = false;
		}
	}

	return result;
}

