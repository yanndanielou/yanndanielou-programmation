package cheatcodes;

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

	public boolean tryAndApplyTextCheatCode(String textCheatCode) {
		LOGGER.info("textCheatCodeEntered:" + textCheatCode);

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

	public void forbidEnemiesToMove() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			List<Attacker> allAttackers = game.getAttackers();
			allAttackers.forEach(attacker -> attacker.forbidToMove());
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
