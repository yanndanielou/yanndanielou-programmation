#include "HmiSocket.h"

//#include "HmiMessageManagement.h"


//Initialisation du singleton
HmiSocket* HmiSocket::_instance = 0;


HmiSocket* HmiSocket::getInstance()
{
	if(_instance)
		return _instance;
	else
	{
		return new HmiSocket();
	}
}


HmiSocket::HmiSocket(void)
{
	_instance = this;

	WSADATA WSAData;
	WSAStartup(MAKEWORD(2,0), &WSAData);
	
	sock = socket(AF_INET, SOCK_STREAM, 0);

	sin.sin_addr.s_addr = inet_addr(SERVER_IP_ADRESS);
	sin.sin_family	    = AF_INET;
	sin.sin_port	    = htons(PORT_NUMBER);
	
	
	connect(sock, (SOCKADDR *)&sin, sizeof(sin));
	
	
}

HmiSocket::~HmiSocket()
{}


std::string HmiSocket::receive()
{
	char buffer[255];
	memset(buffer,0,255);
	
	recv(sock, buffer, sizeof(buffer), 0);
   	printf("%s",buffer);

	//HmiMessageManagement::getInstance()->treatMessage(std::string(buffer));
	return std::string(buffer);
}

void HmiSocket::sendToServer(std::string message)
{
	char buffer[255];
	std::string retour;

	memset(buffer,0,255);
	sprintf(buffer, message.c_str());
	
	send(sock,buffer,sizeof(buffer),0);


	//send(sock,message.c_str(),(int)message.size(),0);
}
