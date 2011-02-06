#ifndef _NO_RESULT_POPUP_H_
#define _NO_RESULT_POPUP_H_

#include <FBase.h>
#include <FUi.h>
#include <FApp.h>


class NoResultPopup :
	public Osp::Ui::Controls::Popup,
	public Osp::Ui::IActionEventListener
{
public:
	NoResultPopup();
	virtual ~NoResultPopup(void);

	result Construct(bool hasTitle,Osp::Graphics::Dimension dim);
	void OnActionPerformed( const Osp::Ui::Control& source, int actionId );

	bool Initialize(void);

protected:
	static const int ID_OK_BUTTON = 201;

private:
};

#endif //_NO_RESULT_POPUP_H_
