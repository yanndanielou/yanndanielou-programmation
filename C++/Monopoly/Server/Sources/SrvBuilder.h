#pragma once

#include <vector>

#include "SrvStreet.h"




class SrvCaseBuilder
{
	enum{CLASSE,ID,LABEL,POSITION};

public:
	SrvCaseBuilder(std::vector<std::string> row);
};

class SrvStreetBuilder
{
	enum{CLASSE,ID,LABEL,POSITION,GROUPEMENT_ID,PURCHASE_PRICE,EMTPY_RENT,A1_FLAT_RENT,A2_FLAT_RENT,A3_FLAT_RENT,A4_FLAT_RENT,HOTEL_RENT,FLAT_PRICE,HOTEL_PRICE};

public:
	SrvStreetBuilder(std::vector<std::string> row);
};

class SrvJailCaseBuilder
{
	enum{CLASSE,ID,LABEL,POSITION};

public:
	SrvJailCaseBuilder(std::vector<std::string> row);
};

class SrvCompanyBuilder
{
	enum{CLASSE,ID,LABEL,POSITION, PURCHASE_PRICE};

public:
	SrvCompanyBuilder(std::vector<std::string> row);
};

class SrvGoCaseBuilder
{
	enum{CLASSE,ID,LABEL,POSITION};

public:
	SrvGoCaseBuilder(std::vector<std::string> row);
};

class SrvFreeParkingBuilder
{
	enum{CLASSE,ID,LABEL,POSITION};

public:
	SrvFreeParkingBuilder(std::vector<std::string> row);
};

class SrvGoToJailBuilder
{
	enum{CLASSE,ID,LABEL,POSITION};

public:
	SrvGoToJailBuilder(std::vector<std::string> row);
};

class SrvChanceBuilder
{
	enum{CLASSE,ID,LABEL,POSITION};

public:
	SrvChanceBuilder(std::vector<std::string> row);
};

class SrvTaxCaseBuilder
{
	enum{CLASSE,ID,LABEL,POSITION, TAX_AMOUNT};

public:
	SrvTaxCaseBuilder(std::vector<std::string> row);
};

class SrvStationBuilder
{
	enum{CLASSE,ID,LABEL,POSITION, PURCHASE_PRICE};

public:
	SrvStationBuilder(std::vector<std::string> row);
};

class SrvCommunityChestBuilder
{
	enum{CLASSE,ID,LABEL,POSITION};

public:
	SrvCommunityChestBuilder(std::vector<std::string> row);
};

class SrvPlayerBuilder
{
	enum{CLASSE,ID,LABEL};
public:
	SrvPlayerBuilder(std::vector<std::string> row);
};