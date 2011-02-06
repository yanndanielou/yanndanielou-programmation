#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QtGui>

#include "HmiStreet.h"
#include "HmiCase.h"
#include "HmiStation.h"
#include "HmiJail.h"
#include "HmiCompany.h"
#include "HmiTaxCase.h"
#include "HmiBigCase.h"
#include "HmiGameBoard.h"

#include "HmiSocket.h"

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = 0);
    ~MainWindow();

	void addStreet(std::string s_color, std::string streetName, std::string prix);
	void addBigCase(std::string id);
	void addStation(std::string label, std::string prix);
	void addCompanyCase(std::string id, std::string label, std::string prix);
	void addCardCase(std::string label, int type);
	void addTaxCase(std::string id, std::string label, std::string taxAmount);

	 
public slots:
    void waitOrder();


private:

	void addCase();
	void defineCoordonates(int &x, int &y, int &width, int &height);

	QColor stringToColor(std::string stringColor);

	QWidget* _centralArea;
	QGridLayout *_centralAreaLayout;

	HmiGameBoard* _gameBoard;

    void creerMenu();
	std::vector<HmiStreet*> _streets;
	std::vector<HmiCase*> _cases;
	std::vector<HmiStation*> _stations;

	HmiSocket* _socket;

	
	QGraphicsScene* _scene;
	QGraphicsView* _vue;

	int _casesCounter;
	int _casesNumber;

	int _width;
	int _height;


};

#endif // MAINWINDOW_H
