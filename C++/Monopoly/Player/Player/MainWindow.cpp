#include "MainWindow.h"

#include "../../Comon/Miscellaneous.cpp"
#include "../../Comon/CmCommunicationProtocol.h"

#include "HmiCardCase.h"
#include "HmiChoiceTokenWindow.h"



MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent),
	_casesCounter(0),
	_casesNumber(40),
	_width(1200),
	_height(800)
{

	_scene =  new QGraphicsScene;
	
	_vue = new QGraphicsView(_scene, this);

	//_scene->setFixedSize(_width - 100, _height - 100);
	_vue->setFixedSize(1.2*_width, 1.2*_height);

	//setFixedSize(_width, _height);

    //Création de la barre d'état
    QStatusBar *barreEtat = statusBar();

    creerMenu();


	_gameBoard = new HmiGameBoard(_scene, _width, _height);

    //setFixedSize(_width,_height);

   //_centralAreaLayout = new QGridLayout();

   // _centralArea = new QWidget;
   // setCentralWidget(_centralArea);

    //_centralArea->setLayout(_centralAreaLayout);

	
	

	QTimer *timer = new QTimer(this);
    connect(timer, SIGNAL(timeout()), this, SLOT(waitOrder()));
    timer->start(100);

	_socket = HmiSocket::getInstance();

}

MainWindow::~MainWindow()
{
}


void MainWindow::creerMenu()
{
    //Menu Fichier
    QMenu *menuFichier = menuBar()->addMenu("&Fichier");
    QAction *actionQuitter = menuFichier->addAction("&Quitter");
    actionQuitter->setShortcut(QKeySequence("Ctrl+Q"));
    actionQuitter->setStatusTip("Quitte le programme");
    connect(actionQuitter, SIGNAL(triggered()), qApp, SLOT(quit()));
}


void MainWindow::addCase()
{
//	int nbCases = 28;
}



void MainWindow::addStreet(std::string s_color, std::string streetName, std::string price)
{
	if(_casesCounter >= _casesNumber)
		return;

	int x, y, width, height;
	defineCoordonates(x, y, width, height);


	HmiStreet* newStreet = new HmiStreet(_scene, x, y, width, height, streetName, price, stringToColor(s_color));
	_streets.push_back(newStreet);
	_cases.push_back(newStreet);

	addCase();
}


void MainWindow::addBigCase(std::string id)
{
	if(_casesCounter >= _casesNumber)
		return;

	int x, y, width, height;
	defineCoordonates(x, y, width, height);

	HmiBigCase* newBigCase = new HmiBigCase(_scene, x, y, width, height, id);
	_cases.push_back(newBigCase);

	addCase();
}


void MainWindow::addCompanyCase(std::string id, std::string label, std::string price)
{
	if(_casesCounter >= _casesNumber)
		return;

	int x, y, width, height;
	defineCoordonates(x, y, width, height);


	HmiCompany* newCompany = new HmiCompany(_scene, x, y, width, height, label, price , id);
	
	_cases.push_back(newCompany);

	addCase();
}

void MainWindow::addStation(std::string label, std::string prix)
{
	if(_casesCounter >= _casesNumber)
		return;

	int x, y, width, height;
	defineCoordonates(x, y, width, height);

HmiStation* newStation = new HmiStation(_scene, x, y, width, height, label, prix);
	_stations.push_back(newStation);
	_cases.push_back(newStation);


	addCase();
}

void MainWindow::addCardCase(std::string label, int type)
{
	if(_casesCounter >= _casesNumber)
		return;

	int x, y, width, height;
	defineCoordonates(x, y, width, height);


	HmiCardCase* newCardCase = new HmiCardCase(_scene, x, y, width, height, label, type);
	_cases.push_back(newCardCase);

	addCase();
}

void MainWindow::addTaxCase(std::string id, std::string label, std::string taxAmount)
{

if(_casesCounter >= _casesNumber)
		return;

	int x, y, width, height;
	defineCoordonates(x, y, width, height);

	HmiTaxCase* newtaxCase = new HmiTaxCase(_scene, x, y, width, height, id, label, taxAmount);
	_cases.push_back(newtaxCase);

	addCase();
}


void MainWindow::waitOrder()
{
	if(_casesCounter >= _casesNumber)
		return;

    std::string message = HmiSocket::getInstance()->receive();

	std::vector<std::string> messageDecoupe;

	split(message, SEPARATOR, &messageDecoupe);

	if(messageDecoupe.size() <= 1)
		return;

	int action = atoi(messageDecoupe[OFFSET_ACTION].c_str());

	if(action == CREATION)
	{
		int objectType = atoi(messageDecoupe[2].c_str());
		if(objectType== STREET)
		{
			std::string couleur = messageDecoupe[3];
			std::string position = messageDecoupe[4];
			std::string streetName = messageDecoupe[5];
			std::string prix = messageDecoupe[6];

			addStreet(couleur, streetName, prix);

		}
		else if(objectType == STATION)
		{
			std::string position = messageDecoupe[3];
			std::string label = messageDecoupe[4];
			std::string prix = messageDecoupe[5];

			addStation(label, prix);
		}
		else if((objectType == JAIL)
			  ||(objectType == GO_TO_JAIL)
			  ||(objectType == GO_CASE)
			  ||(objectType == FREE_PARKING))
		{
			std::string position = messageDecoupe[3];
			std::string id = messageDecoupe[4];

			addBigCase(id);
		}
		else if(objectType == COMPANY)
		{
			std::string position = messageDecoupe[3];
			std::string label = messageDecoupe[4];
			std::string prix = messageDecoupe[5];
			std::string id = messageDecoupe[6];

			addCompanyCase(id, label, prix);
		}
		else if(objectType == TAX)
		{
			std::string position = messageDecoupe[3];
			std::string id = messageDecoupe[4];
			std::string label = messageDecoupe[5];
			std::string taxAmount = messageDecoupe[6];

			addTaxCase(id, label, taxAmount);
		}
		else if(objectType == CHANCE)
		{
			std::string position = messageDecoupe[3];
			std::string label = messageDecoupe[4];
			int type = HmiCardCase::CHANCE;

			addCardCase(label, type);
		}
		else if(objectType == COMMUNITY_CHEST)
		{
			std::string position = messageDecoupe[3];
			std::string label = messageDecoupe[4];
			int type = HmiCardCase::COMMUNITY_CHEST;

			addCardCase(label, type);
		}
	}
	else if(action == TOKEN_CHOICE)
	{
		int tokenCount = (messageDecoupe.size() - 1);

		std::vector<bool> tokens;

		for(int i=0; i<tokenCount; i++)
		{
			bool toAdd = atoi(messageDecoupe[i+1].c_str()) ? true:false;
			tokens.push_back(toAdd);
		}

		
		HmiChoiceTokenWindow *fenetreCode = new HmiChoiceTokenWindow(this, tokens);
		fenetreCode->exec();

		//_socket->sendToServer("1");

	}


	//On envoie l'accusé de réception si celui-ci est nécessaire
	if(atoi(messageDecoupe[0].c_str()) == ACKNOWLEDGE_REQUIERED)
		_socket->sendToServer(ACKNOWLEDGE);


}

void MainWindow::defineCoordonates(int &x, int &y, int &width, int &height)
{

	width = _width/(_casesNumber/4 +1);
	height = _height/(_casesNumber/4 +1);

	if(_casesCounter < _casesNumber/4)
	{
		x = _width - width * (_casesCounter + 1);
		y = _height - height;
	}
	else if(_casesCounter < 2*_casesNumber/4)
	{
		x = 0;
		y = _height - height * ((_casesCounter - _casesNumber/4) + 1);
	}
	else if(_casesCounter < 3*_casesNumber/4)
	{
		x = width * (_casesCounter - 2*_casesNumber/4);
		y = 0;
	}
	else
	{
		x = _width - width;
		y = height * (_casesCounter - 3*_casesNumber/4);
	}

	
	_casesCounter++;
}


QColor MainWindow::stringToColor(std::string stringColor)
{
	if(stringColor == "white")
		return Qt::white;
	else if(stringColor == "black")
		return Qt::black;
	else if(stringColor == "red")
		return Qt::red;
	else if(stringColor == "darkRed")
		return Qt::darkRed;
	else if(stringColor == "green")
		return Qt::green;
	else if(stringColor == "darkGreen")
		return Qt::darkGreen;
	else if(stringColor == "blue")
		return Qt::blue;
	else if(stringColor == "darkBlue")
		return Qt::darkBlue;
	else if(stringColor == "cyan")
		return Qt::cyan;
	else if(stringColor == "darkCyan")
		return Qt::darkCyan;
	else if(stringColor == "magenta")
		return Qt::magenta;
	else if(stringColor == "darkMagenta")
		return Qt::darkMagenta;
	else if(stringColor == "yellow")
		return Qt::yellow;
	else if(stringColor == "darkYellow")
		return Qt::darkYellow;
	else if(stringColor == "gray")
		return Qt::gray;
	else if(stringColor == "darkGray")
		return Qt::darkGray;

	else
		return Qt::gray;
}
