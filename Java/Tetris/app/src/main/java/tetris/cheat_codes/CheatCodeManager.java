package tetris.cheat_codes;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.cheatcode.GenericCheatCodeManager;

public class CheatCodeManager extends GenericCheatCodeManager{

	private static CheatCodeManager instance;
	private static final Logger LOGGER = LogManager.getLogger(CheatCodeManager.class);

	private CheatCodeManager() {
	}

	public static CheatCodeManager getInstance() {
		if (instance == null) {
			LOGGER.info("Create " + instance.getClass().getName() + " instance");
			instance = new CheatCodeManager();
		}
		return instance;
	}



}
