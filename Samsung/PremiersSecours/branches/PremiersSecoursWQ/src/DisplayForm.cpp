#include "DisplayForm.h"

#include "ActionsManager.h"

using namespace Osp::Ui::Controls;

DisplayForm::DisplayForm():
	_pList(0),
	_label(0)
{
}

DisplayForm::~DisplayForm(void)
{
}

bool
DisplayForm::Initialize(void)
{
	Construct(FORM_STYLE_NORMAL | FORM_STYLE_TITLE | FORM_STYLE_INDICATOR);

	return true;
}

result
DisplayForm::OnInitializing(void)
{
	return E_SUCCESS;
}

void DisplayForm::displayAction(Action* action)
{
	unsigned int textLinesCount = action->getText().size();
	int sizeText = action->getTextSize();

	_choices.clear();

	if(_pList)
	{
		_pList->RemoveAllItems();
		_pList->RemoveItemEventListener(*this);
		RemoveControl(*_pList);

		_pList = 0;
	}

	if(_label)
	{
		RemoveControl(*_label);
		_label = 0;
	}

	_label = new Label();
	String text = "";
	for(unsigned int i=0; i< textLinesCount; i++)
	{
		text = text + action->getText().at(i) + "\n";
	}
	textLinesCount = 1;

	_label->Construct(Rectangle(0, 0, 480, LABEL_LINE_HEIGHT * sizeText), text);

	_label->SetTextConfig(38, _label->GetTextStyle());
	_label->SetTextHorizontalAlignment(Osp::Ui::Controls::HorizontalAlignment(0));
	AddControl(*_label);

	_pList = new List();
	_pList->Construct(Rectangle(0,sizeText * LABEL_LINE_HEIGHT,GetClientAreaBounds().width,GetClientAreaBounds().height - sizeText * LABEL_LINE_HEIGHT), LIST_STYLE_NORMAL, LIST_ITEM_SINGLE_TEXT, 100, 0, 450 , 0);
	_pList->AddItemEventListener(*this);

	AddControl(*_pList);


	for (unsigned int i=0; i<action->getChoices().size(); i++)
	{
		Action* nextChoice = ActionsManager::GetInstance()->getActionById(action->getChoices().at(i));

		if(nextChoice)
		{	_choices.push_back(nextChoice->getId());
			_pList->AddItem(&nextChoice->getTitle() , null, null, null);
		}
	}
	this->Draw();
	this->Show();
}

void
DisplayForm::OnItemStateChanged(const Osp::Ui::Control& source, int index, int itemId, Osp::Ui::ItemStatus status)
{
	MoveToForm(index);
}

void
DisplayForm::MoveToForm(int index)
{

	Action* nextAction = ActionsManager::GetInstance()->getActionById(_choices.at(index));
	if(nextAction)
		displayAction(nextAction);
}

