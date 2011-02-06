/*
 * AntiMoustiqueAudioListener.cpp
 *
 *  Created on: 15 juin 2010
 *      Author: Lucas
 */

#include "AntiMoustiqueAudioListener.h"
#include "AntiMoustiqueAudioPlayer.h"

using namespace Osp::Media;
using namespace Osp::Graphics;

extern AntiMoustiqueAudioPlayer	*g_pAudioPlayer;

AudioPlayerListener::AudioPlayerListener(void)
{
}

AudioPlayerListener::~AudioPlayerListener(void)
{
}

void AudioPlayerListener::OnPlayerOpened(result r)
{

}


void AudioPlayerListener::OnPlayerEndOfClip(void)
{

	// Reset a button color
	if(g_pAudioPlayer->pPlayer->GetState() == PLAYER_STATE_ENDOFCLIP)
	{
		g_pAudioPlayer->pButton1->SetTextColor(Color(0,44,55,10));
	}


	g_pAudioPlayer->RefreshForm();

}

void AudioPlayerListener::OnPlayerBuffering(int percent)
{
}
void AudioPlayerListener::OnPlayerErrorOccurred(PlayerErrorReason r)
{
}

void AudioPlayerListener::OnPlayerInterrupted( void )
{

}

void AudioPlayerListener::OnPlayerReleased( void )
{

}
