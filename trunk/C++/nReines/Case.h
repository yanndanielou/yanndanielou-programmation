#pragma once

#include "Colonne.h"
#include "Ligne.h"
//#include "Diagonale.h"

class Case
{
public:
	Case(void);
	~Case(void);

	void setLigne(Ligne * ligne);
	void setColonne(Colonne * colonne);
	//void setDiagonale(Diagonale * diagonale);

	Ligne* getLigne();
	Colonne* getColonne();
	//Diagonale* getDiagonale();

	int getValue();
	void setValue(int valeur, bool isFixed = false);
	bool isFixed();

private:
	static int _indexActuel;

	int _value;
	int _index;

	bool _isFixed;

	Colonne* _colonne;
	Ligne* _ligne;
	//Diagonale* _diagonale;

};


/*
inline void Case::setDiagonale(Diagonale* diagonale){
	_diagonale = diagonale;}
*/
inline void Case::setColonne(Colonne * colonne){
	_colonne = colonne;}

inline void Case::setLigne(Ligne * ligne){
	_ligne = ligne;}

inline int Case::getValue(){
	return _value;}

inline Ligne* Case::getLigne(){
	return _ligne;}

inline Colonne* Case::getColonne(){
	return _colonne;}
/*
inline Diagonale* Case::getDiagonale(){
	return _diagonale;}
*/
inline void Case::setValue(int valeur, bool isFixed){
	_value = valeur;
	_isFixed = isFixed;}

inline bool Case::isFixed(){
	return _isFixed;}

