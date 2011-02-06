#ifndef _CASE_VALUE_CHOICE_POPUP_H_
#define _CASE_VALUE_CHOICE_POPUP_H_

#include <FBase.h>
#include <FUi.h>
#include <FApp.h>


#include <vector>

class GameForm;

class CaseValueChoicePopup :
	public Osp::Ui::Controls::Popup,
	public Osp::Ui::IActionEventListener
{
public:
	CaseValueChoicePopup(int size,
						 int valueMax);

	virtual ~CaseValueChoicePopup(void);

	result Construct(bool hasTitle,Osp::Graphics::Dimension dim);
	void OnActionPerformed( const Osp::Ui::Control& source, int actionId );

	bool Initialize(void);

	void setCaseId(int id);
	void setGameForm(GameForm* gameForm);

protected:
	static const int ID_FIRST_BUTTON = 100;
	static const int ID_CLEAR_BUTTON = 200;
	static const int ID_OK_BUTTON = 201;

private:
	int _caseId;
	GameForm* _gameForm;

	int _size;
	int _valueMax;

	std::vector<Osp::Ui::Controls::Button*> _buttons;

};

inline void CaseValueChoicePopup::setCaseId(int id){
	_caseId = id;}

inline void CaseValueChoicePopup::setGameForm(GameForm* gameForm){
	_gameForm = gameForm;}


#endif //_CASE_VALUE_CHOICE_POPUP_H_
