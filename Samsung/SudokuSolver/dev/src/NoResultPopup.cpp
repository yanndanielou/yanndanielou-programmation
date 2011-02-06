#include "NoResultPopup.h"
#include "MultiResolution.h"


using namespace Osp::App;
using namespace Osp::Base;
using namespace Osp::Ui;
using namespace Osp::Ui::Controls;
using namespace Osp::Graphics;


NoResultPopup::NoResultPopup()
{
}

NoResultPopup::~NoResultPopup(void)
{
}


result
NoResultPopup::Construct(bool hasTitle, Osp::Graphics::Dimension dim)
{
	result r = E_SUCCESS;

	// Construct Popup
	r = Popup::Construct( hasTitle, dim);


	int POPUP_WIDTH = GetWidth();
	int POPUP_HEIGHT = GetHeight();

	int BUTTON_WIDTH  = POPUP_WIDTH * 0.8;
	int BUTTON_HEIGHT = POPUP_HEIGHT * 0.2;


	//Creation of Button "OK"

	int x = 0.1 * POPUP_WIDTH;
	int y = 0.7 * POPUP_HEIGHT;

	String text = "The sudoku is impossible and can't be solved";
	Label *label = new Label();
	label->Construct(Rectangle(POPUP_WIDTH * 0.1, POPUP_HEIGHT * 0.1, POPUP_WIDTH * 0.8, POPUP_HEIGHT * 0.6), text);
	AddControl(*label);

	Button *okButton = new Button();
	okButton->Construct(__R(x, y, BUTTON_WIDTH, BUTTON_HEIGHT));
	okButton->SetText(Osp::Base::String("OK"));
	okButton->SetActionId(ID_OK_BUTTON);
	okButton->AddActionEventListener(*this);
	AddControl(*okButton);

	return r;
}


void
NoResultPopup::OnActionPerformed(const Osp::Ui::Control& source, int actionId)
{
	if(actionId == ID_OK_BUTTON)
	{
		delete this;
	}
}


