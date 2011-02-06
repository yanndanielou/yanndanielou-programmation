#pragma once
#include "SrvCasePropriete.h"

class SrvCompany :
	public SrvCasePropriete
{
public:
	SrvCompany(std::string id, std::string label, int position, int purchasePrice);
	~SrvCompany(void);
};
