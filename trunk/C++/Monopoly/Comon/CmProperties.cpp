#include "CmProperties.h"

#include <iostream>
#include <fstream>
#include <string>

#include <algorithm>

using namespace std;

//Initialisation du singleton
CmProperties* CmProperties::_instance = 0;


CmProperties* CmProperties::getInstance()
{
	if(_instance)
		return _instance;
	else
	{
		return new CmProperties();
	}
}

CmProperties::CmProperties()
: _fileName("")
{
	_instance = this;
}

CmProperties::~CmProperties(void)
{
}

void CmProperties::setFileName(std::string fileName)
{
	_fileName = fileName;
}

void CmProperties::setBoolProperty(char* propertyName, bool value)
{
std::ofstream outfile(_fileName.c_str());
  outfile.open ("test.txt");
  if (outfile.is_open())
  {
    outfile << "Output operation successfully performed\n";
    outfile.close();
  }
  else
  {
	  std::cout << "Error opening file";
  }
}


void CmProperties::setStringProperty(char* propertyName, char* value)
{
 /* ofstream outfile(_fileName);
  if (outfile.is_open())
  {
    outfile << "Output operation successfully performed\n";
    outfile.close();
  }
  else
  {
    cout << "Error opening file";
  }*/
}


std::string CmProperties::getStringProperty(std::string clef, std::string defaultValue)
{
	//Ouverture du fichier en lecture
	std::ifstream fichier(_fileName.c_str(), std::ios::in);
 
	if(fichier)
	{
		std::string ligne;

		//ligne.find(
		while(std::getline(fichier, ligne))  // tant que l'on peut mettre la ligne dans "contenu"
			{
				//On recopie la ligne dans ligneLisible en enlevant les commentaires
				std::string ligneLisible;
				for(std::size_t i=0; i<ligne.size(); i++)
				{
					//On supprime les commentaires
					if(ligne[i] == DEBUT_COMMENTAIRE)
						break;
					//On supprime tous les caracteres espace
					if(ligne[i] != ESPACE)
						ligneLisible += ligne[i];
				}

				//Cherche le caractere de séparation (SEPARATEUR)
				std::size_t positionSeparateur = ligneLisible.find_first_of(SEPARATEUR);
				std::size_t tailleString = ligneLisible.size();

				//Test si la clé est bien la bonne
				if(ligneLisible.substr(0,positionSeparateur) != clef)
					continue;

				//Si la valeur fait au moins un caractere et que l'on trouve le caractere de separateur
				if((positionSeparateur != 0) && (tailleString > positionSeparateur))
				{
					return ligneLisible.substr(positionSeparateur + 1, tailleString - positionSeparateur);
				}
			}
	}
	return defaultValue;
}

char CmProperties::getCharProperty(std::string clef, char defaultValue)
{
	std::string propertyValueAsString = getStringProperty(clef);

	if(!propertyValueAsString.empty())
		return propertyValueAsString[0];

	//Clé non présente ou mal renseignée
	return defaultValue;
}

bool CmProperties::getBoolProperty(std::string clef, bool defaultValue)
{
	std::string propertyValueAsString = getStringProperty(clef);

	//Mise en MAJUSCULES (toupper)
	std::transform(propertyValueAsString.begin(), propertyValueAsString.end(), propertyValueAsString.begin(), toupper);

	if(propertyValueAsString == "TRUE")
		return true;
	else if(propertyValueAsString == "FALSE")
		return false;

	//Clé non présente ou mal renseignée
	return defaultValue;
}


int CmProperties::getIntProperty(std::string clef, int defaultValue)
{
	std::string propertyValueAsString = getStringProperty(clef);

	//Clé non présente ou mal renseignée
	return defaultValue;
}