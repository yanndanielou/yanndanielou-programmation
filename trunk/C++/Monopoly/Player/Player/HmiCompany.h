#pragma once
#include "hmiproperty.h"

class HmiCompany :
	public HmiProperty
{
public:
	HmiCompany(QGraphicsScene* scene, int x, int y, int width, int height, std::string label, std::string purchasePrice, std::string id);
	~HmiCompany(void);

private: 
	QGraphicsPixmapItem* _pixmapItem;
	QPixmap* _pixmap;
};
