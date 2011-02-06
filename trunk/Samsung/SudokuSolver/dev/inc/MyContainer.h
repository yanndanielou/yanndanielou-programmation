#ifndef _MY_CONTAINER_H_
#define _MY_CONTAINER_H_

#include <vector>

class Case;


const int VALUE_MAX = 9;

class MyContainer
{
public:
	MyContainer(void);
	~MyContainer(void);

//	bool isValid();
	bool canAdd(int value);

	virtual void addCase(Case* toAdd);
	std::vector<Case*> getCases();

	int getId();

private:
	std::vector<Case*> _cases;
	int _id;
};


inline int MyContainer::getId(){
	return _id;}

inline std::vector<Case*> MyContainer::getCases(){
	return _cases;}


#endif //_MY_CONTAINER_H_
