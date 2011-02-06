/*
 * AntiMoustiqueAudioListener.h
 *
 *  Created on: 15 juin 2010
 *      Author: Lucas
 */

#ifndef ANTIMOUSTIQUEAUDIOLISTENER_H_
#define ANTIMOUSTIQUEAUDIOLISTENER_H_

#pragma once

#include <FBase.h>
#include <FGraphics.h>
#include <FMedia.h>
#include <FApp.h>
#include <FUi.h>

class AudioPlayerListener :
	public Osp::Media::IPlayerEventListener
{
public:
	AudioPlayerListener(void);
	~AudioPlayerListener(void);
	void OnPlayerOpened(result r);
	void OnPlayerEndOfClip(void);
	void OnPlayerBuffering(int percent);
	void OnPlayerErrorOccurred(Osp::Media::PlayerErrorReason r);
	void OnPlayerInterrupted(void);
	void OnPlayerReleased(void);

};
#endif /* ANTIMOUSTIQUEAUDIOLISTENER_H_ */
