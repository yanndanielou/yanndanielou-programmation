#pragma once

#include <iostream>
#include <fstream>
#include <string>

#include "HmiSocket.h"
#include "MainWindow.h"


class HmiMessageManagement
{
public:
	
    static HmiMessageManagement* getInstance();
    HmiMessageManagement(void);
    ~HmiMessageManagement(void);

	void treatMessage(std::string message);

	/*Mutateurs*/
	//void setMainWindow(MainWindow* _mainWindow);

private:

     static HmiMessageManagement* _instance;
	 HmiSocket* _socket;
	 MainWindow* _mainWindow;
};
