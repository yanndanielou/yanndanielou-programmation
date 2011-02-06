#include "HmiStreet.h"

HmiStreet::HmiStreet(QGraphicsScene* scene, int x, int y, int width, int height, std::string label, std::string purchasePrice, QColor color)
  : HmiProperty(scene, x, y, width, height, label, purchasePrice)
{

	QRect* rect = new QRect(x, y, width, height*0.2);
    QPen pen(Qt::black, 1, Qt::SolidLine);
	QBrush brush(color);
    _groupColor = scene->addRect(*rect, pen, brush);


}
