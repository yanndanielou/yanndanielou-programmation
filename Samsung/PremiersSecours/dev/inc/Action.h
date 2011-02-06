#ifndef _Action_H_
#define _Action_H_

#include "ActionsManager.h"

#include <FBase.h>
#include <vector>

class ActionsManager;

class Action
{
public:
	Action(int id, Osp::Base::String title);
	~Action(void);

	void addText(Osp::Base::String textToAdd);
	void addChoice(int choiceToAdd);

	int getId();
	std::vector<int> getChoices();
	std::vector<Osp::Base::String> getText();
	int getTextSize();
	void setTextSize(int textSize);
	Osp::Base::String getTitle();



private:
	int _id;
	Osp::Base::String _title;
	std::vector<Osp::Base::String> _text;
	int _textSize;
	std::vector<int> _choices;

};


inline void Action::addChoice(int choiceToAdd){
	_choices.push_back(choiceToAdd);}

inline void Action::addText(Osp::Base::String textToAdd){
	_text.push_back(textToAdd);}

inline int Action::getId(){
	return _id;}

inline Osp::Base::String Action::getTitle(){
	return _title;}

inline std::vector<Osp::Base::String> Action::getText(){
	return _text;}

inline int Action::getTextSize(){
	return _textSize;}

inline void Action::setTextSize(int textSize){
	_textSize = textSize;}

inline std::vector<int> Action::getChoices(){
	return _choices;}

#endif //_Action_H_
