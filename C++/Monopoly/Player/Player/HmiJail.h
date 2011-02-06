#pragma once
#include "hmicase.h"

class HmiJail :
	public HmiCase
{
public:
	HmiJail(QGraphicsScene* scene, int x, int y, int width, int height);
	~HmiJail(void);

private:
	/*
	QLabel* _justVisiting;
	QLabel* _inJail;*/
	
	QLabel* _picture;
};
