#pragma once
#include "SrvCase.h"
#include "SrvPlayer.h"

class SrvCasePropriete :
	public SrvCase
{
public:
	SrvCasePropriete(std::string id, std::string label, int position, int purchasePrice);
	~SrvCasePropriete(void);

	int getPurchasePrice(){
		return _purchasePrice;};

private:
	int _purchasePrice;

	//Hypothèque
	int _mortgageValue;
	bool _isMortgaged;

	SrvPlayer* _owner;
};
