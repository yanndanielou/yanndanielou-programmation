#pragma once

#include "iostream"

class CmObject
{
public:
	CmObject(std::string id, std::string label = "");
	~CmObject(void);

	std::string getId(){
		return _id;};

	std::string getLabel(){
		return _label;};

private:
	//Attributs
	std::string _id;
	std::string _label;
};
