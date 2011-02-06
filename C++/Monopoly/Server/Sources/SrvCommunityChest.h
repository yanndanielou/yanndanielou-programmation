#pragma once
#include "srvcase.h"

class SrvCommunityChest :
	public SrvCase
{
public:
	SrvCommunityChest(std::string id, std::string label, int position);
	~SrvCommunityChest(void);
};
