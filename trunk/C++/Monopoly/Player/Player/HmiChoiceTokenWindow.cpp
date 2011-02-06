#include "HmiChoiceTokenWindow.h"
#include "HmiChoiceTokenWindow.h"


#include "HmiSocket.h"

#include <sstream>

HmiChoiceTokenWindow::HmiChoiceTokenWindow(QWidget* parent, std::vector<bool> tokens)
:QDialog(parent)
{
	int tokensCount = tokens.size();

	for(int i=0; i<tokensCount; i++)
	{
		 std::ostringstream fileNameStream;
		 fileNameStream << "../../Images/token" << i << ".jpg";
		 std::string fileName =fileNameStream.str();
		_tokens.push_back(new QPushButton);
		_tokens[i]->setPixmap(QPixmap(fileName.c_str()));
		_tokens[i]->setEnabled(tokens[i]);
	}

	QGridLayout *layout = new QGridLayout;

	for(int i=0; i<tokensCount; i++)
	{
		 layout->addWidget(_tokens[i], i/3, i%3);
	}

	setLayout(layout);

	

    for(int i =0; i<tokensCount; i++){
       connect(_tokens[i], SIGNAL(pressed()), this, SLOT(tokenChoosen()));
    }
}

HmiChoiceTokenWindow::~HmiChoiceTokenWindow(void)
{
}


void HmiChoiceTokenWindow::tokenChoosen(){

  for(int i=0; i<_tokens.size(); i++)
  {
    if(_tokens[i]->isDown())
	{
		// cration d'un flux de sortie
		std::ostringstream oss;

		// écrire un nombre dans le flux
		oss << i;

		HmiSocket::getInstance()->sendToServer(oss.str());
		break;                           
    }
  }
	delete this;
}