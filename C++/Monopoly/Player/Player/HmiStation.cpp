#include "HmiStation.h"

HmiStation::HmiStation(QGraphicsScene* scene, int x, int y, int width, int height, std::string label,  std::string purchasePrice)
:HmiProperty(scene, x, y, width, height, label, purchasePrice)
{

	_label->setPos(x ,y);

	std::string pictureFile("../../Images/station.jpg");

	_pixmap = new QPixmap(pictureFile.c_str());

	_pixmapItem = scene->addPixmap(*_pixmap);
	
	_pixmapItem->setPos(x, y + height * 0.2);

	float pictureHeight =  height * 0.6 -  height * 0.2;
	float pictureWidth =  width * 0.8;

	_pixmapItem->scale(pictureWidth / _pixmap->width() , pictureHeight / _pixmap->height());

}

HmiStation::~HmiStation(void)
{
}
