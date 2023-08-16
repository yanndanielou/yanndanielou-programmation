package cheat_codes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

	public boolean try_and_apply_text_cheat_code(String textCheatCode) {
		LOGGER.info("text_cheat_code_entered:" + textCheatCode);

		try {

			Method method = this.getClass().getDeclaredMethod(textCheatCode);
			method.invoke(this);
			return true;
		} catch (SecurityException e) {

		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
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

	public void oneMoreLife() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			game.getPlayer().addOneLife();
		}
	}

}
