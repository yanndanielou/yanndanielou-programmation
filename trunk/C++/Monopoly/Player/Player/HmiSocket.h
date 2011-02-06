#pragma once

#include <stdio.h>    // printf
#include <winsock2.h>
#include <iostream>
#include <vector>
#pragma comment(lib, "ws2_32.lib")


#define SERVER_IP_ADRESS "127.0.0.1"
#define PORT_NUMBER 	4444

class HmiSocket
{
public:
	
    static HmiSocket* getInstance();
    HmiSocket(void);

	std::string receive();
	void sendToServer(std::string message);

public:
        ~HmiSocket(void);

private:

        static HmiSocket* _instance;
		
		SOCKET sock;
		SOCKADDR_IN sin;
	

};
