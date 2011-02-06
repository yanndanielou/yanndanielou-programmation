#pragma once

#include <vector>

class SrvFactory
{
public:
	SrvFactory(void);
	~SrvFactory(void);
	static SrvFactory* getInstance();

	void readDatasource();
	void build(std::vector<std::string> row);
	void read(std::string fileName);

	void createDatasource();
	void createCases();

private:
	static SrvFactory* _instance;
	char	_separator;
};

//void split(std::string, char separator, std::vector<std::string>* row);