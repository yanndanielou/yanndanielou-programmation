package gameoflife.time;

import common.timer.PausablePeriodicDelayedTask;
import game.genericgame.GenericGame;
import game.genericgame.GenericGameStatusListener;
import gameoflife.game.Game;

public abstract class GamePausablePeriodicDelayedTask extends PausablePeriodicDelayedTask
		implements GenericGameStatusListener<Game> {

	protected GamePausablePeriodicDelayedTask(Game game, long delay) {
		super(delay);
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
		pause();
	}

	@Override
	public void onGameResumed(Game game) {
		resume();
	}

}
