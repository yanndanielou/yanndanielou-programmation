#pragma once
#include "srvcase.h"

class SrvGoCase :
	public SrvCase
{
public:
	SrvGoCase(std::string id, std::string label, int position);
	~SrvGoCase(void);
};
