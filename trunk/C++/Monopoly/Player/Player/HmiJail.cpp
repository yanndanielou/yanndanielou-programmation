#include "HmiJail.h"

HmiJail::HmiJail(QGraphicsScene* scene, int x, int y, int width, int height)
:HmiCase(scene, x, y, width, height)
{

	std::string pictureFile("../../Images/prison.jpg");	
}

HmiJail::~HmiJail(void)
{
}
