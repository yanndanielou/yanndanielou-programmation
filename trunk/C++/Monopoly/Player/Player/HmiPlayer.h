#pragma once

#include "..\..\Comon\CmObject.h"

class HmiPlayer :
	public CmObject
{
public:
	HmiPlayer(std::string id, std::string label);
	~HmiPlayer(void);
};
