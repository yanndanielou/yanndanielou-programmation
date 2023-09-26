package main.cheatcodes;

import java.util.List;

import game.cheatcode.GenericCheatCodeManager;
import main.belligerents.Attacker;
import main.belligerents.Belligerent;
import main.core.GameManager;
import main.game.Game;

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

	public void forbidEnemiesToMove() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			List<Attacker> allAttackers = game.getAttackers();
			allAttackers.forEach(Belligerent::forbidToMove);
		}
	}

	public void moreGold() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			game.getPlayer().addGold(100);
		}
	}

	public void oneMoreLife() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			game.getPlayer().addOneLife();
		}
	}

}
