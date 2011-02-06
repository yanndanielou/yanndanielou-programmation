#include "HmiBigCase.h"

HmiBigCase::HmiBigCase(QGraphicsScene* scene, int x, int y, int width, int height, std::string id)
:HmiCase(scene, x, y, width, height)
{
	std::string pictureFile("../../Images/" + id + ".jpg");	

	_pixmap = new QPixmap(pictureFile.c_str());

	_pixmapItem = scene->addPixmap(*_pixmap);

	_pixmapItem->setPos(x, y);

	_pixmapItem->setParentItem(_contour);

	if(!_pixmap->isNull())
		_pixmapItem->scale((float)width / _pixmap->width() , (float)height / _pixmap->height());
}

HmiBigCase::~HmiBigCase(void)
{
}
