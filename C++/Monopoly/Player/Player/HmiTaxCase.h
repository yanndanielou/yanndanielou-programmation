#pragma once
#include "hmicase.h"

class HmiTaxCase :
	public HmiCase
{
public:
	HmiTaxCase(QGraphicsScene* scene, int x, int y, int width, int height, std::string id, std::string label, std::string taxAmount);
	~HmiTaxCase(void);

protected:	
	QGraphicsPixmapItem* _pixmapItem;
	QPixmap* _pixmap;

	
	QGraphicsTextItem* _taxAmount;


};
