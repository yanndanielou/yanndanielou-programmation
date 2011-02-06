#include "SrvBuilder.h"
#include "SrvGameBoard.h"
#include "SrvJailCase.h"
#include "SrvGoCase.h"
#include "SrvTaxCase.h"
#include "SrvCommunityChest.h"
#include "SrvChance.h"
#include "SrvCompany.h"
#include "SrvStation.h"
#include "SrvFreeParking.h"
#include "SrvGoToJail.h"

#include "SrvMessageManager.h"

SrvCaseBuilder::SrvCaseBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	
	SrvCase* newCase = new SrvCase(id, label, position);

	SrvGameBoard::getInstance()->addCase(newCase);
	//SrvMessageManager::getInstance()->notifyCreation(newCase);
}

SrvFreeParkingBuilder::SrvFreeParkingBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	
	SrvFreeParking* newFreeParking = new SrvFreeParking(id, label, position);

	SrvGameBoard::getInstance()->addCase(newFreeParking);
	SrvMessageManager::getInstance()->notifyCreation(newFreeParking);
}


SrvGoToJailBuilder::SrvGoToJailBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	
	SrvGoToJail* newGoToJail = new SrvGoToJail(id, label, position);

	SrvGameBoard::getInstance()->addCase(newGoToJail);
	SrvMessageManager::getInstance()->notifyCreation(newGoToJail);
}

SrvStreetBuilder::SrvStreetBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	std::string gpId(row[GROUPEMENT_ID]);
	int purchasePrice(atoi(row[PURCHASE_PRICE].c_str()));
	int emptyRent(atoi(row[EMTPY_RENT].c_str()));
	int a1flatRent(atoi(row[A1_FLAT_RENT].c_str()));
	int a2flatRent(atoi(row[A2_FLAT_RENT].c_str()));
	int a3flatRent(atoi(row[A3_FLAT_RENT].c_str()));
	int a4flatRent(atoi(row[A4_FLAT_RENT].c_str()));
	int hotelRent(atoi(row[HOTEL_RENT].c_str()));
	int flatPrice(atoi(row[FLAT_PRICE].c_str()));
	int hotelPrice(atoi(row[HOTEL_PRICE].c_str()));

	SrvStreet* newStreet = new SrvStreet(id,
										label,
										position,
										gpId,
										purchasePrice,
										emptyRent,
										a1flatRent,
										a2flatRent,
										a3flatRent,
										a4flatRent,
										hotelRent,
										flatPrice,
										hotelPrice);

	SrvGameBoard::getInstance()->addCase(newStreet);

	SrvMessageManager::getInstance()->notifyCreation(newStreet);
}

SrvJailCaseBuilder::SrvJailCaseBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	
	SrvJailCase* newJail = new SrvJailCase(id, label, position);

	SrvGameBoard::getInstance()->addCase(newJail);

	SrvMessageManager::getInstance()->notifyCreation(newJail);
}

SrvCompanyBuilder::SrvCompanyBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	int purchasePrice(atoi(row[PURCHASE_PRICE].c_str()));
	
	SrvCompany* newCompany = new SrvCompany(id, label, position, purchasePrice);

	SrvGameBoard::getInstance()->addCase(newCompany);

	SrvMessageManager::getInstance()->notifyCreation(newCompany);
}


SrvGoCaseBuilder::SrvGoCaseBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	
	SrvGoCase* newGo = new SrvGoCase(id, label, position);

	SrvGameBoard::getInstance()->addCase(newGo);
	SrvMessageManager::getInstance()->notifyCreation(newGo);
}

SrvTaxCaseBuilder::SrvTaxCaseBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	int taxAmount(atoi(row[TAX_AMOUNT].c_str()));
	
	SrvTaxCase* newTax = new SrvTaxCase(id, label, position, taxAmount);

	SrvGameBoard::getInstance()->addCase(newTax);

	SrvMessageManager::getInstance()->notifyCreation(newTax);
}

SrvStationBuilder::SrvStationBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	int purchasePrice(atoi(row[PURCHASE_PRICE].c_str()));
	
	SrvStation* newStation = new SrvStation(id, label, position, purchasePrice);

	SrvGameBoard::getInstance()->addCase(newStation);

	SrvMessageManager::getInstance()->notifyCreation(newStation);
}

SrvCommunityChestBuilder::SrvCommunityChestBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	
	SrvCommunityChest* newCommunityChest = new SrvCommunityChest(id, label, position);

	SrvGameBoard::getInstance()->addCase(newCommunityChest);

	SrvMessageManager::getInstance()->notifyCreation(newCommunityChest);
}

SrvChanceBuilder::SrvChanceBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	int position(atoi(row[POSITION].c_str()));
	
	SrvChance* newChance = new SrvChance(id, label, position);

	SrvGameBoard::getInstance()->addCase(newChance);

	SrvMessageManager::getInstance()->notifyCreation(newChance);
}

SrvPlayerBuilder::SrvPlayerBuilder(std::vector<std::string> row)
{
	std::string id(row[ID]);
	std::string label(row[LABEL]);
	
	SrvPlayer* player = new SrvPlayer(id, label);
	
	SrvGameBoard::getInstance()->addPlayer(player);
}
