#include "HmiTaxCase.h"

HmiTaxCase::HmiTaxCase(QGraphicsScene* scene, int x, int y, int width, int height, std::string id, std::string label, std::string taxAmount)
:HmiCase(scene, x, y, width, height)
{

	
	_label = scene->addText(QString(label.c_str()));
	_label->setPos(x ,y);


	std::string pictureFile("../../Images/" + id + ".jpg");

	_pixmap = new QPixmap(pictureFile.c_str());
	
	
	if(!_pixmap->isNull())
	{
		_pixmapItem = scene->addPixmap(*_pixmap);
		_pixmapItem->setPos(x, y + height * 0.2);

		
		float pictureHeight =  height * 0.6 -  height * 0.2;
		float pictureWidth =  width * 0.8;

		_pixmapItem->scale(pictureWidth / _pixmap->width() , pictureHeight / _pixmap->height());
	}

	_taxAmount = scene->addText(QString(taxAmount.c_str()));
	_taxAmount->setPos(x ,y + height * 0.6);



}

HmiTaxCase::~HmiTaxCase(void)
{
}
