#ifndef HmiStreet_H
#define HmiStreet_H

#include <QtGui>
#include "HmiProperty.h"

class HmiStreet : public HmiProperty
{
public:
	HmiStreet(QGraphicsScene* scene, int x, int y, int width, int height, std::string label, std::string purchasePrice, QColor color);



private:
	QGraphicsRectItem* _groupColor;

	QGraphicsTextItem* _streetLabel;
};

#endif
