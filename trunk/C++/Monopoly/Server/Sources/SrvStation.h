#pragma once
#include "SrvCasePropriete.h"

#include <vector>

class SrvStation :
	public SrvCasePropriete
{
public:
	SrvStation(std::string id, std::string label, int position, int purchasePrice);
	~SrvStation(void);

	static std::vector<SrvStation*> _listStations;
};
