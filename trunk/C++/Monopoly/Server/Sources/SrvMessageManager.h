#pragma once

#include "SrvStreet.h"
#include "SrvStation.h"
#include "SrvChance.h"
#include "SrvCommunityChest.h"
#include "SrvJailCase.h"
#include "SrvCompany.h"
#include "SrvTaxCase.h"
#include "SrvFreeParking.h"
#include "SrvGoToJail.h"
#include "SrvGoCase.h"



#include "SrvSocket.h"


#include "../../Comon/CmCommunicationProtocol.h"

class SrvMessageManager
{
public:
	SrvMessageManager(void);
	~SrvMessageManager(void);

	static SrvMessageManager* getInstance();

	void notifyCreation(std::string message);
	void notifyCreation(SrvStreet* street);
	void notifyCreation(SrvStation* station);
	void notifyCreation(SrvChance* chance);
	void notifyCreation(SrvCommunityChest* communityChest);
	void notifyCreation(SrvJailCase* jail);
	void notifyCreation(SrvCompany* company);
	void notifyCreation(SrvTaxCase* tax);
	void notifyCreation(SrvFreeParking* freeParking);
	void notifyCreation(SrvGoToJail* goToJail);
	void notifyCreation(SrvGoCase* goCase);

	void askPlayersToChooseToken(std::vector<bool> &jetons);

private:
	static SrvMessageManager* _instance;

	SrvSocket* _socket;
};
