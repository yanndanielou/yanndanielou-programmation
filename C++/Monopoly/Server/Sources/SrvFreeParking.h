#pragma once
#include "srvcase.h"

class SrvFreeParking :
	public SrvCase
{
public:
	SrvFreeParking(std::string id, std::string label, int position);
	~SrvFreeParking(void);
};
