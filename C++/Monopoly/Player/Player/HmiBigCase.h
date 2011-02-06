#pragma once
#include "hmicase.h"

class HmiBigCase :
	public HmiCase
{
public:
	HmiBigCase(QGraphicsScene* scene, int x, int y, int width, int height, std::string id);
	~HmiBigCase(void);

protected:
	QGraphicsPixmapItem* _pixmapItem;
	QPixmap* _pixmap;
};
