#pragma once
#include "hmicase.h"

class HmiCardCase :
	public HmiCase
{
public:
	HmiCardCase(QGraphicsScene* scene, int x, int y, int width, int height, std::string label, int type);
	~HmiCardCase(void);

	
	enum { CHANCE, COMMUNITY_CHEST};

private:
	QGraphicsPixmapItem* _pixmapItem;
	QPixmap*			 _pixmap;
	



};
