#ifndef _GAME_FORM_H_
#define _GAME_FORM_H_

#include <FBase.h>
#include <FUi.h>
#include <FApp.h>

#include <vector>

class CaseValueChoicePopup;

class GameForm :
	public Osp::Ui::Controls::Form,
	public Osp::Ui::IActionEventListener
{
public:
	GameForm(int size);
	virtual ~GameForm(void);


	virtual result OnInitializing(void);
	virtual result OnTerminating(void);
	virtual void OnActionPerformed(const Osp::Ui::Control& source, int actionId);


	bool Initialize(void);
	void updateDisplay();

	void updateCaseDisplay(int caseId);

protected:
	static const int ID_BUTTON_OK_POPUP_NO_RESULT = 100;
	static const int ID_RESET_BUTTON			  = ID_BUTTON_OK_POPUP_NO_RESULT+1;
	static const int ID_RESULT_BUTTON 			  = ID_RESET_BUTTON + 1;
	static const int ID_FIST_CASE 				  = ID_RESULT_BUTTON + 1;
	//Osp::Ui::Controls::OptionMenu* __pOptionMenu;


private:

	Osp::Ui::Controls::Popup * _noResultPopup;
	Osp::Ui::Controls::Button *_pButtonClosePopup;

	std::vector<Osp::Ui::Controls::Button*> _buttons;
	int _size;
	int _rowSize;
	int _columnSize;
	int _casesCount;
	int _valueMax;

};

#endif //_GAME_FORM_H_
