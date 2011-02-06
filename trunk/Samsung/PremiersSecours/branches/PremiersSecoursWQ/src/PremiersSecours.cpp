/**
 * Name        : PremiersSecours
 * Version     : $(version)
 * Vendor      : 
 * Description : 
 */

#include "PremiersSecours.h"

#include "DisplayForm.h"
#include "ActionsManager.h"

#include "FBase.h"
#include "FUiControls.h"


using namespace Osp::Ui::Controls;

using namespace Osp::App;
using namespace Osp::Base;
using namespace Osp::System;
using namespace Osp::Graphics;

PremiersSecours::PremiersSecours()
{
}

PremiersSecours::~PremiersSecours()
{
}

Application*
PremiersSecours::CreateInstance(void)
{
	// Create the instance through the constructor.
	return new PremiersSecours();
}

String
PremiersSecours::GetAppName(void) const
{
	//WARNING:
	// This method is automatically generated.
	// Please don't modify it!

	// Return the name of the application.
	static String appName(L"PremiersSecours");
	return appName;
}

AppId
PremiersSecours::GetAppId(void) const
{
	//WARNING:
	// This method is automatically generated.
	// Please don't modify it!

	// Return the application ID.
	static AppId appId(L"6kaic6pfd2");
	return appId;
}

AppSecret
PremiersSecours::GetAppSecret(void) const
{
	//WARNING:
	// This method is automatically generated.
	// Please don't modify it!

	// Return the secret code of the application.
	static AppSecret appSecret(L"761ECC80BCFA6574F8B5BF0B492781CB");
	return appSecret;
}

bool
PremiersSecours::OnAppInitializing(AppRegistry& appRegistry)
{

	// TODO:
	// Initialize UI resources and application specific data.
	// The application's permanent data and context can be obtained from the appRegistry.
	//
	// If this method is successful, return true; otherwise, return false.
	// If this method returns false, the application will be terminated.

	ActionsManager::GetInstance()->init();

	DisplayForm* pForm = new DisplayForm();

	pForm->Initialize();
	pForm->displayAction(ActionsManager::GetInstance()->getCurrentAction());

	GetAppFrame()->GetFrame()->AddControl(*pForm);
	GetAppFrame()->GetFrame()->SetCurrentForm(*pForm);

	return true;
}

bool
PremiersSecours::OnAppTerminating(AppRegistry& appRegistry, bool forcedTermination)
{
	// TODO:
	// Deallocate resources allocated by this application for termination.
	// The application's permanent data and context can be saved via appRegistry.

	ActionsManager* inst = ActionsManager::GetInstance();
	inst->eraseMap();
	delete inst;

	return true;
}

void
PremiersSecours::OnForeground(void)
{
	// TODO:
	// Start or resume drawing when the application is moved to the foreground.
}

void
PremiersSecours::OnBackground(void)
{
	// TODO:
	// Stop drawing when the application is moved to the background.
	//this->Terminate();
}

void
PremiersSecours::OnLowMemory(void)
{
	// TODO:
	// Free unused resources or close the application.
}

void
PremiersSecours::OnBatteryLevelChanged(BatteryLevel batteryLevel)
{
	// TODO:
	// Handle any changes in battery level here.
	// Stop using multimedia features(camera, mp3 etc.) if the battery level is CRITICAL.
}
