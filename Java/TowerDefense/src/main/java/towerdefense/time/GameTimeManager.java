package towerdefense.time;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.timer.PausableTimeManager;
import towerdefense.game.Game;
import towerdefense.game.GameStatusListener;

public class GameTimeManager extends PausableTimeManager implements GameStatusListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameTimeManager.class);

	@Override
	public void onGameCancelled(Game game) {
		stop();
	}

	@Override
	public void onGameLost(Game game) {
		stop();
	}

	@Override
	public void onGameWon(Game game) {
		stop();
	}

	@Override
	public void onGameStarted(Game game) {
		start();
	}

	@Override
	public void onGamePaused(Game game) {
		pause();
	}

	@Override
	public void onGameResumed(Game game) {
		resume();
	}

}
