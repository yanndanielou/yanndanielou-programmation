#include "ActionsManager.h"

#include <FIo.h>

using namespace Osp::Io;
using namespace Osp::Base;

//Initialisation of static attributes
ActionsManager* ActionsManager::_instance = 0;

ActionsManager* ActionsManager::GetInstance()
{
	if(_instance)
		return _instance;
	else
		return new ActionsManager();
}

ActionsManager::ActionsManager()
:_currentAction(0)
{
	_instance = this;
}

ActionsManager::~ActionsManager(void)
{
}

void ActionsManager::init(void)
{
	build();
	_currentAction = _actions.find(0)->second;
}

void ActionsManager::build(void)
{
	Osp::Base::String filePath(L"/Home/dataPremiersSecours.txt");
	File file;
	file.Construct(filePath, L"r");

	Osp::Base::String readLine;

	while(file.Read(readLine) == E_SUCCESS)
	{
		addAction(readLine);
	}



}

void ActionsManager::addAction(Osp::Base::String & actionAsString)
{
	int id,textSize;
	std::vector<Osp::Base::String> row = split(actionAsString,COLUMNS_SEPARATOR);

	Osp::Base::Integer::Decode(row[0],id);

	Osp::Base::String title = row[1];
	Action* newAction = new Action(id,title);

	Osp::Base::String textAsString = row[2];

	std::vector<Osp::Base::String> texts = split(textAsString,SUB_SEPARATOR);
	for(unsigned int i=0;i<texts.size();i++)
	{
		newAction->addText(texts[i]);
	}

	Osp::Base::Integer::Decode(row[3],textSize);
	newAction->setTextSize(textSize);

	Osp::Base::String choicesAsString = row[4];
	std::vector<Osp::Base::String> choices = split(choicesAsString,SUB_SEPARATOR);
	for(unsigned int i=0;i<choices.size();i++)
	{
		int choice = 0;
		Osp::Base::Integer::Decode(choices[i],choice);
		newAction->addChoice(choice);
	}

	_actions[id]=newAction;

}


Action* ActionsManager::getActionById(int id)
{
	return _actions.find(id)->second;
}

void ActionsManager::eraseMap()
{
	std::map<int, Action*>::iterator it;

    for (it = _actions.begin(); it != _actions.end(); it++)
        delete it->second;

    _actions.erase(_actions.begin(), _actions.end());

}

std::vector<Osp::Base::String> ActionsManager::split(Osp::Base::String toBeSplitted, char separator)
{
	std::vector<Osp::Base::String> res;

	int length = toBeSplitted.GetLength();
	int cursor = 0;


	while(cursor<length)
	{
		Osp::Base::String subString;
		bool charFound = false;

		for(int i=cursor;i<length && !charFound; i++)
		{
			mchar charToAdd;
			toBeSplitted.GetCharAt(i,charToAdd);

			if(charToAdd == separator)
				charFound = true;
			else
				subString.Append(charToAdd);

			cursor++;

		}

		res.push_back(subString);
	}
	return res;
}
