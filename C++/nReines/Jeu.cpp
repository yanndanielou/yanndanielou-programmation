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
  _cases = new int*[TAILLE_LIGNE];

  for(int i=0; i<TAILLE_LIGNE; i++)
  {
	_cases[i] = new int[TAILLE_COLONNE];
//	_cases[i][j] = 0;
  }

  for(int i=0; i<TAILLE_LIGNE; i++)
  {
	  for(int j=0; j<TAILLE_COLONNE; j++)
	  {
		  _cases[i][j]=VIDE;
	  }
  }

}

void Jeu::afficher()
{
  for(int i=0; i<TAILLE_LIGNE; i++)
  {
	  for(int j=0; j<TAILLE_COLONNE; j++)
	  {
		  std::cout << _cases[i][j] << ' ';
	  }
	  std::cout<<std::endl;
  }
}

bool Jeu::verifierDiagonale(int x, int y)
{
	bool result = true;

	//Dans le sens en haut à gauche
	int tmpX = x;
	int tmpY = y;

	while(tmpX >= 0 &&
		  tmpY >= 0 &&
		  result)
	{
		if(_cases[tmpX][tmpY] == REINE)
		{
			result = false;
		}
		tmpX--;
		tmpY--;
	}

	//Dans le sens en haut à droite
	tmpX = x;
	tmpY = y;

	while(tmpX < TAILLE_LIGNE &&
		  tmpY >= 0 &&
		  result)
	{

		if(_cases[tmpX][tmpY] == REINE)
		{
			result = false;
		}
		tmpX++;
		tmpY--;
	}
	
	//Dans le sens en bas à gauche
	tmpX = x;
	tmpY = y;

	while(tmpX >= 0 &&
		  tmpY < TAILLE_COLONNE &&
		  result)
	{
		if(_cases[tmpX][tmpY] == REINE)
		{
			result = false;
		}
		tmpX--;
		tmpY++;
	}
	
	//Dans le sens en bas à droite
	tmpX = x;
	tmpY = y;

	while(tmpX < TAILLE_LIGNE   &&
		  tmpY < TAILLE_COLONNE &&
		  result)
	{
		if(_cases[tmpX][tmpY] == REINE)
		{
			result = false;
		}
		tmpX++;
		tmpY++;
	}
	
	return result;
}

bool Jeu::verifierLigne(int x)
{
  bool result = true;

  for(int i=0; i<TAILLE_LIGNE && result; i++)
  { 
	  if(_cases[x][i] == REINE)
			 result = false;
  }
  return result;
}

bool Jeu::verifierColonne(int y)
{
  bool result = true;

  for(int i=0; i<TAILLE_COLONNE && result; i++)
  { 
	  if(_cases[i][y] == REINE)
			 result = false;
  }
  return result;
}

bool Jeu::solve()
{
 /* int curseur = 0;

  while(curseur<30)
  {
	  afficher();
	  Case * currentCase = _cases[curseur];
	  //on peut modifier modifier la case si ce n'est pas une consigne
	  if(currentCase->isFixed() == false)
	  {
		bool caseTrouvee = false;
		int i;

		for(i=currentCase->getValue()+1; i<=VALUE_MAX && !caseTrouvee; i++)
		{
			currentCase->setValue(i);
			if(currentCase->getLigne()->isValid() &&
			   currentCase->getColonne()->isValid())// &&
		//	   currentCase->get//()->isValid())
			{
				caseTrouvee = true;
/*
				for(int k=0; k <_lignes.size(); k++)
				{
					if(_lignes[k]->isValid() == false)
					{
						system("pause");
					}
				}
				for(int k=0; k < _colonnes.size(); k++)
				{
					if(_colonnes[k]->isValid() == false)
					{
						system("pause");
					}
				}
				for(int k=0; k < _diagonales.size(); k++)
				{
					if(_diagonales[k]->isValid() == false)
					{
						system("pause");
					}
				}*//*
			}
		}
		if(caseTrouvee == false)
		{
			currentCase->setValue(0);
			curseur--;
			while(_cases[curseur]->isFixed() == true && curseur >0)
			{
				curseur--;
			}
			if(curseur <= 0)
			{
				std::cout << "Case non trouvée, retour en arriere";
				return false;
			}
		}
		else
		{
			curseur++;
		}

	  }
	  //Si la case courante est une consigne, on ne peut pas la modifier donc on passe à la suivante
	  else
	  {
		curseur++;
	  }
  }*/
 return true;
}

bool Jeu::solveRecursive()
{
	solve2(0,0);
	return true;
}


bool Jeu::solve2(int index, int colonne)
{

  bool caseTrouvee = false;

	int i;
	for(i=colonne; i<TAILLE_LIGNE && !caseTrouvee; i++)
	{
		if( verifierLigne(index)   && 
		    verifierColonne(i) && 
		    verifierDiagonale(index, i) )
		{
			_cases[index][i] = REINE;
			caseTrouvee = true;
		}
	}
	if(caseTrouvee == false)
	{
	//	for(int i=0; i<TAILLE_LIGNE; i++)
	//		_cases[index][i] = VIDE;

		return false;
	}
	else
	{
		if(index == TAILLE_LIGNE - 1)
			return true;

		if(solve2(index+1,0))
			return true;
		else
		{			
			for(int i=0; i<TAILLE_LIGNE; i++)
				_cases[index][i] = VIDE;
			return solve2(index, i+1);
		}
	}
return true;
}