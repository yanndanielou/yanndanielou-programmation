#pragma once

#include <stdio.h>    // printf
#include <winsock2.h>
#include <iostream>
#include <vector>
#pragma comment(lib, "ws2_32.lib")


class SrvSocket
{
public:
	
	static SrvSocket* getInstance();
	SrvSocket(void);
	bool addSocket(int socketNumber);


	std::string receive(int client);


	void sendToClient(int client, std::string message, bool acknowledgeRequired = false);
	void sendToAll(std::string message, bool acknowledgeRequired = false);

	std::vector<SOCKET>* getSockets(){
		return &_csock;};

	int getSocketsCount(){
		return _csock.size();};

public:
	~SrvSocket(void);

private:

	static SrvSocket* _instance;

	std::vector<SOCKET> _sock;
	std::vector<SOCKET> _csock;
	

};
