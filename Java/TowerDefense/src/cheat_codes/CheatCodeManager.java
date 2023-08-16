package cheat_codes;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import core.GameManager;
import game.Game;

public class CheatCodeManager {

	private static CheatCodeManager instance;
	private static final Logger LOGGER = LogManager.getLogger(CheatCodeManager.class);

	private CheatCodeManager() {
	}

	public static CheatCodeManager getInstance() {
		if (instance == null) {
			instance = new CheatCodeManager();
		}
		return instance;
	}

	public boolean try_and_apply_text_cheat_code(String text_cheat_code) {
		LOGGER.info("text_cheat_code_entered:" + text_cheat_code);
		return false;
	}

	public void forbid_enemies_to_move() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			List<Attacker> all_attackers = game.getAttackers();
			all_attackers.forEach((attacker) -> attacker.forbid_to_move());
		}
	}

	public void moreGold() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			game.getPlayer().addGold(100);
		}
	}

}
