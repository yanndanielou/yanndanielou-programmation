#include "Chiffres.h"

#include <time.h>
#include <iostream>
#include <sstream>
#include <algorithm>



Chiffres::Chiffres(void)
:_valeurApprochee(0),
 _ecart(999999)
{
  srand ( time(NULL) );
}

Chiffres::~Chiffres(void)
{
}

void Chiffres::test()
{
	_resultat = 888;

	_nombres.push_back(3);
	_nombres.push_back(75);
	_nombres.push_back(2);
	_nombres.push_back(4);
	_nombres.push_back(4);
	_nombres.push_back(1);


}


void Chiffres::jouer()
{
	//test();
	tirerNombres();
	afficher();
	std::cout << std::endl;
	etapeGenerique(_nombres, NOMBRE_NOMBRES);
	afficherResultats();

}

void Chiffres::afficher()
{
	std::cout << "Consigne: " << _resultat << " Chiffres:" ;
	for(int i=0 ;i<_nombres.size();i++)
	{
		std::cout << _nombres[i] << " ";
	}
	std::cout << std::endl;
}

void Chiffres::afficherResultats()
{
	std::cout << "Affichage des resultats" << std::endl ;

	if(_resultatsTrouves.empty())
	{
		std::cout << "Operation impossible. Meilleur resultat: " << _resultatApproche << " = " << _valeurApprochee << std::endl;
	}
	else
	{
		std::cout << _resultatsTrouves.size() << " resultats trouves:" << std::endl;
		std::cout << "meilleur resultat = " << _meilleurResultat << std::endl;

	}
/*
	for(int i=0 ;i<_resultatsTrouves.size();i++)
	{
		std::cout << _resultatsTrouves[i] << std::endl;
	}*/
}


void Chiffres::tirerNombres()
{
	for(int i=0; i<NOMBRE_NOMBRES; i++)
	{
		//Un nombre compris entre 1 et 14
		int nouveauNombre = rand() % 14 + 1;

		if(nouveauNombre == 11)
			nouveauNombre = 25;
		else if(nouveauNombre == 12)
			nouveauNombre = 50;
		else if(nouveauNombre == 13)
			nouveauNombre = 75;
		else if(nouveauNombre == 14)
			nouveauNombre = 100;

		_nombres.push_back(nouveauNombre);
	}

	_resultat = rand() % 899 + 100;
}

void Chiffres::testerOrdre(std::vector<int> & aTester)
{
	/*for(int i=0; i<NOMBRE_NOMBRES; i++)
	{
		//std::cout << aTester[i] << ' ';
	}
	std::cout << std::endl;*/

	for(unsigned int i=1; i<aTester.size(); i++)
	{
		std::vector<int> temp;
		for(unsigned int j=0; j<=i; j++)
		{
			temp.push_back(aTester[j]);
		}		
		testerVecteur(temp);
	}
}

void Chiffres::testerVecteur(std::vector<int> & vecteur)
{
	std::vector<int> operations;

	for(unsigned int i=0; i<vecteur.size() - 1; i++)
	{
		operations.push_back(ADDITION);
	}

	calculerVecteur(vecteur, operations);

	bool finit = false;
	while(finit == false)
	{
		operationSuivante(operations,operations.size()-1,  finit);
		calculerVecteur(vecteur, operations);
	}

	
}

void Chiffres::operationSuivante(std::vector<int> & operations, int index, bool & fini)
{
	if(index <0)
	{
		fini = true;
		return;
	}


	operations[index]++;
	if(operations[index] > DIVISION)
	{
		operations[index] = ADDITION;
		operationSuivante(operations, index-1,fini);		
	}

}

int Chiffres::calculerVecteur(std::vector<int> nombres, std::vector<int> operations)
{
	int result = nombres[nombres.size() - 1];

	bool resultatTrouve = false;

	//int nombresUtilises = 1;

	for(int i=nombres.size() - 2; i>=0 && !resultatTrouve; i--)
	{
		//nombresUtilises++;
		int operation = operations[i];
		switch(operation)
		{
		case ADDITION:
			result += nombres[i];
			break;

		case SOUSTRACTION:
			result -= nombres[i];
			break;

		case MULTIPLICATION:
			result *= nombres[i];
			break;
			
		case DIVISION:
			if(result % nombres[i] == 0)
			{
				result /= nombres[i];
			}
			else
			{
				return 0;
			}
			break;
		default:
			break;

		}
	/*	if(result == _resultat)
		{
			resultatTrouve = true;
		}*/
	}

	if(_resultat == result)
	{
	//	if(nombresUtilises == nombres.size())
	//	{
			std::ostringstream oss;		

			for(unsigned int i=0; i<nombres.size(); i++)
			{
				oss << nombres[i];

				if(i < nombres.size() -1)
				{
					int operation = operations[i];
					switch(operation)
					{
					case ADDITION:
						oss <<  "+";
						break;

					case SOUSTRACTION:
						oss <<  "-";
						break;

					case MULTIPLICATION:
						oss <<  "*";
						break;
						
					case DIVISION:
						oss <<  "/";
						break;
					default:
						break;

					}
				}
			}
			std::string toAdd = oss.str();
			if(std::find( _resultatsTrouves.begin(), _resultatsTrouves.end(), toAdd) == _resultatsTrouves.end())
		//	if(ite ==  _resultatsTrouves.end())
				_resultatsTrouves.push_back(toAdd);

			if(_meilleurResultat.size() == 0)
				_meilleurResultat = toAdd;
			else if(_meilleurResultat.size() > toAdd.size())
				_meilleurResultat = toAdd;

	//	}
		
	}
	else if(_meilleurResultat.empty())
	{
		int ecart;
		if(result > _resultat)
			ecart = result-_resultat;
		else
			ecart = _resultat - result;

		if(ecart < _ecart)
		{
			_valeurApprochee = result;
			_ecart = ecart;
			std::ostringstream oss;		


			for(unsigned int i=0; i<nombres.size(); i++)
			{
				oss << nombres[i];

				if(i < nombres.size() -1)
				{
					int operation = operations[i];
					switch(operation)
					{
					case ADDITION:
						oss <<  "+";
						break;

					case SOUSTRACTION:
						oss <<  "-";
						break;

					case MULTIPLICATION:
						oss <<  "*";
						break;
						
					case DIVISION:
						oss <<  "/";
						break;
					default:
						break;

					}
				}
			}
			_resultatApproche = oss.str();


		}


	}



	return 1;
}


void Chiffres::etapeGenerique(std::vector<int> & nombres, char numEtape)
{
	if(numEtape>2)
	{   
		for(int i=0; i<numEtape; i++)
		{       
		  etapeGenerique(nombres, numEtape-1);
		  rotation(nombres, NOMBRE_NOMBRES-numEtape); 
		}                     
	}

	else if(numEtape==2){  
		for(int i=0; i<numEtape; i++)
		{
			//ajouter(nombres);
			
			testerOrdre(nombres);		

			rotation(nombres, NOMBRE_NOMBRES-numEtape); 
		}
	}    
}

void Chiffres::rotation(std::vector<int> & entree,char rang)
{
	std::vector<int> temp;

	for(int i=0; i<NOMBRE_NOMBRES-rang; i++)
	{
		temp.push_back(entree[i+rang]);
	}

	entree[rang] = temp[NOMBRE_NOMBRES-rang-1];

	for(int i=1; i<NOMBRE_NOMBRES-rang; i++)
	{
		entree[rang+i] = temp[i-1];   
	}
 }
