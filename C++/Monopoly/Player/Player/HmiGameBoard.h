#pragma once

#include <QtGui>

class HmiGameBoard
{
public:
	HmiGameBoard(QGraphicsScene* scene, int sceneWidth, int sceneHeight);
	~HmiGameBoard(void);

private:
	QGraphicsPixmapItem* _chanceCardPilePixmapItem;
	QPixmap* _chanceCardPilePixmap;
	
	QGraphicsPixmapItem* _communityChestCardPilePixmapItem;
	QPixmap* _communityChestCardPilePixmap;
};
