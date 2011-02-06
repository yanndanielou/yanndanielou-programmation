#ifndef _ActionsManager_H_
#define _ActionsManager_H_

#include "Action.h"

#include <FBase.h>

#include <vector>
#include <map>
#include <string>

class Action;


const char COLUMNS_SEPARATOR  = ';';
const char SUB_SEPARATOR  = '*';

class ActionsManager
{
public:
	ActionsManager(void);
	~ActionsManager(void);

	static ActionsManager* GetInstance();

	void init();
	void build();
	void addAction(Osp::Base::String & actionAsString);
	Action* getCurrentAction();
	Action* getActionById(int id);

	void eraseMap();

private:

	std::vector<Osp::Base::String> split(Osp::Base::String toBeSplitted, char separator);

	static ActionsManager* _instance;

	//List of actions
	std::map<int,Action*> _actions;
	Action* _currentAction;

};

inline Action* ActionsManager::getCurrentAction(){
	return _currentAction;}



#endif //_ActionsManager_H_
