#include "SrvTaxCase.h"

SrvTaxCase::SrvTaxCase(std::string id, std::string label, int position, int taxAmount)
:SrvCase(id,label, position),
_taxAmount(taxAmount)
{
}

SrvTaxCase::~SrvTaxCase(void)
{
}
