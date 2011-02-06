#include <QtGui/QApplication>
#include "MainWindow.h"

#include "HmiSocket.h"


int main(int argc, char *argv[])
{

    QApplication a(argc, argv);
    MainWindow w;
    w.show();

	//HmiSocket::getInstance()->sendToServer("Bonjour serveur");

    return a.exec();

}
