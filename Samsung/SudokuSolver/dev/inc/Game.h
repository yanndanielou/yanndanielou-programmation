#ifndef _GAME_H_
#define _GAME_H_

#include <vector>

#include "MyContainer.h"
#include "Case.h"

class GameForm;

const unsigned int POSSIBLE_LIMIT = 198875502;

class Game
{
public:
	Game(void);
	~Game(void);

	static Game* getInstance();

	void build();

	bool solve(int index);

	Case* getCase(int x, int y);
	Case* getCase(int position);

	void setProperties(int size, int casesCount, int  rowSize, int valueMax);
	void setGameForm(GameForm* gameForm);

	int getTriesCounter();

	void reset(bool onlyCalculatedCases = false);
	/*std::vector<Case*> rotation();
	void findBestRotation();
	int findNearestCase(std::vector<Case*> & casesTemp);*/

private:

	static Game* _instance;

	GameForm* _gameForm;

	std::vector<MyContainer*> _rows;
	std::vector<MyContainer*> _columns;
	std::vector<MyContainer*> _squares;

	std::vector<Case*> _cases;

	int _size;
	int _casesCount;
	int _rowSize;
	int _valueMax;

	int _triesCounter;
};

inline void Game::setGameForm(GameForm* gameForm){
	_gameForm = gameForm;}

inline int Game::getTriesCounter(){
	return _triesCounter;}


#endif //_GAME_H_
