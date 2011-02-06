/*
 * AntiMoustiqueAudioPlayer.h
 *
 *  Created on: 15 juin 2010
 *      Author: Lucas
 */

#ifndef ANTIMOUSTIQUEAUDIOPLAYER_H_
#define ANTIMOUSTIQUEAUDIOPLAYER_H_

#pragma once

#include <FBase.h>
#include <FGraphics.h>
#include <FUi.h>
#include <FApp.h>
#include <FMedia.h>

#include "AntiMoustiqueRessources.h"
#include "AntiMoustiqueAudioPlayer.h"
#include "AntiMoustiqueAudioListener.h"
using namespace Osp::Ui::Controls;

class AntiMoustiqueAudioPlayer	:
	public Osp::Ui::Controls::Form,
	public Osp::Ui::IActionEventListener,
	public Osp::Ui::IKeyEventListener
{
public:
		static const int ID_BUTTON_SWITCH_ON = 253;
		static const int ID_BUTTON_SWITCH_OFF = ID_BUTTON_SWITCH_ON + 1;

	AntiMoustiqueAudioPlayer(void);
	~AntiMoustiqueAudioPlayer(void);

public
:// Action event

	void AudioPlayerStop();
	void AudioPlayerOpen();
	void RefreshForm(void);
	bool ConstructAudioPlayer();


private:
	void SetNotOpened();
	void AudioPlayerPlay();
	void AudioPlayerClose();
	void checkSilentModeThenAudioPlayerPlay();
	void GotoMainMenu();
	bool ReturnToMainForm(Osp::Ui::Controls::Frame* pFrame);
	void OnActionPerformed(const Control& source, int actionId);
	void OnKeyPressed (const Control &source, Osp::Ui::KeyCode  keyCode);
	void OnKeyReleased (const Control &source, Osp::Ui::KeyCode  keyCode);
	void OnKeyLongPressed (const Control &source, Osp::Ui::KeyCode  keyCode);

public:
	Osp::Media::Player *pPlayer;
	Osp::Ui::Controls::Button* pButton1;
	bool pauedFlag;
	int playerVolume;


private:
    Osp::Graphics::Canvas *pCanvas;

	Osp::Ui::Controls::Button* pButton4;
	Osp::Ui::Controls::Label*  pLabel;
	AudioPlayerListener	*pListener;
	bool openFlag;
	bool _turnSoundOnIfSilent;
	Popup * __pPopup;
	Button * __pButtonSwitchOn;
	Button * __pButtonSwitchOff;
	Label * __pLabel;

};


#endif /* ANTIMOUSTIQUEAUDIOPLAYER_H_ */
