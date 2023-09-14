package main.time;

import main.common.timer.PausableOneShotDelayedTask;
import main.game.Game;
import main.game.game.GenericGame;
import main.game.game.GenericGameStatusListener;

public abstract class GamePausableOneShotDelayedTask extends PausableOneShotDelayedTask
		implements GenericGameStatusListener<Game> {

	public GamePausableOneShotDelayedTask(Game game, long delay) {
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
