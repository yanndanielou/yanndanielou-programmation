#pragma once
#include "srvcase.h"

class SrvJailCase :
	public SrvCase
{
public:
	SrvJailCase(std::string id, std::string label, int position);
	~SrvJailCase(void);
};
