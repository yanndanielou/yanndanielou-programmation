package main.time;

import game.genericgame.GenericGame;
import game.genericgame.GenericGameStatusListener;
import main.common.timer.PausablePeriodicDelayedTask;

public abstract class GamePausablePeriodicDelayedTask extends PausablePeriodicDelayedTask
		implements GenericGameStatusListener<GenericGame> {

	protected GamePausablePeriodicDelayedTask(GenericGame game, long delay) {
		super(delay);
	}

	@Override
	public void onGameCancelled(GenericGame game) {
		cancel();
	}

	@Override
	public void onGameLost(GenericGame game) {
		cancel();
	}

	@Override
	public void onGameWon(GenericGame game) {
		cancel();
	}

	@Override
	public void onGamePaused(GenericGame game) {
		pause();
	}

	@Override
	public void onGameResumed(GenericGame game) {
		resume();
	}

}
