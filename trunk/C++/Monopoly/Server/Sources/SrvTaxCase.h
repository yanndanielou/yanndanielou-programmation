#pragma once
#include "SrvCase.h"

class SrvTaxCase :
	public SrvCase
{
public:
	SrvTaxCase(std::string id, std::string label, int position, int taxAmount);
	~SrvTaxCase(void);

	int getTaxAmount(){
		return _taxAmount;};

private:
	int _taxAmount;
};
