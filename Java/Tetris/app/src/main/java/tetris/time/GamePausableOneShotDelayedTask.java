package tetris.time;

import common.timer.PausableOneShotDelayedTask;
import tetris.game.Game;
import tetris.game.GameStatusListener;

public abstract class GamePausableOneShotDelayedTask extends PausableOneShotDelayedTask implements GameStatusListener {

	private Game game;

	public GamePausableOneShotDelayedTask(Game game, long delay) {
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
	protected void afterTaskRun() {
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
		pause();
	}

	@Override
	public void onGameResumed(Game game) {
		resume();
	}

}
