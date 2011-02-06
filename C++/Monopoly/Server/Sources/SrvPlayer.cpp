#include "SrvPlayer.h"
#include "SrvSocket.h"

int SrvPlayer::_playersCount = 0;

SrvPlayer::SrvPlayer(std::string id, std::string label)
: CmObject(id, label),
	_playerNumber(_playersCount)
{
	_playersCount++;
	SrvSocket::getInstance()->addSocket(_playerNumber);
}

SrvPlayer::~SrvPlayer(void)
{
}
