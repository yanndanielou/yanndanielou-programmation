package tetris.time;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.timer.PausablePeriodicDelayedTask;
import tetris.game.Game;
import tetris.game.GameStatusListener;

public abstract class GamePausablePeriodicDelayedTask extends PausablePeriodicDelayedTask
		implements GameStatusListener {

	private static final Logger LOGGER = LogManager.getLogger(GamePausablePeriodicDelayedTask.class);
	private Game game;

	protected GamePausablePeriodicDelayedTask(Game game, long delay) {
		super(delay);
		this.game = game;
		game.addGameStatusListener(this);
	}

	@Override
	public void cancel() {
		super.cancel();
		game.removeGameStatusListener(this);
	}


	@Override
	public void onGameCancelled(Game game) {
		cancel();
	}

	@Override
	public void onGameLost(Game game) {
		cancel();
	}

	@Override
	public void onGameWon(Game game) {
		cancel();
	}

	@Override
	public void onGamePaused(Game game) {
		LOGGER.info("Pause " + this + " because " + new Object() {
		}.getClass().getEnclosingMethod().getName());
		pause();
	}

	@Override
	public void onGameResumed(Game game) {
		LOGGER.info("resume " + this + " because game resumed");
		resume();
	}

}
