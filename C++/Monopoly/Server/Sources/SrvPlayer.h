#pragma once
#include "..\..\comon\cmobject.h"
#include "SrvCase.h"

class SrvPlayer :
	public CmObject
{
public:
	SrvPlayer(std::string id, std::string label = "");
	~SrvPlayer(void);


	//Accesseurs
	int getTokenNumber(){
		return _tokenNumber;};

	//Mutateurs
	void setPosition(SrvCase* newPosition){
		_position = newPosition;};

	void setTokenNumber(int tokenNumber){
		_tokenNumber = tokenNumber;};


private:

	static int _playersCount;

	//Numéro du joueur, le premier est 0 
	int _playerNumber;

	int _money;
	int _tokenNumber;

	SrvCase* _position;


};
