/*
 * AntiMoustiqueMainForm.h
 *
 *  Created on: 15 juin 2010
 *      Author: Lucas
 */

#ifndef ANTIMOUSTIQUEMAINFORM_H_
#define ANTIMOUSTIQUEMAINFORM_H_

#include <FBase.h>
#include <FGraphics.h>
#include <FMedia.h>
#include <FApp.h>
#include <FUi.h>
#include <FContent.h>
#include <FIo.h>

#include "AntiMoustiqueRessources.h"

using namespace Osp::Ui::Controls;

class AntiMoustiqueMainForm :
	public Osp::Ui::Controls::Form,
	public Osp::Ui::IActionEventListener

{
public:
	AntiMoustiqueMainForm(void);
	~AntiMoustiqueMainForm(void);

	static const int ID_BUTTON_OK = 456;
	static const int AC_BUTTON_AUDIO = ID_BUTTON_OK + 1;

private:
	Osp::Ui::Controls::List				*pList;
	Osp::Ui::Controls::EditField   		*pEditField;
	Osp::Ui::Controls::Button			*pBtnAudioPlayer;
	Osp::Ui::Controls::Label			*pLabel;
	Osp::Graphics::Bitmap				*pListBmp;
	Osp::Base::Collection::ArrayList	arItems;

public:
    bool ConstructMediaPlayerMainForm();
	bool InitializeMediaPlayerMainForm( Osp::Ui::Controls::Frame *pFrame );
	void RefreshMediaPlayerMainForm();

private:
	bool CreateMainList();
	bool GetSelectedItemInfo( int nSelIndex );
	void OnActionPerformed(const Osp::Ui::Control& source, int actionId);
	Osp::Io::File* pFile;
	Osp::Io::Directory *pDirectory;
	Osp::Io::DirEnumerator *pDirEnum;
	Osp::Io::DirEntry *pDirEntry;

	Popup * __pPopup;
	Button * __pButtonOk;
	Label * __pLabel;
private:
	void StartAudioPlayer();

};

#endif /* ANTIMOUSTIQUEMAINFORM_H_ */
