/**
 * Name        : SudokuSolver
 * Version     : $(version)
 * Vendor      : 
 * Description : 
 */

#include "SudokuSolver.h"
#include "GameForm.h"
#include "Game.h"

using namespace Osp::App;
using namespace Osp::Base;
using namespace Osp::System;
using namespace Osp::Graphics;

SudokuSolver::SudokuSolver()
{
}

SudokuSolver::~SudokuSolver()
{
}

Application*
SudokuSolver::CreateInstance(void)
{
	// Create the instance through the constructor.
	return new SudokuSolver();
}

String
SudokuSolver::GetAppName(void) const
{
	//WARNING:
	// This method is automatically generated.
	// Please don't modify it!

	// Return the name of the application.
	static String appName(L"SudokuSolver");
	return appName;
}

AppId
SudokuSolver::GetAppId(void) const
{
	//WARNING:
	// This method is automatically generated.
	// Please don't modify it!

	// Return the application ID.
	static AppId appId(L"2l6da4751t");
	return appId;
}

AppSecret
SudokuSolver::GetAppSecret(void) const
{
	//WARNING:
	// This method is automatically generated.
	// Please don't modify it!

	// Return the secret code of the application.
	static AppSecret appSecret(L"EAD745E64EE5CF3C048574308D2FC96B");
	return appSecret;
}

bool
SudokuSolver::OnAppInitializing(AppRegistry& appRegistry)
{
	// TODO:
	// Initialize UI resources and application specific data.
	// The application's permanent data and context can be obtained from the appRegistry.
	//
	// If this method is successful, return true; otherwise, return false.
	// If this method returns false, the application will be terminated.

	GameForm* pForm = new GameForm(3);

	pForm->Initialize();

	GetAppFrame()->GetFrame()->AddControl(*pForm);
	GetAppFrame()->GetFrame()->SetCurrentForm(*pForm);
	return true;
}

bool
SudokuSolver::OnAppTerminating(AppRegistry& appRegistry, bool forcedTermination)
{
	// TODO:
	// Deallocate resources allocated by this application for termination.
	// The application's permanent data and context can be saved via appRegistry.

	//Delete the
	Game* game = Game::getInstance();

	delete game;

	return true;
}

void
SudokuSolver::OnForeground(void)
{
	// TODO:
	// Start or resume drawing when the application is moved to the foreground.
}

void
SudokuSolver::OnBackground(void)
{
	// TODO:
	// Stop drawing when the application is moved to the background.
}

void
SudokuSolver::OnLowMemory(void)
{
	// TODO:
	// Free unused resources or close the application.
}

void
SudokuSolver::OnBatteryLevelChanged(BatteryLevel batteryLevel)
{
	// TODO:
	// Handle any changes in battery level here.
	// Stop using multimedia features(camera, mp3 etc.) if the battery level is CRITICAL.
}
