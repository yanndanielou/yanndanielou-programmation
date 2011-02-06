#include "SrvCasePropriete.h"

SrvCasePropriete::SrvCasePropriete(std::string id, std::string label, int position, int purchasePrice)
: SrvCase(id, label, position),
  _purchasePrice(purchasePrice),
  _isMortgaged(false),
  _owner(0),
  _mortgageValue(purchasePrice/2)
{
}

SrvCasePropriete::~SrvCasePropriete(void)
{
}
