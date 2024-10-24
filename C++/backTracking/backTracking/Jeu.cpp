#include <iostream>

#include "Jeu.h"

Jeu::Jeu(void)
{
}

Jeu::~Jeu(void)
{
}

void Jeu::build()
{
	
  for(int i=0; i<NOMBRE_LIGNES; i++){
	  _lignes.push_back(new Ligne());            
  }
  
  for(int i=0; i<NOMBRE_COLONNES; i++){
	  _colonnes.push_back(new Colonne());           
  }
  for(int i=0; i<NOMBRE_CARRES; i++){
	  _carres.push_back(new Carre());   
  }
  
  for(int i=0; i<NOMBRE_LIGNES; i++)
  {
    for(int j=0; j<NOMBRE_COLONNES; j++)
    {   
	 Case * caseTemp = new Case();
	 _cases.push_back(caseTemp);

      _lignes[i]->ajouterCase(caseTemp);   
      _colonnes[j]->ajouterCase(caseTemp);  


	  int carre;
      if(j <3)
       carre = 1;
      else if(j <6)
       carre = 2;
      else if(j <9)
       carre = 3;
      
      if(i <3)
       carre *= 1;
      else if(i <6)
       carre += 3;
      else if(i <9)
       carre += 6;
       
       carre--;

	  
      _carres[carre]->ajouterCase(caseTemp);  
    }
  }       
      
}

void Jeu::afficher()
{

	std::cout << "Affichage du jeu, il y a " << _cases.size() << " cases" << std::endl;

	int ligne = 0;

	for(unsigned int i=0; i< _cases.size(); i++)
	{
		std::cout << _cases[i]->getValue();
	    std::cout << "  ";
		if(i!=0)
		{
			if((i+1)% TAILLE_LIGNE == 0)
			{
				ligne ++;

				if(ligne %3 == 0)
					std::cout << std::endl;

				std::cout << std::endl;
			}
			else if((i+1)%3 == 0)
				std::cout << "  |  ";
		}
	}
}


bool Jeu::solveRecursive()
{
	solve(0);
	return true;
}



bool Jeu::solve(int index)
{

  if(index >= 81)
  { 
	  return true;
  }

 // afficher();


  Case * currentCase = _cases[index];

  bool caseTrouvee = false;

	for(int i=currentCase->getValue()+1; i<=VALUE_MAX && !caseTrouvee; i++)
	{
		if(!currentCase->getLigne()->isPresent(i) &&
		   !currentCase->getColonne()->isPresent(i) &&
		   !currentCase->getCarre()->isPresent(i))
		{
			currentCase->setValue(i);
			caseTrouvee = true;
		}
	}
	if(caseTrouvee == false)
	{
		currentCase->setValue(0);
		return false;
	}
	else
	{
		if(solve(index+1))
			return true;
		else
			return solve(index);
	}

}