#pragma once
#include "hmiproperty.h"

class HmiStation :
	public HmiProperty
{
public:
	HmiStation(QGraphicsScene* scene, int x, int y, int width, int height, std::string label,  std::string purchasePrice);
	~HmiStation(void);

private:
	QGraphicsPixmapItem* _pixmapItem;
	QPixmap*			 _pixmap;
};
