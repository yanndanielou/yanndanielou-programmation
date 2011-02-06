#include "HmiCardCase.h"

HmiCardCase::HmiCardCase(QGraphicsScene* scene, int x, int y, int width, int height, std::string label, int type)
:HmiCase(scene, x, y, width, height)
{
	
	_label = scene->addText(QString(label.c_str()));
	_label->setPos(x ,y);


	std::string pictureFile;

	if(type == CHANCE)
		pictureFile = "../../Images/chance.jpg";
	else if(type == COMMUNITY_CHEST)
		pictureFile = "../../Images/community_chest.jpg";

	_pixmap = new QPixmap(pictureFile.c_str());
	_pixmapItem = scene->addPixmap(*_pixmap);
	
	_pixmapItem->setPos(x, y + height * 0.2);

	
	float pictureHeight =  height * 0.9 -  height * 0.2;
	float pictureWidth =  width * 0.8;

	_pixmapItem->scale(pictureWidth / _pixmap->width() , pictureHeight / _pixmap->height());



}

HmiCardCase::~HmiCardCase(void)
{
}
