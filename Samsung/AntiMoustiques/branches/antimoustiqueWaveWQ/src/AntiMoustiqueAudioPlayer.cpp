/*
 * AntiMoustiqueAudioPlayer.cpp
 *
 *  Created on: 15 juin 2010
 *      Author: Lucas
 */

#include "AntiMoustiqueAudioListener.h"
#include "AntiMoustiqueAudioPlayer.h"
#include "AntiMoustique.h"
#include "AntiMoustiqueMainForm.h"
#include <FApp.h>

using namespace Osp::Base;
using namespace Osp::Ui::Controls;
using namespace Osp::Media;
using namespace Osp::Graphics;
using namespace Osp::App;

extern AntiMoustiqueMainForm 		*g_pMediaPlayerMainForm;
extern AntiMoustique				*g_pAudioPlayer;

AntiMoustiqueAudioPlayer::AntiMoustiqueAudioPlayer(void):
_turnSoundOnIfSilent(false)
{
	pauedFlag = false;
	openFlag = false;
	pListener = null;
	pPlayer = null;
	pButton1 = null;
	pButton4 = null;
	playerVolume = 50;

	result r = E_SUCCESS;

	// Create the listener
	pListener = new AudioPlayerListener;

	// Create Player using the listener and the Canvas
	pPlayer = new Player();
	if( !pPlayer)
	{
		AppLog(">>>>>> pPlayer = new Player() has failed\n");
	}


	r = pPlayer->Construct(*pListener, null);
	if( IsFailed(r))
	{
		AppLog(">>>>>> pPlayer->Construct has failed\n");
	}

}

AntiMoustiqueAudioPlayer::~AntiMoustiqueAudioPlayer(void)
{
	if( pListener )
	{
		delete pListener;
		pListener = null;
	}

	if( pPlayer )
	{
		PlayerState nowState = pPlayer->GetState();
		if( nowState == PLAYER_STATE_PLAYING || nowState == PLAYER_STATE_PAUSED )
		{
			pPlayer->Stop();
			pPlayer->Close();
		}else if(nowState == PLAYER_STATE_OPENED || nowState == PLAYER_STATE_ENDOFCLIP || nowState == PLAYER_STATE_STOPPED )
		{
			pPlayer->Close();
		}
		delete pPlayer;
		pPlayer = null;
	}

    g_pAudioPlayer = null;

}

bool
AntiMoustiqueAudioPlayer::ConstructAudioPlayer()
{
	result r = E_SUCCESS;
//	Osp::Graphics::Rectangle rect = Rectangle(0,0,480,80);

	// button for mp3

	int appHeight = Application::GetInstance()->GetAppFrame()->GetFrame()->GetHeight();
	int appWidth = Application::GetInstance()->GetAppFrame()->GetFrame()->GetWidth();

	Osp::Graphics::Rectangle rect1 = Rectangle(appWidth * 0.2 ,appHeight*0.1,appWidth * 0.6,appHeight*0.2);
	Osp::Graphics::Rectangle rect4 = Rectangle(appWidth*0.5,appHeight*0.7,appWidth*0.3,appHeight*0.1);

	//Do construct!!

	//Form has indicator bar, Softkey and title
	r = Construct(FORM_STYLE_NORMAL|FORM_STYLE_TITLE|FORM_STYLE_INDICATOR);
	if(IsFailed(r))
	{
		AppLog( ">>>>>> Construct() has been failed\n");
		return false;
	}

	SetTitleText(L"Anti-Mosquitoes Player");


	pButton1 = new Button();
	r = pButton1->Construct(rect1,L"---");
	if(IsFailed(r))
	{
		AppLog( ">>>>>> pButton1 create has been failed\n");
		return false;
	}

	pButton4 = new Button();
	r = pButton4->Construct(rect4,L"START");
	if(IsFailed(r))
	{
		AppLog( ">>>>>> pButton4 create has been failed\n");
		return false;
	}


	// Add button controls


	// For MP3 Play
	pButton1->SetActionId(IDC_PLAYER_STOP);
	pButton1->AddActionEventListener(*this);
	r = AddControl(*pButton1);
	if(IsFailed(r))
	{
		AppLog( ">>>>>> pButton1 AddControl has been failed\n");
		return false;
	}

	pButton4->SetActionId(IDC_PLAYER_LOOP);
	pButton4->AddActionEventListener(*this);
	r = AddControl(*pButton4);
	if(IsFailed(r))
	{
		AppLog( ">>>>>> pButton4 AddControl has been failed\n");
		return false;
	}

	AddKeyEventListener(*this);

	return true;
}


void
AntiMoustiqueAudioPlayer::AudioPlayerOpen()
{
	result r = E_SUCCESS;


	if( openFlag == false )
	{
		// All right reserved of these sample audio clipes by Samsung Electorics.

		// Open mp3
		r = pPlayer->OpenFile(String(L"/Res/AntiMoustique.mp3")); // Sync Open

		if( IsFailed(r))
		{
			AppLog(">>>>>> (AudioPlayerOpen) pPlayer->OpenFile has failed: %s\n", GetErrorMessage(r));

			return;
		}

		// Set Volume for proper mixing
		pPlayer->SetVolume(100);

		openFlag = true;

	}else{
		// just Playback
	}


}

void
AntiMoustiqueAudioPlayer::SetNotOpened()
{
	openFlag = false;
}

void
AntiMoustiqueAudioPlayer::OnActionPerformed(const Control& source, int actionId)
{
	switch(actionId)
	{


	case IDC_PLAYER_LOOP:
			if (!pPlayer->IsLooping() )
			{
				checkSilentModeThenAudioPlayerPlay();
			}
			else
			{
				pPlayer->SetLooping(false);
				pButton4->SetText(L"START");
				AudioPlayerStop();
			}
			RefreshForm();
			break;


	case IDC_MENU:
			if( pPlayer->GetState() == PLAYER_STATE_PLAYING || pPlayer->GetState() == PLAYER_STATE_PAUSED)
			{
				pPlayer->Stop();

			}

			if (pPlayer->IsLooping() )
			{
				pPlayer->SetLooping(false);
				pButton4->SetText(L"START");
			}

			pButton1->SetTextColor(Color(0,44,55,10));
			pButton4->SetTextColor(Color(0,44,55,10));

			// goto Main menu after closing all of players
			AudioPlayerClose();
			SetNotOpened();
			GotoMainMenu();
			break;
	case ID_BUTTON_SWITCH_ON:
		if(__pPopup)
		{
			delete __pPopup;
			__pPopup = null;
			_turnSoundOnIfSilent = true;
			AudioPlayerPlay();
		}
		break;
	case ID_BUTTON_SWITCH_OFF:
		if(__pPopup)
		{
			delete __pPopup;
			__pPopup = null;
			_turnSoundOnIfSilent = false;
		}
		break;
	default:
		break;
	}

}

void
AntiMoustiqueAudioPlayer::GotoMainMenu()
{
	result r = E_SUCCESS;

	//Get Frame
	Frame *pFrame = (Frame *)GetParent();
	if( !pFrame )
	{
		AppLog( ">>>>>>  GetParent has failed.\n");
		return;
	}

	if( !ReturnToMainForm( pFrame ) )	return;

	//Redraw
	pFrame->Draw();
	r = pFrame->Show();
	if( IsFailed(r))
	{
		AppLog( ">>>>>>  Show() has failed.\n");
		return;
	}
}

void AntiMoustiqueAudioPlayer::checkSilentModeThenAudioPlayerPlay()
{
	bool silentModeOn;
		Osp::System::SettingInfo::GetValue("SilentMode",silentModeOn);

		AppLog("Silent mode: %d \n", silentModeOn);

		if(silentModeOn)
		{
			int appHeight = Application::GetInstance()->GetAppFrame()->GetFrame()->GetHeight();
			int appWidth = Application::GetInstance()->GetAppFrame()->GetFrame()->GetWidth();

			int popupWidth  = appWidth * 0.7;
			int popupHeight = appHeight * 0.7;

			int buttonWidth  = popupWidth * 0.3;
			int buttonHeight = popupHeight * 0.1;

			/* Create Popup */
			__pPopup = new Popup();
			__pPopup->Construct(true, Dimension(popupWidth,popupHeight));
			__pPopup->SetTitleText("Silent Mode is On");
			__pPopup->SetShowState(true);
			__pPopup->Show();

			/* Create Buttons */
			__pButtonSwitchOff = new Button();
			__pButtonSwitchOff->Construct(Rectangle(popupWidth*0.15,popupHeight*0.7,buttonWidth, buttonHeight), L"YES");
			__pButtonSwitchOff->SetActionId(ID_BUTTON_SWITCH_ON);
			__pButtonSwitchOff->AddActionEventListener(*this);
			__pPopup->AddControl(*__pButtonSwitchOff);

			/* Create Buttons */
			__pButtonSwitchOn = new Button();
			__pButtonSwitchOn->Construct(Rectangle(popupWidth*0.7 ,popupHeight*0.7,buttonWidth, buttonHeight), L"NO");
			__pButtonSwitchOn->SetActionId(ID_BUTTON_SWITCH_OFF);
			__pButtonSwitchOn->AddActionEventListener(*this);
			__pPopup->AddControl(*__pButtonSwitchOn);

			/* Create Label */
			__pLabel = new Label();
			__pLabel->Construct(Rectangle(appWidth*0.1, popupHeight*0.1, popupWidth*0.8, popupHeight*0.5), L"Silent mode is on. Do you want to play the sound anyway?");
			__pLabel->SetTextConfig(20, LABEL_TEXT_STYLE_NORMAL);
			__pLabel->SetTextHorizontalAlignment(ALIGNMENT_CENTER);
			__pLabel->SetTextVerticalAlignment(ALIGNMENT_MIDDLE);
			__pPopup->AddControl(*__pLabel);
		}
		else
		{
			AudioPlayerPlay();
		}
}


void AntiMoustiqueAudioPlayer::AudioPlayerPlay()
{

	pPlayer->SetLooping(true);
	pButton4->SetText(L"STOP");

	result r = E_SUCCESS;
	PlayerState nowState = pPlayer->GetState();

	// Re-Play the playing.
	AppLog(">>>>>>Now state : %d ",nowState);
	if( nowState == PLAYER_STATE_ENDOFCLIP || nowState == PLAYER_STATE_OPENED || nowState == PLAYER_STATE_INITIALIZED || nowState == PLAYER_STATE_STOPPED || nowState == PLAYER_STATE_PAUSED)
	{
		r = pPlayer->Play();
		pauedFlag = false;
		if(IsFailed(r))
		{
			AppLog(">>>>>> (AudioPlayer::AudioPlayerPlay) Play has failed: %s\n", GetErrorMessage(r));
			AppLog(">>>>>>Now state in IsFailed: %d ",nowState);
			return;
		}
		if(pPlayer == pPlayer)
		{
			pButton1->SetTextColor(Color(200,25,150,10));
			pButton1->SetText(L"Playing");


		}
		RefreshForm();

	}



}

void
AntiMoustiqueAudioPlayer::AudioPlayerStop()
{
	PlayerState nowState = pPlayer->GetState();

	if (pPlayer->IsLooping() )
	{
		pPlayer->SetLooping(false);
		if(pPlayer == pPlayer) pButton4->SetText(L"START");
	}

	// Stop the playing.
	if(nowState == PLAYER_STATE_PLAYING || nowState == PLAYER_STATE_PAUSED)
	{
		pPlayer->Stop();
		if(pPlayer == pPlayer)
		{

			pButton1->SetText(L"---");
			pButton1->SetTextColor(Color(0,44,55,10));


		}
		RefreshForm();
	}

}

void
AntiMoustiqueAudioPlayer::AudioPlayerClose()
{
	pPlayer->Close();
}


void
AntiMoustiqueAudioPlayer::RefreshForm(void)
{
	result r = E_SUCCESS;
	//Get Frame
	Frame *pFrame = (Frame *)GetParent();

	r = pFrame->Draw();
	if ( r != E_SUCCESS )
	{
		AppLog( ">>>>>>  Frame Draw was failed\n");
	}

	r = pFrame->Show();
	if ( r != E_SUCCESS )
	{
		AppLog( ">>>>>>  Frame Show was failed\n");
	}

}

bool
AntiMoustiqueAudioPlayer::ReturnToMainForm(Frame* pFrame)
{

	result r = E_SUCCESS;

	if ( !g_pMediaPlayerMainForm )
	{
		//------------------------------
		// Create Instance of Forms
		//------------------------------
		g_pMediaPlayerMainForm = new AntiMoustiqueMainForm();
		if( !g_pMediaPlayerMainForm )
		{
			AppLog( ">>>>>> new g_pMediaPlayerMainForm() has been failed\n");
			return false;
		}

		//------------------------------
		// Construct form
		//------------------------------
		if( g_pMediaPlayerMainForm->ConstructMediaPlayerMainForm() == false )
		{
			AppLog( ">>>>>> g_pMultiFormBase->InitializeMultiFormBase( pFrame ) has been failed\n");
			delete g_pMediaPlayerMainForm;
			g_pMediaPlayerMainForm= null;
			return false;
		}


		//------------------------------
		// Attach Form to Frame
		//------------------------------
		r = pFrame->AddControl( *g_pMediaPlayerMainForm );
		if( IsFailed(r))
		{
			AppLog( ">>>>>> pFrame->AddControl( *g_pMultiFormBase ) has been failed\n");
			delete g_pMediaPlayerMainForm;
			g_pMediaPlayerMainForm= null;
			return false;
		}

		//------------------------------
		// Initialize Form
		//------------------------------
		if( g_pMediaPlayerMainForm->InitializeMediaPlayerMainForm( pFrame ) == false )
		{
			AppLog( ">>>>>> g_pMultiFormBase->InitializeFormBase() has been failed\n");
			return false;
		}
	}

	//Switch to FormBase
	r = pFrame->SetCurrentForm( *g_pMediaPlayerMainForm );
	if( IsFailed(r))
	{
		AppLog( ">>>>>>  SetCurrentForm( *g_pMultiFormBase ) has failed.\n");
		return false;
	}

	return true;

}
void AntiMoustiqueAudioPlayer::OnKeyReleased (const Control &source, Osp::Ui::KeyCode  keyCode)
{

}

void AntiMoustiqueAudioPlayer::OnKeyLongPressed (const Control &source, Osp::Ui::KeyCode  keyCode)
{

}

void AntiMoustiqueAudioPlayer::OnKeyPressed (const Control &source, Osp::Ui::KeyCode  keyCode)
{

	if(pPlayer != null)
	{
		if(pPlayer->GetState() >= PLAYER_STATE_OPENED && pPlayer->GetState() < PLAYER_STATE_CLOSED	  )
		{

			if(keyCode == Osp::Ui::KEY_SIDE_UP)
			{
				playerVolume = playerVolume <= 90 ? playerVolume + 5 : playerVolume;


				// Up button is pressed
				pPlayer->SetVolume(playerVolume);



			}else if(keyCode == Osp::Ui::KEY_SIDE_DOWN)
			{
				// Down button is pressed
				playerVolume = playerVolume >= 5 ? playerVolume - 5 : playerVolume;

				pPlayer->SetVolume(playerVolume);
			}
		}
	}

}
