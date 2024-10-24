#pragma once

#include "Colonne.h"
#include "Ligne.h"
#include "Carre.h"

class Case
{
public:
	Case(void);
	~Case(void);

	void setLigne(Ligne * ligne);
	void setColonne(Colonne * colonne);
	void setCarre(Carre * carre);

	Ligne* getLigne();
	Colonne* getColonne();
	Carre* getCarre();

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
	Carre* _carre;

};



inline void Case::setCarre(Carre* carre){
	_carre = carre;}

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

inline Carre* Case::getCarre(){
	return _carre;}

inline void Case::setValue(int valeur, bool isFixed){
	_value = valeur;
	_isFixed = isFixed;}

inline bool Case::isFixed(){
	return _isFixed;}

