#ifndef __PREMIERSSECOURS_H__
#define __PREMIERSSECOURS_H__

#include <FApp.h>
#include <FBase.h>
#include <FGraphics.h>
#include <FSystem.h>

/**
 * [PremiersSecours] application must inherit from Application class
 * which provides basic features necessary to define an application.
 */
class PremiersSecours :
	public Osp::App::Application
{
public:
	/**
	 * [PremiersSecours] application must have a factory method that creates an instance of itself.
	 */
	static Osp::App::Application* CreateInstance(void);

public:
	PremiersSecours();
	~PremiersSecours();

public:
	/**
	 * The application must provides its name.
	 */
	Osp::Base::String GetAppName(void) const;


protected:
	/**
	 * The application must provide its ID.
	 */
	Osp::App::AppId GetAppId(void) const;

	/**
	 * The application must provide a secret code.
	 */
	Osp::App::AppSecret GetAppSecret(void) const;


public:
	/**
	 * Called when the application is initializing.
	 */
	bool OnAppInitializing(Osp::App::AppRegistry& appRegistry);

	/**
	 * Called when the application is terminating.
	 */
	bool OnAppTerminating(Osp::App::AppRegistry& appRegistry, bool forcedTermination = false);

	/**
	 * Called when the application's frame moves to the top of the screen.
	 */
	void OnForeground(void);

	/**
	 * Called when this application's frame is moved from top of the screen to the background.
	 */
	void OnBackground(void);

	/**
	 * Called when the system memory is not sufficient to run the application any further.
	 */
	void OnLowMemory(void);

	/**
	 * Called when the battery level changes.
	 */
	void OnBatteryLevelChanged(Osp::System::BatteryLevel batteryLevel);
};

#endif
