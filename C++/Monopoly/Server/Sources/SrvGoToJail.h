#pragma once
#include "srvcase.h"

class SrvGoToJail :
	public SrvCase
{
public:
	SrvGoToJail(std::string id, std::string label, int position);
	~SrvGoToJail(void);
};
