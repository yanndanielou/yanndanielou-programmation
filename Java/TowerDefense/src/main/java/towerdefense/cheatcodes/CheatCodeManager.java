package towerdefense.cheatcodes;

import java.util.List;

import game.cheatcode.GenericCheatCodeManager;
import towerdefense.belligerents.Attacker;
import towerdefense.belligerents.Belligerent;
import towerdefense.core.GameManager;
import towerdefense.game.Game;

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
