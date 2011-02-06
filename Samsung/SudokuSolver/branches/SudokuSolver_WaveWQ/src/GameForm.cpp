#include "GameForm.h"
#include "MultiResolution.h"


#include "CaseValueChoicePopup.h"
#include "NoResultPopup.h"


#include "Game.h"

using namespace Osp::App;
using namespace Osp::Base;
using namespace Osp::Ui;
using namespace Osp::Ui::Controls;
using namespace Osp::Graphics;

GameForm::GameForm(int size)
: _noResultPopup(0),
  _size(size),
  _rowSize(size*size),
  _columnSize(size*size),
  _casesCount(_rowSize * _columnSize),
  _valueMax(size*size)
{
}

GameForm::~GameForm(void)
{
	/*for(unsigned int i=0; i<_buttons.size(); i++)
		delete _buttons[i];

//	_buttons.clear();*/
}

bool
GameForm::Initialize(void)
{
	Form::Construct(FORM_STYLE_NORMAL|FORM_STYLE_INDICATOR);
	SetName(L"IDF_GameForm");
//	SetTitleText(L"GameForm - Button");
	SetBackgroundColor(Color(0,0,0,0));

	return true;
}

result
GameForm::OnInitializing(void)
{
	result r = E_SUCCESS;

	// Create an OptionKey
	//SetOptionkeyActionId(ID_OPTIONKEY);
	//AddOptionkeyActionListener(*this);

	Game* game = Game::getInstance();
	game->setProperties(_size, _casesCount, _rowSize, _valueMax);
	game->build();
	game->setGameForm(this);

	// Create a Button

	int APPLICATION_WIDTH =480;// Application::GetInstance()->GetAppFrame()->GetFrame()->GetWidth();
	int APPLICATION_HEIGHT = 800;//Application::GetInstance()->GetAppFrame()->GetFrame()->GetHeight();

	int BUTTON_WIDTH = APPLICATION_WIDTH/ (_rowSize + 1);
	int BUTTON_HEIGHT = BUTTON_WIDTH * 1.2;//APPLICATION_HEIGHT/12;

	for(int i = 0; i < _casesCount; i++)
	{
		Button *pButton = new Button();

		int row =  i/ _rowSize;
		int column = i% _columnSize;

		int x = column * BUTTON_WIDTH;
		int y = BUTTON_HEIGHT / 2 + row * BUTTON_HEIGHT;

		//Add spaces betwin group of cases
		y += (row / _size) * BUTTON_HEIGHT / 2;

		//Add spaces betwin group of cases
		x += (column / _size) * BUTTON_WIDTH / 2;

		pButton->Construct(__R(x, y, BUTTON_WIDTH, BUTTON_HEIGHT));
		AddControl(*pButton);
		Osp::Base::String label= "";
		pButton->SetText(label);
		//pButton->SetText(L);
		pButton->SetActionId(ID_FIST_CASE + i);
		pButton->AddActionEventListener(*this);

		_buttons.push_back(pButton);
	}

	int controlButtonsWidth = APPLICATION_WIDTH * 0.8;
	int remainingHeight = APPLICATION_HEIGHT - ( BUTTON_HEIGHT * (_columnSize + 1));

	int resultButtonHeight = remainingHeight / 5;
	int controlButtonsY      = APPLICATION_HEIGHT - remainingHeight/2 - resultButtonHeight/2;//- (1.75 * resultButtonHeight);

	Button *resultButton = new Button();
	resultButton->Construct(__R(controlButtonsWidth * 0.1, controlButtonsY, controlButtonsWidth/2, resultButtonHeight));
	AddControl(*resultButton);
	resultButton->SetText(L"Solve Me!");
	resultButton->SetActionId(ID_RESULT_BUTTON);
	resultButton->AddActionEventListener(*this);

	Button *resetButton = new Button();
	resetButton->Construct(__R(controlButtonsWidth * 0.1 + controlButtonsWidth/2, controlButtonsY, controlButtonsWidth/2, resultButtonHeight));
	AddControl(*resetButton);
	resetButton->SetText(L"Reset");
	resetButton->SetActionId(ID_RESET_BUTTON);
	resetButton->AddActionEventListener(*this);

	return r;
}

result
GameForm::OnTerminating(void)
{
	result r = E_SUCCESS;

	//delete __pOptionMenu;

	return r;
}

void
GameForm::OnActionPerformed(const Osp::Ui::Control& source, int actionId)
{
	if( actionId >= ID_FIST_CASE &&
		actionId <= ID_FIST_CASE + _casesCount)
	{
		int idButton = actionId - ID_FIST_CASE;

		int APPLICATION_WIDTH = Application::GetInstance()->GetAppFrame()->GetFrame()->GetWidth();
		int APPLICATION_HEIGHT = Application::GetInstance()->GetAppFrame()->GetFrame()->GetHeight();


		int popupWidth = APPLICATION_WIDTH * 0.6;
		int popupHeight = APPLICATION_HEIGHT * 0.6;

		CaseValueChoicePopup * valueChoicePopup;
		valueChoicePopup = new CaseValueChoicePopup(_size,_valueMax);
		valueChoicePopup->setCaseId(idButton);
		valueChoicePopup->Construct(false,Dimension(popupWidth, popupHeight));
		valueChoicePopup->setGameForm(this);
		valueChoicePopup->SetShowState(true);
		valueChoicePopup->Show();

	}
	else if(actionId == ID_RESULT_BUTTON)
	{
		bool res = Game::getInstance()->solve(0);

		if(res)
		{
			updateDisplay();
			this->RequestRedraw(true);
		}
		//If the sudoku couldn't have been solved, we display a popup that say
		//the sudoku is impossible
		else
		{
			int APPLICATION_WIDTH =480;// Application::GetInstance()->GetAppFrame()->GetFrame()->GetWidth();
			int APPLICATION_HEIGHT = 800;//Application::GetInstance()->GetAppFrame()->GetFrame()->GetHeight();


			int popupWidth = APPLICATION_WIDTH * 0.6;
			int popupHeight = APPLICATION_HEIGHT * 0.6;

			NoResultPopup* noResultPopup = new NoResultPopup();
			noResultPopup->Construct(false,Dimension(200, 200));
			noResultPopup->SetShowState(true);
			noResultPopup->Show();

			Game::getInstance()->reset(true);
		}
	}

	else if(actionId == ID_RESET_BUTTON)
	{
		Game::getInstance()->reset();
		updateDisplay();
		this->RequestRedraw(true);
	}
	else if(actionId == ID_BUTTON_OK_POPUP_NO_RESULT)
	{
		delete _noResultPopup;
		_noResultPopup = 0;
		delete _pButtonClosePopup;
		_pButtonClosePopup = 0;

	}

}


void GameForm::updateDisplay()
{
	for(int i=0; i < _casesCount; i++)
	{
		updateCaseDisplay(i);
	}
}


void GameForm::updateCaseDisplay(int caseId)
{
	Case* currentCase = Game::getInstance()->getCase(caseId);
	int value = currentCase->getValue();

	Osp::Base::String label= "";

	if(value >0 && value <= _valueMax)
	{
		if(currentCase->isFixed())
			_buttons[caseId]->SetTextColor(Osp::Graphics::Color::COLOR_RED);
		else
			_buttons[caseId]->SetTextColor(Osp::Graphics::Color::COLOR_BLUE);

		label.Insert(value,0);
	}
	_buttons[caseId]->SetText(label);
}
