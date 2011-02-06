#include "HmiMessageManagement.h"

#include "../../Comon/Miscellaneous.cpp"

#include "../../Comon/CmCommunicationProtocol.h"

//Initialisation du singleton
HmiMessageManagement* HmiMessageManagement::_instance = 0;


HmiMessageManagement* HmiMessageManagement::getInstance()
{
	if(_instance)
		return _instance;
	else
	{
		return new HmiMessageManagement();
	}
}


HmiMessageManagement::HmiMessageManagement()
{
	_instance = this;
	_socket   = HmiSocket::getInstance();
	//_mainWindow = mainWindow;
}

HmiMessageManagement::~HmiMessageManagement()
{}


void HmiMessageManagement::treatMessage(std::string message)
{

	std::vector<std::string> messageDecoupe;

	split(message, SEPARATOR, &messageDecoupe);


	if(atoi(messageDecoupe[OFFSET_ACTION].c_str()) == CREATION)
	{
		if(atoi(messageDecoupe[2].c_str()) == STREET)
		{
			int a = 0;
		}
	}


	
	if(atoi(messageDecoupe[0].c_str()) == ACKNOWLEDGE_REQUIERED)
		_socket->sendToServer(ACKNOWLEDGE);

}
