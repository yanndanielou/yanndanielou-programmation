#include "SrvStreet.h"

SrvStreet::SrvStreet(std::string id, std::string label,	 int position, std::string group,
					 int purchasePrice,  int emptyRent,  int a1FlatRent, int a2FlatRent,
					 int a3FlatRent, int a4FlatRent, int hotelRent,	 int flatPrice,	 int hotelPrice)
	:SrvCasePropriete(id, label,position, purchasePrice),
	_group(group),
	_emptyRent(emptyRent),
	_1FlatRent(a1FlatRent),
	_2FlatRent(a2FlatRent),
	_3FlatRent(a3FlatRent),
	_4FlatRent(a4FlatRent),
	_hotelRent(hotelRent),
	_flatPrice(flatPrice),
	_hotelPrice(hotelPrice),
	_FlatsCount(0),
	_hotelsCount(0)
{
}

SrvStreet::~SrvStreet(void)
{
}
