package gameoflife.cheatcodes;

import game.cheatcode.GenericCheatCodeManager;

public class CheatCodeManager extends GenericCheatCodeManager {

	private static CheatCodeManager instance;

	private CheatCodeManager() {
	}

	public static CheatCodeManager getInstance() {
		if (instance == null) {
			instance = new CheatCodeManager();
		}
		return instance;
	}



}
