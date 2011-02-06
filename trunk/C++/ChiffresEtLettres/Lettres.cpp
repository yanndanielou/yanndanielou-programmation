#include "Lettres.h"

#include <fstream>
#include <iostream>
#include <string>
#include <algorithm>


Lettres::Lettres(void)
: _fileName("liste_francais.txt")
{
}

Lettres::~Lettres(void)
{
}


void Lettres::test(std::string & mot)
{
	nettoyerLigne(mot);
	bool motTrouve;
	motTrouve = testerMot(mot);
	if(motTrouve)
	{
		std::cout << "le Mot " << mot << " est bien présent dans: " << _lettres << std::endl;
	}
	else
		std::cout << "le Mot " << mot << " n'est PAS présent dans: " << _lettres << std::endl;	

}

void Lettres::jouer()
{


	entrerLettres();
	trouverMotContenantLettres();
	afficherResult();
	/*std::cout << "affichage lettres avant: " << _lettres << std::endl;

	std::string::size_type loc = _lettres.find('a');
	if(loc != std::string::npos)
	{
		_lettres.erase(loc, 1);
		//std::remove(

	}

	std::cout << "affichage lettres apres: " << _lettres << std::endl;
*/
		/*test(std::string("ok"));
		test(std::string("Yann"));
		test(std::string("Bonjour"));
		test(std::string("NON"));
*/
}


void Lettres::entrerLettres()
{
	std::cout << "Entrez les lettres" << std::endl;

	for(int i=0; i< NOMBRE_LETTRES; i++)
	{
		char car;
		do
		{
			std::cout << "Lettre num " << i << " : ";
			std::cin >> car;
		}while(car < 'a' || car > 'z');

		_lettres+=car;
		//_lettres.push_back(car);
	}
}


void Lettres::trouverMotContenantLettres()
{
	std::ifstream fichier(_fileName.c_str() , std::ios::in);  // on ouvre en lecture
 
	if(fichier)  // si l'ouverture a fonctionné
	{
		std::string ligne;

		while(std::getline(fichier, ligne))  // tant que l'on peut mettre la ligne dans "contenu"
        {
			if(testerMot(ligne))
				_results.push_back(ligne);

			//std::cout << ligne << std::endl;  // on l'affiche
        }
		fichier.close();
	}
	else
		std::cerr << "Impossible d'ouvrir le fichier !" << std::endl;


}

bool Lettres::testerMot(std::string & mot)
{
	bool res = true;
	nettoyerLigne(mot);

	std::string lettresTemp = _lettres;

	if(mot.size() > _lettres.size())
		res = false;

	else if(mot.size()<1)
		res = false;


	else
	{
		for(unsigned int i=0; i<mot.size() && res; i++)
		{
		//	if(_lettres
			std::string::size_type loc = lettresTemp.find(mot[i]);
			if(loc != std::string::npos)
			{
				lettresTemp.erase(loc,1);
			}
			else
				res = false;
		}

	}

	return res;

}

void Lettres::nettoyerLigne(std::string & ligne)
{
	if(ligne.find('-') != std::string::npos)
		ligne.erase();

	else
	{
		//On supprime les accents
		for(unsigned int i=0; i<ligne.size(); i++)
		{
			if(ligne[i] == 'é')
				ligne[i] = 'e';

			else if(ligne[i] == 'è')
				ligne[i] = 'e';
				
			else if(ligne[i] == 'à')
				ligne[i] = 'a';

			if(ligne[i] >= 'A' && ligne[i] <= 'Z')
			{
				ligne[i] = ligne[i] + 'a' - 'A';
			}
		}
	}


}

void Lettres::afficherResult()
{
	for(unsigned int i=0; i<_results.size(); i++)
	{
		std::cout << _results[i] <<std::endl;
	}
}