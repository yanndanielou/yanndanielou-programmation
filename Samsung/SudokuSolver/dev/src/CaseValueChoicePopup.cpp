#include "CaseValueChoicePopup.h"
#include "MultiResolution.h"

#include "GameForm.h"

#include "Game.h"

using namespace Osp::App;
using namespace Osp::Base;
using namespace Osp::Ui;
using namespace Osp::Ui::Controls;
using namespace Osp::Graphics;



CaseValueChoicePopup::CaseValueChoicePopup(	int size,
											int valueMax)
: _caseId(0),
  _gameForm(0),
  _size(size),
  _valueMax(valueMax)
{
}

CaseValueChoicePopup::~CaseValueChoicePopup(void)
{
	AppLog("CaseValueChoicePopup::~CaseValueChoicePopup");

	// delete all pointers in the vector
	//for(unsigned int i=0; i<_buttons.size(); i++)
	//	delete _buttons[i];


	//_buttons.clear();


	AppLog("END CaseValueChoicePopup::~CaseValueChoicePopup");

}


result
CaseValueChoicePopup::Construct(bool hasTitle, Osp::Graphics::Dimension dim)
{
	result r = E_SUCCESS;

	// Construct Popup
	r = Popup::Construct( hasTitle, dim);


	int POPUP_WIDTH = GetWidth();
	int POPUP_HEIGHT = GetHeight();

	int BUTTON_WIDTH  = POPUP_WIDTH/(_size + 1);
	int BUTTON_HEIGHT = POPUP_HEIGHT/ (_size + 2);



	for(int i=0; i<_valueMax; i++)
	{
		int value = i+1;

		int x = 0.5 * BUTTON_WIDTH + /*GetX() + */(i%_size) * BUTTON_WIDTH;

		int y = 0.5 * BUTTON_HEIGHT/*+  GetY()*/ + (i/_size) * BUTTON_HEIGHT;

		Button *choiceButton = new Button();
		choiceButton->Construct(__R(x, y, BUTTON_WIDTH, BUTTON_HEIGHT));
		Osp::Base::String label= "";
		label.Insert( value , 0);
		choiceButton->SetText(label);
		choiceButton->SetActionId(ID_FIRST_BUTTON + i);
		choiceButton->AddActionEventListener(*this);

		Case* currentCase = Game::getInstance()->getCase(_caseId);


		bool isEnable = currentCase->canAdd(value);

		choiceButton->SetEnabled(isEnable);


		AddControl(*choiceButton);
		_buttons.push_back(choiceButton);

		//AppLog("Popup: creation button %d, x = %d, y = %d", i, x, y);
	}


	//Creation of Button "OK"

	int butonWidth = POPUP_WIDTH / 3;

	int x = 0.5 * butonWidth;
	int y = 0.5 * BUTTON_HEIGHT + /* GetY()+  */_size * BUTTON_HEIGHT;




	Button *okButton = new Button();
	okButton->Construct(__R(x, y, butonWidth, BUTTON_HEIGHT));
	okButton->SetText(Osp::Base::String("OK"));
	okButton->SetActionId(ID_OK_BUTTON);
	okButton->AddActionEventListener(*this);
	AddControl(*okButton);
	_buttons.push_back(okButton);


	//Creation of Button "CLEAR"
	x = 0.5 * butonWidth + butonWidth;

	Button *clearButton = new Button();
	clearButton->Construct(__R(x, y, butonWidth, BUTTON_HEIGHT));
	clearButton->SetText(Osp::Base::String("CLEAR"));
	clearButton->SetActionId(ID_CLEAR_BUTTON);
	clearButton->AddActionEventListener(*this);
	AddControl(*clearButton);
	_buttons.push_back(clearButton);



	return r;
}


void
CaseValueChoicePopup::OnActionPerformed(const Osp::Ui::Control& source, int actionId)
{
	// Get the frame pointer
//	Frame *pFrame = Application::GetInstance()->GetAppFrame()->GetFrame();

	//Case of buttons
	if(  actionId >= ID_FIRST_BUTTON &&
         actionId <= ID_FIRST_BUTTON + _valueMax )
	{
		int value = actionId - ID_FIRST_BUTTON + 1;

		Game::getInstance()->getCase(_caseId)->setValue(value, true);
		_gameForm->updateDisplay();

		delete this;
	}
	else if(actionId == ID_CLEAR_BUTTON)
	{
		Game::getInstance()->getCase(_caseId)->setValue(0);
		_gameForm->updateCaseDisplay(_caseId);
//		_gameForm->updateDisplay();

		delete this;

	}
	else if(actionId == ID_OK_BUTTON)
	{
		delete this;
	}

}


