#include "HmiCompany.h"

HmiCompany::HmiCompany(QGraphicsScene* scene, int x, int y, int width, int height, std::string label, std::string purchasePrice, std::string id)
:HmiProperty(scene, x, y, width, height, label, purchasePrice)
{
	
	_label->setPos(x ,y);


	std::string pictureFile("../../Images/" + id + ".jpg");	

	_pixmap = new QPixmap(pictureFile.c_str());

	_pixmap->scaled(QSize(10,10));


	_pixmapItem = scene->addPixmap(*_pixmap);

	_pixmapItem->setPos(x, y + height * 0.2);

	_pixmapItem->update( x, y + height * 0.2,   60,   60);


	_pixmapItem->setParentItem(_contour);


	
	float pictureHeight =  height * 0.6 -  height * 0.2;
	float pictureWidth =  width * 0.8;

	_pixmapItem->scale(pictureWidth / _pixmap->width() , pictureHeight / _pixmap->height());


}

HmiCompany::~HmiCompany(void)
{
}
