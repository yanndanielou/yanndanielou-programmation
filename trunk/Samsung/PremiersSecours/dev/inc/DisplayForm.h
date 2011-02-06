#ifndef _DisplayForm_H_
#define _DisplayForm_H_

#include "Action.h"

#include <FUi.h>
#include <FApp.h>
#include <FBase.h>
#include <FGraphics.h>
#include <FSystem.h>

#include <vector>

using namespace Osp::Base;
using namespace Osp::Graphics;

const int LABEL_LINE_HEIGHT = 50;

class DisplayForm :
	public Osp::Ui::Controls::Form,
	public Osp::Ui::IItemEventListener
{
public:
	DisplayForm();
	virtual ~DisplayForm(void);

	bool Initialize(void);
	void MoveToForm(int index);

	void displayAction(Action* action);

protected:
	Osp::Ui::Controls::List* _pList;
	Osp::Ui::Controls::Label* _label;

public:
	virtual result OnInitializing(void);
	virtual void OnItemStateChanged(const Osp::Ui::Control& source, int index, int itemId, Osp::Ui::ItemStatus status);

private:
	Canvas* _pCanvas;
	std::vector<int> _choices;

};

#endif
