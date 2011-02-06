#pragma once
#include "SrvCasePropriete.h"

class SrvStreet :
	public SrvCasePropriete
{
public:
	SrvStreet(  std::string id,
				std::string label,
				int position,
				std::string gpId,
				int purchasePrice,
				int emptyRent, 
				int a1FlatRent,
				int a2FlatRent,
				int a3FlatRent,
				int a4FlatRent,
				int hotelRent,
				int flatPrice,
				int hotelPrice);
	~SrvStreet(void);



	/*Accesseurs*/
	std::string getGroup(){
		return _group;}

private:
	//Différents prix possibles que doit payer un visiteur au propriétaire
	int _emptyRent;
	int _1FlatRent;
	int _2FlatRent;
	int _3FlatRent;
	int _4FlatRent;
	int _hotelRent;

	//Prix que doit payer le propriétaire pour construire
	int _flatPrice;
	int _hotelPrice;

	//Nombre de constructions faites
	int _hotelsCount;
	int _FlatsCount;

	//La couleur (le regroupement)
	std::string _group;
};
