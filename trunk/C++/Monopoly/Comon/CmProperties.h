#pragma once

#include "iostream"

#define ESPACE ' '
#define DEBUT_COMMENTAIRE ';'
#define SEPARATEUR '='

class CmProperties
{
public:
	CmProperties();
	~CmProperties();
	static CmProperties* getInstance();

	void setFileName(std::string fileName);

	void setBoolProperty(char* propertyName, bool value);
	void setStringProperty(char* propertyName, char* value);
	std::string getStringProperty(std::string propertyName, std::string defaultValue = "");
	bool getBoolProperty(std::string propertyName, bool defaultValue = true);
	int getIntProperty(std::string clef, int defaultValue = 0);
	char getCharProperty(std::string clef, char defaultValue);

private:
	static CmProperties* _instance;

	std::string _fileName;
};
