#include "SrvFactory.h"

#include "SrvBuilder.h"

#include "..\..\Comon\cmproperties.h"


#include <iostream>
#include <fstream>
#include <string>


#include "..\..\Comon\Miscellaneous.cpp"

//Initialisation du singleton
SrvFactory* SrvFactory::_instance = 0;


SrvFactory* SrvFactory::getInstance()
{
	if(_instance)
		return _instance;
	else
	{
		return new SrvFactory();
	}
}


SrvFactory::SrvFactory(void)
{
	_separator = CmProperties::getInstance()->getCharProperty("CSV_SEPARATOR", ';');
}

SrvFactory::~SrvFactory(void)
{
}

void SrvFactory::build(std::vector<std::string> row)
{
	//Construction des cartes
	if(row[0] == "SrvCase")
		new SrvCaseBuilder(row);
	else if(row[0] == "SrvGoCase")
		new SrvGoCaseBuilder(row);
	else if(row[0] == "SrvStreet")
		new SrvStreetBuilder(row);
	else if(row[0] == "SrvCommunityChest")
		new SrvCommunityChestBuilder(row);
	else if(row[0] == "SrvTaxCase")
		new SrvTaxCaseBuilder(row);
	else if(row[0] == "SrvStation")
		new SrvStationBuilder(row);
	else if(row[0] == "SrvChance")
		new SrvChanceBuilder(row);
	else if(row[0] == "SrvJailCase")
		new SrvJailCaseBuilder(row);
	else if(row[0] == "SrvCompany")
		new SrvCompanyBuilder(row);
	else if(row[0] == "SrvGoToJail")
		new SrvGoToJailBuilder(row);
	else if(row[0] == "SrvFreeParking")
		new SrvFreeParkingBuilder(row);

	//Construction des joueurs
	else if(row[0] == "SrvPlayer")
		new SrvPlayerBuilder(row);
	
	else
		int a = 1;


}

void SrvFactory::read(std::string fileName)
{
	std::ifstream fichier(fileName.c_str(), std::ios::in);
 
	if(fichier.is_open())
	{
		std::string ligne;

		while(std::getline(fichier, ligne))
		{
			std::vector<std::string> row;
			split(ligne, _separator, &row);
			build(row);
			
		}
	}
	else if(fileName == "Cases.csv")
	{
		//createCases();
	}

}

void SrvFactory::readDatasource()
{
//	SrvProperties* prop = SrvProperties::getInstance();
	std::string fileName = "E:\\programmation\\C++\\Monopoly\\Server\\Data\\Csv\\datasources.csv";

	std::ifstream fichier(fileName.c_str(), std::ios::in);
 
	if(fichier.is_open())
	{
		std::string ligne;

		while(std::getline(fichier, ligne))
		{
			if(!ligne.empty())
				read(ligne);			
		}
	}
	else
	{
		//createDatasource();
	}


}


void SrvFactory::createDatasource()
{
	/*std::ofstream outfile("Data\\Csv\\datasources.csv");

	if (outfile.is_open())
	{
		outfile << "Data\\Csv\\Case.csv";	
		outfile.close();
		readDatasource();
	}*/
}

void SrvFactory::createCases()
{
/*	std::ofstream outfile("Data\\Csv\\Case.csv");

	if (outfile.is_open())
	{
		outfile << "Data\\Csv\\Case.csv";
		outfile.close();
		readDatasource();
	}*/
}
/*

void split(std::string toBeSplitted, char separator, std::vector<std::string>* row)
{
	std::size_t currentPosition;
    std::size_t previousPosition;

    previousPosition = toBeSplitted.find(separator);

	if(previousPosition != std::string::npos)
    {
        previousPosition = 0;
		while((currentPosition = toBeSplitted.find(separator, previousPosition)) != std::string::npos
            &&(currentPosition < toBeSplitted.length()))
        {
            row->push_back(toBeSplitted.substr(previousPosition, currentPosition - previousPosition));
            previousPosition = currentPosition + 1;
        }

        row->push_back(toBeSplitted.substr(previousPosition, toBeSplitted.length() - previousPosition));

    }
    else
    {
       row->push_back(toBeSplitted);

    }
}

*/