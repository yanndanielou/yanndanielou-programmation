#include "Case.h"

int Case::_indexActuel = 0;

Case::Case()
: _isFixed(false),
  _value(0)
{
	_index = _indexActuel;
	_indexActuel++;
}

Case::~Case(void)
{
}
