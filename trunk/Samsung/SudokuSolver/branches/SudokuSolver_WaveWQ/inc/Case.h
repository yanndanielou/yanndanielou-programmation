#ifndef _CASE_H_
#define _CASE_H_

#include "MyContainer.h"

class Case
{
public:
	Case(void);
	~Case(void);

	void setRow(MyContainer * row);
	void setColumn(MyContainer * column);
	void setSquare(MyContainer * square);

	MyContainer* getRow();
	MyContainer* getColumn();
	MyContainer* getSquare();

	int getValue();
	void setValue(int valeur, bool isFixed = false);
	bool isFixed();

	bool canAdd(int value);

private:
	static int _indexActuel;

	int _value;
	int _index;

	bool _isFixed;

	MyContainer* _column;
	MyContainer* _row;
	MyContainer* _square;

};



inline void Case::setSquare(MyContainer* square){
	_square = square;}

inline void Case::setColumn(MyContainer * column){
	_column = column;}

inline void Case::setRow(MyContainer * row){
	_row = row;}

inline int Case::getValue(){
	return _value;}

inline MyContainer* Case::getRow(){
	return _row;}

inline MyContainer* Case::getColumn(){
	return _column;}

inline MyContainer* Case::getSquare(){
	return _square;}

inline void Case::setValue(int valeur, bool isFixed){
	_value = valeur;
	_isFixed = isFixed;}

inline bool Case::isFixed(){
	return _isFixed;}


#endif //_CASE_H_
