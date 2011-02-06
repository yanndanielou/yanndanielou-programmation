#include "HmiCase.h"

HmiCase::HmiCase(QGraphicsScene* scene, int x, int y, int width, int height)
{
	QRect* rect = new QRect(x, y, width, height);
    QPen pen(Qt::black, 1, Qt::SolidLine);
    _contour = scene->addRect(*rect, pen);
    scene->setBackgroundBrush(Qt::white);

}

HmiCase::~HmiCase(void)
{
}
