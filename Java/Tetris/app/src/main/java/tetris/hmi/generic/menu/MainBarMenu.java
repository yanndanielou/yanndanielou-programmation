package tetris.hmi.generic.menu;

import tetris.core.GameManager;
import tetris.game.PauseReason;
import tetris.hmi.generic.views.MainViewI;
import tetris.save_and_load.SaveCurrentGameManager;

public abstract class MainBarMenu {
	
	public abstract void createMenuGame();
	public abstract void createMenuTest();
	public abstract void createMenuSkill();
	public abstract void createMenuOptions();
	public abstract void createMenuHelp();
	public abstract void addToMainFrame(MainViewI mainView);
	
	protected void onPauseMenuItemAction() {
		if (GameManager.hasGameInProgress()) {
			GameManager.getInstance().getGame().togglePauseReason(PauseReason.PAUSE_REQUESTED_IN_HMI);
		}		
	}


	protected void onSaveGameMenuItemAction() {
		if (GameManager.hasGameInProgress()) {
			SaveCurrentGameManager saveCurrentGameManager = new SaveCurrentGameManager();
			saveCurrentGameManager.saveCurrentGame(GameManager.getInstance().getGame());
		}	
		
	}

}
