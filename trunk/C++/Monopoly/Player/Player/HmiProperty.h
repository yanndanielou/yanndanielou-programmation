#pragma once
#include "hmicase.h"

class HmiProperty :
	public HmiCase
{
public:
	HmiProperty(QGraphicsScene* scene, int x, int y, int width, int height, std::string label, std::string purchasePrice);
	~HmiProperty(void);

protected:
	QGraphicsTextItem* _purchasePrice;
};
