#pragma once

#include <vector>

#include "..\..\comon\cmobject.h"

#include "SrvCase.h"
#include "SrvPlayer.h"

#define NB_PIONS 5

class SrvGameBoard :
	public CmObject
{
public:

	static SrvGameBoard* getInstance();
	SrvGameBoard(std::string id, std::string label = "");
	~SrvGameBoard(void);

	void addCase(SrvCase* toAdd);
	void addPlayer(SrvPlayer* toAdd);

	int getCasesCount();

	bool isThisPionUsed(int pion){
		return _isPionFree[pion];};

private:
	static SrvGameBoard* _instance;

	std::vector<SrvCase*> *_cases;
	std::vector<SrvPlayer*> *_players;

	std::vector<bool> _isPionFree;

};
