#include "HmiGameBoard.h"

HmiGameBoard::HmiGameBoard(QGraphicsScene* scene, int sceneWidth, int sceneHeight)
{

	//Affichage des paquets

	int tasWidth = sceneWidth * 0.2;
	int tasHeight = sceneHeight * 0.2;


	_chanceCardPilePixmap =  new QPixmap("../../Images/tas_chance.jpg");
	_communityChestCardPilePixmap =  new QPixmap("../../Images/tas_communit_chest.jpg");

	_chanceCardPilePixmap->scaled(QSize((float) tasWidth/sceneWidth, (float) tasHeight / sceneHeight));
	_communityChestCardPilePixmap->scaled(QSize((float) tasWidth/sceneWidth, (float) tasHeight / sceneHeight));

	_chanceCardPilePixmapItem = scene->addPixmap(*_chanceCardPilePixmap);
	_chanceCardPilePixmapItem->setPos(sceneWidth * 0.2, sceneHeight * 0.2);

	_communityChestCardPilePixmapItem = scene->addPixmap(*_communityChestCardPilePixmap);
	_communityChestCardPilePixmapItem->setPos(sceneWidth * 0.6, sceneHeight * 0.6);
}

HmiGameBoard::~HmiGameBoard(void)
{
}
