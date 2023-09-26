package towerdefense.time;

import game.genericgame.GenericGameStatusListener;
import main.common.timer.PausableOneShotDelayedTask;
import towerdefense.game.Game;

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
