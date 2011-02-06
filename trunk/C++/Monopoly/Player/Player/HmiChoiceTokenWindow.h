#pragma once
#include "c:\qt\4.6.0\src\gui\dialogs\qdialog.h"

#include <QtGui>

class HmiChoiceTokenWindow :
	public QDialog
{

    Q_OBJECT

public:
	HmiChoiceTokenWindow(QWidget* parent, std::vector<bool> tokens);
	~HmiChoiceTokenWindow(void);

private slots:
	void tokenChoosen();

private:
	std::vector<QPushButton*> _tokens;


};
