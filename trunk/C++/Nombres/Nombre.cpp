#include "Nombre.h"

Nombre::Nombre(TYPE unit)
{
	if(unit)
		addDigit(unit);
}

Nombre::~Nombre(void)
{
}


///////////////Opérateurs arithmétiques//////////////////////////


/**************************************************************
 *	operator+  Nombre
 *  Retourne nb1+nb2
***************************************************************/
Nombre Nombre::operator+(const Nombre& nb)
{
	TYPE retenue = 0;
	Nombre ret;

	Nombre memNb1 = nb;
	Nombre memNb2 = *this;

	ret.clear();
	completeSmallerWithZero(memNb1, memNb2);

	//Les nombres n1 et n2 ont maintenant le même nombre de digits
	for(int i=0; i<memNb1.getDigitsCount(); i++)
	{
		TYPE sommeDigits = memNb1.getDigit(i) + memNb2.getDigit(i) + retenue;
		
		if(sommeDigits >= VALUE_MAX)
		{	
			sommeDigits -= VALUE_MAX;
			retenue = 1;
		}
		else
			retenue = 0;

		ret.addDigit(sommeDigits);
	}

	if(retenue)
		ret.addDigit(1);
	
	

	return ret;
}

/**************************************************************
 *	operator-  Nombre
 *  Operateur-. Retourne nb1-nb2
 *  Si le résultat est négatif, retourne 0
***************************************************************/
Nombre Nombre::operator-(const Nombre& nb)
{
	TYPE retenue = 0;
	Nombre ret;

	Nombre memNb1 = *this;
	Nombre memNb2 = nb;

	ret.clear();
	completeSmallerWithZero(memNb1, memNb2);

	if(memNb1 >= memNb2)
	{

		//Les nombres n1 et n2 ont maintenant le même nombre de digits
		for(int i=0; i<memNb1.getDigitsCount(); i++)
		{
			TYPE difference = memNb1.getDigit(i) - (memNb2.getDigit(i) + retenue);

			if(difference <0)
			{	
				difference += VALUE_MAX;
				retenue = 1;
			}
			else
				retenue = 0;

			ret.addDigit(difference);
		}

		if(retenue)
			ret.addDigit(1);
	}
	
	ret.clean();
	return ret;
}



/**************************************************************
 *	operator-  Nombre
 *  Operateur-. Retourne nb1-nb2
 *  Si le résultat est négatif, retourne 0
***************************************************************/
Nombre Nombre::operator*(int facteurInt)
{
	Nombre facteurNombre = *this;	
	Nombre produit;

	for(int i=0; i<facteurInt; i++)
	{
		produit = produit + facteurNombre;
	}

	return produit;
}




/**************************************************************
 *	operator-  Nombre
 *  Operateur-. Retourne nb1-nb2
 *  Si le résultat est négatif, retourne 0
***************************************************************/
Nombre Nombre::operator*(const Nombre& nb)
{

	Nombre low;
	Nombre high;	
	Nombre produit;

	if(*this >= nb)
	{
		low = nb;
		high = *this;
	}
	else
	{
		high = nb;
		low = *this;
	}



	if(low.getDigitsCount() == 1)
	{
		produit = high * low.getDigit(0);
		/*while(low > 0)
		{
			produit = produit + high;
			low = low -1;
		}*/
	}

	else
		{


		std::vector<Nombre> produits;

		//Parcours de la ligne du bas, donc du facteur le plus petit
		for(int i=0 ; i<low.getDigitsCount(); i++)
		{
			Nombre produitTemp;
			Nombre compteurDigit(1);
			Nombre UN(1);

			for(int j=0; j<high.getDigitsCount(); j++)
			{
				Nombre n1 = UN.power10(j);
				Nombre n2 = high.getDigit(j);
				Nombre n3 = low.getDigit(i);

				Nombre s1 = n1*n2*n3;
				produitTemp = produitTemp + s1;

				//produitTemp = produitTemp + low.getDigit(i) * high.getDigit(j) * compteurDigit.power10(i);			
				compteurDigit = compteurDigit + 1;
			}
			produits.push_back(produitTemp);
		}

		for(unsigned int i= 0; i < produits.size(); i++)
		{
			produit = produit + produits[i].power10(i); 
		}
	}

	return produit;
}



/**************************************************************
 *	operator-  Nombre
 *  Operateur-. Retourne nb1-nb2
 *  Si le résultat est négatif, retourne 0
***************************************************************/
Nombre Nombre::operator/(const Nombre& nb)
{
	Nombre quotient;

	Nombre diviseur = nb;
	Nombre dividande = *this;

	int nbDigitsDiviseurs = nb.getDigitsCount();
	int nbDigitsDividande = this->getDigitsCount();

	int nbDigitsDifference = nbDigitsDiviseurs>nbDigitsDividande? nbDigitsDiviseurs-nbDigitsDividande: nbDigitsDividande - nbDigitsDiviseurs;

	if(nbDigitsDifference <= 1) 
	{
	
		while(dividande >= diviseur)
		{
	//		PRINT(dividande);
	//		PRINT(diviseur);
			dividande = dividande - diviseur;
			quotient = quotient + 1;
		}
	}

	return quotient;
}


/*
const Nombre Nombre::operator++(int)
{
	const Nombre temp=const_cast<const Nombre>(*this); // copie de l'objet dans
	// son état actuel
	operator++(); // pre incrementation
	return temp;
}


Nombre & operator++(void)
{
	Nombre nb

}
*/

/**************************************************************
 *	operator-  Nombre
 *  Operateur-. Retourne nb1-nb2
 *  Si le résultat est négatif, retourne 0
***************************************************************/
Nombre Nombre::power10(int exposant)
{
	Nombre ret;

	for(int i=0; i<exposant; i++)
		ret.addDigit(0);

	for(int i=0; i<this->getDigitsCount(); i++)
	{
		ret.addDigit(this->getDigit(i));
	}

	return ret;
}


//Opérateurs de comparaison

/**************************************************************
 *	operator==  Nombre
 *  Compare deux Nombre, true si identiques
***************************************************************/
bool Nombre::operator==(const Nombre &nb)
{
	Nombre copyNb1 = *this;
	Nombre copyNb2 = nb;

	bool ret = true;

	completeSmallerWithZero(copyNb1, copyNb2);
	for(int i=0; i<copyNb1.getDigitsCount() && ret; i++)
	{
		if(copyNb1.getDigit(i) != copyNb2.getDigit(i))
		{
			ret = false;
		}
	}

	return ret;
}


/**************************************************************
 *	operator <  Nombre
 *  Compare deux Nombre, true si le this < nb
***************************************************************/
bool Nombre::operator<(const Nombre &nb)
{
	Nombre copyNb1 = *this;
	Nombre copyNb2 = nb;

	bool ret = true;

	if(copyNb1 == copyNb2)
		ret = false;
	else if(copyNb1 > copyNb2)
		ret = false;

	return ret;
}

/**************************************************************
 *	operator <  Nombre
 *  Compare deux Nombre, true si le this < nb
***************************************************************/
bool Nombre::operator<=(const Nombre &nb)
{
	Nombre copyNb1 = *this;
	Nombre copyNb2 = nb;

	return (copyNb1<copyNb2 || copyNb1==copyNb2);
}


/**************************************************************
 *	operator >  Nombre
 *  Compare deux Nombre, true si le this > nb
***************************************************************/
bool Nombre::operator>(const Nombre &nb)
{
	Nombre copyNb1 = *this;
	Nombre copyNb2 = nb;

	//Initialisé à false dans le cas où ils sont identiques
	bool ret = false;
	bool continu = true;

	completeSmallerWithZero(copyNb1, copyNb2);

	//On compare les MSB en premiers
	for(int i=copyNb1.getDigitsCount() -1; i >= 0 && continu; i--)
	{
		if(copyNb1.getDigit(i) > copyNb2.getDigit(i))
		{
			ret = true;
			continu = false;
		}
		else if(copyNb1.getDigit(i) < copyNb2.getDigit(i))
		{
			ret = false;
			continu = false;
		}
	}

	return ret;
}

/**************************************************************
 *	operator >  Nombre
 *  Compare deux Nombre, true si le this > nb
***************************************************************/
bool Nombre::operator>=(const Nombre &nb)
{
	Nombre copyNb1 = *this;
	Nombre copyNb2 = nb;

	return (copyNb1>copyNb2 || copyNb1==copyNb2);
}


///////////////Opérateurs de flux//////////////////////////////

/**************************************************************
 *	operator<< >  ostream
 *  Serialize un Nombre dans un ostream
***************************************************************/
std::ostream &operator<<(std::ostream &out, Nombre &nb)
{
	for(int i=nb.getDigitsCount() -1; i>=0; i--)
	{
		out << (int)nb.getDigit(i);
	}
    return out;
}


//////////////////////////Méthodes//////////////////////////////
void Nombre::addDigit(TYPE digitToAdd)
{
	_digits.push_back(digitToAdd);
}

/**************************************************************
 *	clear : ostream
 *  Supprime tous les digits d'un nombre
***************************************************************/
void Nombre::clear()
{
	_digits.clear();
}

/**************************************************************
 *	clean : ostream
 *  Supprime les digits 0 inutiles d'un nombre (ceux situés à droite du vecteur)
***************************************************************/
void Nombre::clean()
{
	bool again = true;

	for(int i=_digits.size()-1; i>=0 && again; i--)
	{
		if(_digits[i] == 0)
			_digits.pop_back();
		else
			again = false;
	}
}

void clean();

/**************************************************************
 *	completeSmallerWithZero : EXTERNE
 *  Insere des zeros dans le Nombre qui a le moins de digits
 *  Afin que les deux Nombres soient de la même longueur
***************************************************************/
void completeSmallerWithZero(Nombre & n1, Nombre & n2)
{
	int nbDigitsN1 = n1.getDigitsCount();
	int nbDigitsN2 = n2.getDigitsCount();

	if(nbDigitsN1 < nbDigitsN2)
	{
		for(int i=n1.getDigitsCount(); i<nbDigitsN2; i++)
		{
			n1.addDigit(0);
		}
	}
	else if(nbDigitsN2 < nbDigitsN1)
	{
		for(int i=n2.getDigitsCount(); i<nbDigitsN1; i++)
		{
			n2.addDigit(0);
		}
	}
}