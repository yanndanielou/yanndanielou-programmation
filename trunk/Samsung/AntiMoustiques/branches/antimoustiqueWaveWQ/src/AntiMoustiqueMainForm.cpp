/*
 * AntiMoustiqueMainForm.cpp
 *
 *  Created on: 15 juin 2010
 *      Author: Lucas
 */

#include "AntiMoustiqueMainForm.h"
#include "AntiMoustiqueAudioPlayer.h"



using namespace Osp::Base;
using namespace Osp::Ui::Controls;
using namespace Osp::Media;
using namespace Osp::Graphics;


extern AntiMoustiqueMainForm		*g_pMediaPlayerMainForm;
extern AntiMoustiqueAudioPlayer				*g_pAudioPlayer;

AntiMoustiqueMainForm::AntiMoustiqueMainForm(void)
{
	pBtnAudioPlayer = null;
	pLabel = null;
}

AntiMoustiqueMainForm::~AntiMoustiqueMainForm(void)
{
	if( pBtnAudioPlayer )
	{
		pBtnAudioPlayer = null;
	}
	arItems.RemoveAll();
}


bool AntiMoustiqueMainForm::ConstructMediaPlayerMainForm()
{
	result r = E_SUCCESS;

	//Do construct!!

	r = Construct(FORM_STYLE_NORMAL|FORM_STYLE_TITLE|FORM_STYLE_INDICATOR);
	if(IsFailed(r))
	{
		AppLog(">>>>>> (MultiFormBase::InitializeMultiFormBase) Construct(FORM_STYLE_NORMAL, 0) has been failed\n");
		return false;
	}

	SetTitleText(L"Anti-Mosquitoes");

	return true;


}

bool AntiMoustiqueMainForm::InitializeMediaPlayerMainForm( Frame *pFrame )
{
	//Create list control
	if( !CreateMainList() ) return false;

	return true;
}

void AntiMoustiqueMainForm::OnActionPerformed(const Osp::Ui::Control& source, int actionId)
{
	switch( actionId )
	{

	case AC_BUTTON_AUDIO:
		StartAudioPlayer();
		break;
	case ID_BUTTON_OK:
		delete __pPopup;
		__pPopup = 0;
		break;

	}


}

void AntiMoustiqueMainForm::StartAudioPlayer()
{
	result r = E_SUCCESS;

	Frame *pFrame = (Frame *)GetParent();
	if( !pFrame )
	{
		AppLog(">>>>>>  GetParent has failed.\n");
		return ;
	}

	if ( !g_pAudioPlayer )
	{
		g_pAudioPlayer = new AntiMoustiqueAudioPlayer();
		if( !g_pAudioPlayer )
		{
			AppLog( ">>>>>> new AudioPlay() has been failed\n");
			return;
		}

		if( g_pAudioPlayer->ConstructAudioPlayer() == false )
		{
			AppLog( ">>>>>>ConstructVideoPlay has been failed\n");
			delete g_pAudioPlayer;
			g_pAudioPlayer= null;
			return;
		}

		//------------------------------
		// Attach Form to Frame
		//------------------------------
		r = pFrame->AddControl( *g_pAudioPlayer );
		if( IsFailed(r))
		{
			AppLog( ">>>>>> pFrame->AddControl( *g_pAudioPlayer ) has been failed\n");
			delete g_pAudioPlayer;
			g_pAudioPlayer= null;
			return;
		}
	}

	//Assign the current form as Form1
	r = pFrame->SetCurrentForm( *g_pAudioPlayer );
	if( IsFailed(r))
	{
		AppLog(">>>>>>  SetCurrentForm( *g_pAudioPlayer ) has failed.\n");
		return ;
	}

	//Redraw form
	pFrame->Draw();
	r = pFrame->Show();
	if( IsFailed(r))
	{
		AppLog(">>>>>>  Show() has failed.\n");
		return ;
	}

	g_pAudioPlayer->AudioPlayerOpen();

}


bool AntiMoustiqueMainForm::GetSelectedItemInfo( int nSelIndex )
{
	return true;
}


bool AntiMoustiqueMainForm::CreateMainList()
{
	result r1 = E_SUCCESS;
	result r2 = E_SUCCESS;

	//==========================
	// Create List
	//==========================
	pBtnAudioPlayer = new Button;
	pLabel = new Label();


	if( !pBtnAudioPlayer || !pLabel ) return false;

	int nFormWidth	= GetWidth();
	int nFormHeight = GetHeight();

	int buttonWidth = nFormWidth*0.8;
	int buttonHeight = nFormHeight/6;

	//Osp::Graphics::Rectangle rtAudioPlayer = Osp::Graphics::Rectangle( 20, 20, nFormWidth-40, nFormHeight/6 );
	Osp::Graphics::Rectangle rtAudioPlayer = Osp::Graphics::Rectangle( nFormWidth*0.1, nFormHeight*0.1, buttonWidth,  buttonHeight);


	int labelWidth = nFormWidth*0.8;
	int labelHeight = nFormHeight - buttonHeight - nFormHeight*0.2;

	r1 = pLabel->Construct(Rectangle(0, 20 + nFormHeight/6, nFormWidth, nFormHeight - (20 + nFormHeight/6)), L"This application generates high-frequence sounds which can be harmful to health.\nDon't use this application if you suffer from a tinnitus or if you own cats or dogs.\nTinnitus is the perception of sound within the human ear in the absence of corresponding external sound");

	pLabel->SetName(L"Label1");
	AddControl(*pLabel);
	pLabel->SetTextVerticalAlignment(ALIGNMENT_MIDDLE);
	pLabel->SetTextHorizontalAlignment(ALIGNMENT_LEFT);

	r2 = pBtnAudioPlayer->Construct( rtAudioPlayer, String("Launch anti-mosquitoes!"));

	if(IsFailed(r2) || IsFailed(r1)  )
	{
		AppLog( ">>>>> (CreateMainList) List constructing has failed\n" );
		return false;
	}

	//==========================
	// Add list to form
	//==========================
	r2 = AddControl(*pBtnAudioPlayer);

	if( IsFailed(r2) )
	{
		AppLog( ">>>>> (CreateMainList) AddControl has failed\n" );
		return false;
	}

	pBtnAudioPlayer->SetActionId(AC_BUTTON_AUDIO);

	//==========================
	// Add listener of list
	//==========================
	pBtnAudioPlayer->AddActionEventListener(*this);


	/* Create Popup */
	int popupWidth = GetWidth()*0.8;
	int popupHeight = GetHeight()*0.8;

	__pPopup = new Popup();
	__pPopup->Construct(true, Dimension(popupWidth,popupHeight));
	__pPopup->SetTitleText("Health warning!");
	__pPopup->SetShowState(true);

	/* Create Label */
	__pLabel = new Label();
	__pLabel->Construct(Rectangle(0, 0, popupWidth, popupHeight*0.7), L"This application can be harmful to health (particularly to people who suffer from a tinnitus) due to the high-frequence sounds generated");
	__pLabel->SetTextConfig(22, LABEL_TEXT_STYLE_NORMAL);
	__pLabel->SetTextHorizontalAlignment(ALIGNMENT_CENTER);
	__pLabel->SetTextVerticalAlignment(ALIGNMENT_MIDDLE);
	__pPopup->AddControl(*__pLabel);

	/* Create Buttons */
	__pButtonOk = new Button();
	//__pButtonOk->Construct(Rectangle(125,320,150, 70), L"OK");
	__pButtonOk->Construct(Rectangle(popupWidth*0.3,popupHeight*0.75,popupWidth*0.4, popupHeight*0.1), L"OK");
	__pButtonOk->SetActionId(ID_BUTTON_OK);
	__pButtonOk->AddActionEventListener(*this);
	__pPopup->AddControl(*__pButtonOk);

	__pPopup->Show();



	return true;
}
