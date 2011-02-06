#ifndef PLAYER_H
#define PLAYER_H

#include <QtGui/QMainWindow>
#include "ui_player.h"

class Player : public QMainWindow
{
	Q_OBJECT

public:
	Player(QWidget *parent = 0, Qt::WFlags flags = 0);
	~Player();

private:
	Ui::PlayerClass ui;
};

#endif // PLAYER_H
