#include "SrvMessageManager.h"
#include <sstream>


//Initialisation du singleton
SrvMessageManager* SrvMessageManager::_instance = 0;


SrvMessageManager* SrvMessageManager::getInstance()
{
	if(_instance)
		return _instance;
	else
	{
		return new SrvMessageManager();
	}
}

SrvMessageManager::SrvMessageManager(void)
{
	_instance = this;

	_socket = SrvSocket::getInstance();
}

SrvMessageManager::~SrvMessageManager(void)
{
}


void SrvMessageManager::notifyCreation(std::string message)
{
	_socket->sendToAll(message, true);
	//Sleep(100);
}


void SrvMessageManager::notifyCreation(SrvStreet* street)
{
	// cration d'un flux de sortie
    std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< STREET
		<< SEPARATOR				
		<< street->getGroup()
		<< SEPARATOR
		<< street->getPosition()
		<< SEPARATOR				
		<< street->getLabel()
		<< SEPARATOR
		<< street->getPurchasePrice();


						
    std::string message = oss.str();

	notifyCreation(message);
}

void SrvMessageManager::notifyCreation(SrvStation* station)
{
	// cration d'un flux de sortie
    std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< STATION
		<< SEPARATOR		
		<< station->getPosition()
		<< SEPARATOR				
		<< station->getLabel()
		<< SEPARATOR
		<< station->getPurchasePrice();


						
    std::string message = oss.str();

	notifyCreation(message);
}


void SrvMessageManager::notifyCreation(SrvChance* chance)
{
   std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< CHANCE
		<< SEPARATOR		
		<< chance->getPosition()
		<< SEPARATOR				
		<< chance->getLabel();
						
    std::string message = oss.str();

	notifyCreation(message);

}

void SrvMessageManager::notifyCreation(SrvFreeParking* freeParking)
{
   std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< FREE_PARKING
		<< SEPARATOR		
		<< freeParking->getPosition()
		<< SEPARATOR				
		<< freeParking->getId();
						
    std::string message = oss.str();

	notifyCreation(message);
}

void SrvMessageManager::notifyCreation(SrvGoCase* goCase)
{
   std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< GO_CASE
		<< SEPARATOR		
		<< goCase->getPosition()
		<< SEPARATOR				
		<< goCase->getId();
						
    std::string message = oss.str();

	notifyCreation(message);
}

void SrvMessageManager::notifyCreation(SrvGoToJail* goToJail)
{
   std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< GO_TO_JAIL
		<< SEPARATOR		
		<< goToJail->getPosition()
		<< SEPARATOR				
		<< goToJail->getId();
						
    std::string message = oss.str();

	notifyCreation(message);
}

void SrvMessageManager::notifyCreation(SrvCommunityChest* communityChest)
{
   std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< COMMUNITY_CHEST
		<< SEPARATOR		
		<< communityChest->getPosition()
		<< SEPARATOR				
		<< communityChest->getLabel();
						
    std::string message = oss.str();

	notifyCreation(message);

}

void SrvMessageManager::notifyCreation(SrvCompany* company)
{
	// cration d'un flux de sortie
    std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< COMPANY
		<< SEPARATOR		
		<< company->getPosition()
		<< SEPARATOR				
		<< company->getLabel()
		<< SEPARATOR
		<< company->getPurchasePrice()
		<< SEPARATOR
		<< company->getId();


						
    std::string message = oss.str();

	notifyCreation(message);

}

void SrvMessageManager::notifyCreation(SrvJailCase* jail)
{
	// cration d'un flux de sortie
    std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< JAIL
		<< SEPARATOR
		<< jail->getPosition()	
		<< SEPARATOR
		<< jail->getId();
				
    std::string message = oss.str();

	notifyCreation(message);
}

void SrvMessageManager::notifyCreation(SrvTaxCase* tax)
{
	// cration d'un flux de sortie
    std::ostringstream oss;

    // écrire un nombre dans le flux
    oss << ACKNOWLEDGE_REQUIERED
		<< SEPARATOR
		<< CREATION	
		<< SEPARATOR
		<< TAX
		<< SEPARATOR
		<< tax->getPosition()
		<< SEPARATOR
		<< tax->getId()
		<< SEPARATOR
		<< tax->getLabel()
		<< SEPARATOR
		<< tax->getTaxAmount();
				
    std::string message = oss.str();

	notifyCreation(message);
}

void SrvMessageManager::askPlayersToChooseToken(std::vector<bool> &jetons)
{
	std::vector<SOCKET>* csock = _socket->getSockets();

	int playersCount = csock->size();

	for(int i=0; i<playersCount; i++)
	{
		std::ostringstream oss;

		// écrire un nombre dans le flux
		oss << ACKNOWLEDGE_NOT_REQUIERED
			<< SEPARATOR
			<< TOKEN_CHOICE;	

		for(int j=0; j< jetons.size(); j++)
		{
			oss << SEPARATOR		
				<< (jetons[i]? 1 : 0);
		}

		_socket->sendToClient(i,oss.str(), false);
		std::string tokenChoosen = _socket->receive(i);
		int jeton = atoi(tokenChoosen.c_str());

		jetons[i] = false;

		std::cout << "Le joueur " << i << "choisit le jeton n°" << jeton << std::endl;
		

	}

}