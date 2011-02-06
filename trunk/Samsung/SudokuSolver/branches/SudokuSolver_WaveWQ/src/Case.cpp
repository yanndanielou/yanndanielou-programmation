#include "Case.h"

int Case::_indexActuel = 0;

Case::Case()
: _value(0),
 _isFixed(false),
 _column(0),
 _row(0),
 _square(0)
{
	_index = _indexActuel;
	_indexActuel++;
}

Case::~Case(void)
{
}


bool Case::canAdd(int value)
{
 return _row->canAdd(value)    &&
		_column->canAdd(value) &&
	    _square->canAdd(value);
}
