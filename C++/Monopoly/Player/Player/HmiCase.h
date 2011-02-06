#pragma once

#include <QtGui>
#include "HmiPlayer.h"


class HmiCase
{
public:
	HmiCase(QGraphicsScene* scene, int x, int y, int width, int height);
	~HmiCase(void);

	std::vector<HmiPlayer*> _playerOnCase;

protected:	
	int _x;
	int _y;
	int _width;
	int _height;

	QGraphicsRectItem* _contour;
	QGraphicsTextItem* _label;

};
