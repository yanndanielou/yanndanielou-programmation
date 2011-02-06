#include <iostream>
//#include <time.h>
#include "..\..\Comon\cmproperties.h"
#include "SrvFactory.h"
#include "SrvSocket.h"
#include "SrvGameBoard.h"

int main()
{

	/*bool socketOk;

	SrvPlayer* player1 = new SrvPlayer("player1", "joueur 1");
	SrvPlayer* player5 = new SrvPlayer("player5", "joueur 5");
		

	SrvSocket::getInstance()->sendToAll("Bienvenue joueur");*/

	SrvFactory* fact = SrvFactory::getInstance();
	fact->readDatasource();

	std::string recu;
	recu = SrvSocket::getInstance()->receive(0);
	std::cout<< "recu: " << recu.c_str() << std::endl;
	SrvSocket::getInstance()->sendToAll("Bienvenue joueur");

	std::cout << "message sent";
	std::cout << SrvGameBoard::getInstance()->getCasesCount()<< std::endl;
//	system("pause");

    return 0;
}
