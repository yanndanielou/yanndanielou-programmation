#include "SrvGameBoard.h"


#include "SrvMessageManager.h"


SrvGameBoard* SrvGameBoard::_instance = 0;


SrvGameBoard* SrvGameBoard::getInstance()
{
	if(_instance)
		return _instance;
	else
	{
		return new SrvGameBoard("plateau", "plateau de jeu");
	}
}

SrvGameBoard::SrvGameBoard(std::string id, std::string label)
:CmObject(id, label)
{
	_instance = this;
	_cases = new std::vector<SrvCase*>;
	_players = new std::vector<SrvPlayer*>;

	for(int i=0; i < NB_PIONS; i++)
		_isPionFree.push_back(true);


	SrvMessageManager::getInstance()->askPlayersToChooseToken(_isPionFree);
	//SrvMessageManager::getInstance()->notifyCreation("ok");
}

SrvGameBoard::~SrvGameBoard(void)
{
}


void SrvGameBoard::addCase(SrvCase* toAdd)
{
	_cases->push_back(toAdd);
}

int SrvGameBoard::getCasesCount()
{
	return (int)_cases->size();
}

void SrvGameBoard::addPlayer(SrvPlayer* toAdd)
{
	_players->push_back(toAdd);
}