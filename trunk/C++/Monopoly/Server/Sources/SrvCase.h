#pragma once

#include "..\..\Comon\CmObject.h"

#include <vector>

class SrvCase: public CmObject
{
public:
	SrvCase(std::string id, std::string label, int position);
	~SrvCase(void);

	virtual void onArrival();

	/* Accesseurs */
	int getPosition(){
		return _position;};

private:
	static std::vector<SrvCase*>* _cases;
	int _position;

};
