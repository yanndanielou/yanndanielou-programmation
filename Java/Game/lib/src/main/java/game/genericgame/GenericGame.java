package game.genericgame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GenericGame {
	private static final Logger LOGGER = LogManager.getLogger(GenericGame.class);

//	protected ArrayList<GenericGameListener<GenericGame>> gameListeners = new ArrayList<>();
//	protected ArrayList<GenericGameStatusListener<GenericGame>> gameStatusListeners = new ArrayList<>();

	protected boolean lost = false;
	protected boolean over = false;
	protected boolean won = false;

	protected boolean begun = false;

	protected boolean paused = false;

/*	public void addGameListener(GenericGameListener listener) {
		listener.onListenToGame(this);
		gameListeners.add(listener);
	}

	public void addGameStatusListener(GenericGameStatusListener listener) {
		listener.onListenToGameStatus(this);
		gameStatusListeners.add(listener);
	}

	public void cancel() {
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameCancelled(this));
	}
*/
	public void setLost() {
		LOGGER.info("Game is lost!");
		lost = true;
		over = true;
	//	gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameLost(this));
	}

	public void setWon() {
		LOGGER.info("Game is won!");
		won = true;
		over = true;
		//gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameWon(this));
	}

	public boolean isLost() {
		return lost;
	}

	public boolean isOver() {
		return over;
	}

	public boolean isWon() {
		return won;
	}

	public boolean isBegun() {
		return begun;
	}
/*
	public void start() {
		if (begun) {
			throw new IllegalStateException("Game already started!");
		}
		this.begun = true;
		LOGGER.info("Game has started. " + this);
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameStarted(this));
	}

	public void pause() {
		if (!paused) {
			paused = true;
			gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGamePaused(this));
		} else {
			LOGGER.info("Game is already paused, cannot pause!");
		}
	}

	public void resume() {
		if (paused) {
			paused = false;
			gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameResumed(this));
		} else {
			LOGGER.info("Game is not paused, cannot resume!");
		}
	}*/

}
