#include "HmiProperty.h"

HmiProperty::HmiProperty(QGraphicsScene* scene, int x, int y, int width, int height, std::string label,  std::string purchasePrice)
: HmiCase(scene, x, y, width, height)
{

	_label = scene->addText(QString(label.c_str()));
	_label->setPos(x ,y + height * 0.2);

	_purchasePrice = scene->addText(QString(purchasePrice.c_str()));
	_purchasePrice->setPos(x ,y + height * 0.6);

	
	_label->setParentItem(_contour);
	_purchasePrice->setParentItem(_contour);

}

HmiProperty::~HmiProperty(void)
{
}
