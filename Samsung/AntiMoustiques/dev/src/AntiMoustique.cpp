/**
 * Name        : AntiMoustique
 * Version     : 
 * Vendor      : 
 * Description : 
 */


#include "AntiMoustique.h"
#include "AntiMoustiqueMainForm.h"
#include "AntiMoustiqueAudioPlayer.h"
#include <FSysPowerManager.h>


AntiMoustiqueMainForm		*g_pMediaPlayerMainForm;
AntiMoustiqueAudioPlayer				*g_pAudioPlayer;


using namespace Osp::App;
using namespace Osp::Base;
using namespace Osp::System;
using namespace Osp::Ui;
using namespace Osp::Ui::Controls;
using namespace Osp::Media;


AntiMoustique::AntiMoustique()
{
}

AntiMoustique::~AntiMoustique()
{
}

Application*
AntiMoustique::CreateInstance(void)
{
	// Create the instance through the constructor.
	return new AntiMoustique();
}

bool
AntiMoustique::OnAppInitializing(AppRegistry& appRegistry)
{
	// TODO:
	// Initialize UI resources and application specific data.
	// The application's permanent data and context can be obtained from the appRegistry.
	//
	// If this method is successful, return true; otherwise, return false.
	// If this method returns false, the application will be terminated.

	// Uncomment the following statement to listen to the screen on/off events.
	//PowerManager::SetScreenEventListener(*this);

	Frame *pFrame = null;

		IAppFrame* pAppFrame = GetAppFrame();
		if (null == pAppFrame)
		{
			AppLog("GetAppFrame() has failed.\n");
			goto CATCH;
		}

		// Get frame
		pFrame = pAppFrame->GetFrame();
		if (!pFrame)
		{
			AppLog("GetAppFrame()->GetFrame() has failed.\n");
			goto CATCH;
		}
		// initialize forms
			if( !StartMainForm( pFrame ) )	return false;

			//Show Form!
			pFrame->Draw();
			pFrame->Show();

			// You should comment following statement if you do not listen to the screen on/off events.
			PowerManager::SetScreenEventListener(*this);

			return true;

		CATCH:
			return false;
}

bool
AntiMoustique::OnAppTerminating(AppRegistry& appRegistry, bool forcedTermination)
{
	// TODO:
	// Deallocate resources allocated by this application for termination.
	// The application's permanent data and context can be saved via appRegistry.
	return true;
}

void
AntiMoustique::OnForeground(void)
{
	// TODO:
	// Start or resume drawing when the application is moved to the foreground.
	//Osp::System::PowerManager::KeepScreenOnState(true, false);

	    if (  g_pAudioPlayer != null )
	    {
	    	if(g_pAudioPlayer->pPlayer != null)
	    	{
	    		if ( g_pAudioPlayer->pPlayer->GetState() == PLAYER_STATE_PAUSED )
	    			g_pAudioPlayer->AudioPlayerStop();
	    	}
	    }

}

void
AntiMoustique::OnBackground(void)
{
	// TODO:
	// Stop drawing when the application is moved to the background.
	if(g_pAudioPlayer && g_pAudioPlayer->pPlayer)
		g_pAudioPlayer->AudioPlayerStop();

    // g_pAudioPlayer is not paused for the background working


}

void
AntiMoustique::OnLowMemory(void)
{
	// TODO:
	// Free unused resources or close the application.
}

void
AntiMoustique::OnBatteryLevelChanged(BatteryLevel batteryLevel)
{
	// TODO:
	// Handle any changes in battery level here.
	// Stop using multimedia features(camera, mp3 etc.) if the battery level is CRITICAL.
}
bool
AntiMoustique::StartMainForm(Frame* pFrame)
{
	result r = E_SUCCESS;
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
	// Construct Main Menu form
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
	// Initialize Form (Creating buttons)
	//------------------------------
	if( g_pMediaPlayerMainForm->InitializeMediaPlayerMainForm( pFrame ) == false )
	{
		AppLog( ">>>>>> g_pMultiFormBase->InitializeFormBase() has been failed\n");
		return false;
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

void
AntiMoustique::OnScreenOn (void)
{
	// TODO:
	// Get the released resources or resume the operations that were paused or stopped in OnScreenOff().
}

void
AntiMoustique::OnScreenOff (void)
{
	// TODO:
	//  Unless there is a strong reason to do otherwise, release resources (such as 3D, media, and sensors) to allow the device to enter the sleep mode to save the battery.
	// Invoking a lengthy asynchronous method within this listener method can be risky, because it is not guaranteed to invoke a callback before the device enters the sleep mode.
	// Similarly, do not perform lengthy operations in this listener method. Any operation must be a quick one.
	if(g_pAudioPlayer && g_pAudioPlayer->pPlayer)
		g_pAudioPlayer->AudioPlayerStop();

}
