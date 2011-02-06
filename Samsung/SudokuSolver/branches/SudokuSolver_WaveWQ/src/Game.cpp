#include "Game.h"



#include <FApp.h>

//Initialisation of static attributes
Game* Game::_instance = 0;

Game* Game::getInstance()
{
	if(_instance)
		return _instance;
	else
		return new Game();
}

Game::Game()
: _size(0),
  _casesCount(0),
  _triesCounter(0)
{
	_instance = this;
}

Game::~Game(void)
{
	//Destructions of cases
	for(unsigned int i=0; i<_cases.size(); i++)
		delete _cases[i];
	_cases.clear();


	//Destructions of columns
	for(unsigned int i=0; i<_columns.size(); i++)
		delete _columns[i];
	_columns.clear();


	//Destructions of rows
	for(unsigned int i=0; i<_rows.size(); i++)
		delete _rows[i];
	_rows.clear();


	//Destructions of squares
	for(unsigned int i=0; i<_squares.size(); i++)
		delete _squares[i];
	_squares.clear();
}
/*
void Game::findBestRotation()
{
	int bestRotation = 0;
	int nearestCase = _size * _size * _size *_size;


	std::vector<Case*> casesTemp;

	for(unsigned int i=0; i<_cases.size(); i++)
	{
		casesTemp.push_back(_cases.at(i));
	}

	for(unsigned int i=0; i<4; i++)
	{
		int index = findNearestCase(casesTemp);
		if(index < nearestCase)
		{
			nearestCase = index;
			bestRotation = i;
		}

		for(int j=0; j<i; j++)
		{


		}
	}
}

int Game::findNearestCase(std::vector<Case*> & casesTemp)
{
	int index = 0;
	bool caseFound = false;
	for(unsigned int i=0; i<casesTemp.size() && !caseFound; i++)
	{
		if(casesTemp.at(i)->isFixed())
		{
			index = i;
			caseFound = true;
		}
	}
	return index;
}

std::vector<Case*> Game::rotation()
{
	std::vector<Case*> casesTemp;

	for(unsigned int i=_columns.size()-1; i>=0; i--)
	{
		for(unsigned int j=0; j<_columns[i]->getCases().size(); j++)
		{
			casesTemp.push_back(_columns[i]->getCases().at(j));
		}
	}
	return casesTemp;
}
*/
void Game::reset(bool onlyCalculatedCases)
{
  for(std::vector<Case*>::iterator it = _cases.begin();it != _cases.end(); ++it)
  {
	  if( (onlyCalculatedCases && !(*it)->isFixed())
	   || (!onlyCalculatedCases))
	  {
		  (*it)->setValue(0);
	  }
	//  ((Case*)it)->setValue(0);
  }
  _triesCounter = 0;
}


void Game::build()
{
  for(int i=0; i<_size * _size; i++){
	  _rows.push_back(new MyContainer());
  }
  
  for(int i=0; i<_size * _size; i++){
	  _columns.push_back(new MyContainer());
  }
  for(int i=0; i<_size * _size; i++){
	  _squares.push_back(new MyContainer());
  }
  
  for(int i=0; i<_size * _size; i++)
  {
    for(int j=0; j<_size *_size; j++)
    {   
	 Case * caseTemp = new Case();
	 _cases.push_back(caseTemp);

	 _rows[i]->addCase(caseTemp);
      _columns[j]->addCase(caseTemp);

      int carre = 0;

      if(i >=_size*2)
      {
    	 carre += 2;
      }
      else if(i >= _size)
    	  carre++;


      if(j >= _size * 2)
      {
    	 carre += _size * 2;
      }
      else if(j >= _size)
    	  carre+= _size;

      _squares[carre]->addCase(caseTemp);


      caseTemp->setRow(_rows[i]);
      caseTemp->setColumn(_columns[j]);
      caseTemp->setSquare(_squares[carre]);
    }
  }
}


/*****************************************
 * 		solve: Game
 *
 *  Recursive method that solves the sudoku
 *
 *****************************************/

bool Game::solve(int index)
{
	_triesCounter++;
	if(_triesCounter >= POSSIBLE_LIMIT)
		return false;

	if(index >= _casesCount)
	{
		//AppLog("_triesCounter %d \n", _triesCounter);
		return true;
	}

  Case * currentCase = _cases[index];

  bool caseFound = false;

	 if(currentCase->isFixed() == false)
	 {
		for(int i=currentCase->getValue()+1; i<=_valueMax && !caseFound; i++)
		{
			if(currentCase->canAdd(i))
			{
				currentCase->setValue(i);
				caseFound = true;
			}
		}
		if(caseFound == false)
		{
			currentCase->setValue(0);
			return false;
		}
		else
		{
			if(solve(index+1))
				return true;
			else
				return solve(index);
		}
	}
	else
	{
		return solve(index + 1);
	}
}



void Game::setProperties(int size, int casesCount, int rowSize, int valueMax)
{
	_size = size;
	_casesCount = casesCount;
	_rowSize = rowSize;
	_valueMax = valueMax;
}


 Case* Game::getCase(int x, int y){
	 return _cases[x + _rowSize * y];}

 Case* Game::getCase(int position){
	return _cases[position];}
