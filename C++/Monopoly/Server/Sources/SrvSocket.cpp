#include "SrvSocket.h"

#include <iostream>

//Initialisation du singleton
SrvSocket* SrvSocket::_instance = 0;


SrvSocket* SrvSocket::getInstance()
{
	if(_instance)
		return _instance;
	else
	{
		return new SrvSocket();
	}
}


SrvSocket::SrvSocket(void)
{
	_instance = this;

	WSADATA WSAData;
	WSAStartup(MAKEWORD(2,0), &WSAData);
}

SrvSocket::~SrvSocket()
{

}


bool SrvSocket::addSocket(int socketNumber)
{
	SOCKET sock_temp = socket(AF_INET, SOCK_STREAM, 0);
	SOCKET csock_temp;
	
	SOCKADDR_IN sin_temp;
	SOCKADDR_IN csin_temp;

	sin_temp.sin_addr.s_addr = INADDR_ANY;
	sin_temp.sin_family	     = AF_INET;
	sin_temp.sin_port	     = htons(4444 + socketNumber);

	
	bind(sock_temp, (SOCKADDR *)&sin_temp, sizeof(sin_temp));

	listen(sock_temp, 0);


	int sinsize = sizeof(csin_temp);

	csock_temp = accept(sock_temp, (SOCKADDR *)&csin_temp, &sinsize);

	_csock.push_back(csock_temp);
	_sock.push_back(sock_temp);

	return csock_temp != INVALID_SOCKET;
}


std::string SrvSocket::receive(int client)
{
	char buffer[255];
	std::string retour;

	memset(buffer,0,255);
	recv(_csock[client], buffer, sizeof(buffer), 0);
	retour = std::string(buffer);

	return retour;
}

void SrvSocket::sendToClient(int client, std::string message, bool acknowledgeRequired)
{
	char buffer[255];

	memset(buffer,0,255);
	sprintf(buffer, message.c_str());

	send(_csock[client],buffer,sizeof(buffer),0);

	if(acknowledgeRequired)
	{
		std::string retour;
		retour = receive(client);
		std::cout<< "recu: " << retour.c_str() << std::endl;
	}

}

void SrvSocket::sendToAll(std::string message, bool acknowledgeRequired)
{
	for(unsigned int i=0; i < _csock.size(); i++)
	{
		sendToClient(i, message, acknowledgeRequired);
	}
}