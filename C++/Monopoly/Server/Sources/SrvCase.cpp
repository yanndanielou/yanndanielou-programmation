#include "SrvCase.h"

std::vector<SrvCase*>* SrvCase::_cases = 0;

SrvCase::SrvCase(std::string id, std::string label, int position)
: CmObject(id, label),
_position(position)

{
	if(!_cases)
		_cases = new std::vector<SrvCase*>;

	_cases->push_back(this);
}

SrvCase::~SrvCase(void)
{
}

void SrvCase::onArrival(void)
{
}
