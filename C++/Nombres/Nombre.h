#pragma once

#include <iostream>
#include <vector>
#include <cmath>                    // for sqrt() and pow()



#define PRINT(P) std::cout<<#P<<" = "<<P<<std::endl;

typedef char TYPE;
const char VALUE_MAX=10;


class Nombre
{
public:
	Nombre(TYPE unit=0);
	~Nombre(void);

	void addDigit(TYPE digitToAdd);

	Nombre operator+(const Nombre& nb);
	Nombre operator-(const Nombre& nb);
	Nombre operator*(const Nombre& nb);
	Nombre operator*(int facteurInt);
	Nombre operator/(const Nombre& nb);


	Nombre power10(int exposant);

	/*Nombre & operator++(void); // pré incrémentation
	const Nombre operator++(int); // post incrémentation*/
	
	//Opérateurs de comparaison
	bool operator==(const Nombre &nb);
	bool operator<(const Nombre &nb);
	bool operator<=(const Nombre &nb);
	bool operator>(const Nombre &nb);
	bool operator>=(const Nombre &nb);
	
	std::vector<TYPE> getDigits() const;
	TYPE getDigit(int position) const;
	int getDigitsCount() const;

	//Efface le vecteurs des digits
	void clear();

	//Supprime les digits à 0 inutils
	void clean();

	//Externes
	friend void completeSmallerWithZero(Nombre & n1, Nombre & n2);	
	friend std::ostream &operator<<(std::ostream &out, Nombre &nb);



private:
	//Attributs
	std::vector<TYPE> _digits;
};

inline 	TYPE Nombre::getDigit(int position) const{
	return _digits.at(position);}

inline 	std::vector<TYPE> Nombre::getDigits() const{
	return _digits;}

inline int Nombre::getDigitsCount() const{
	return _digits.size();}
