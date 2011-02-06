#pragma once
#include "srvcase.h"

class SrvChance :
	public SrvCase
{
public:
	SrvChance(std::string id, std::string label, int position);
	~SrvChance(void);
};
