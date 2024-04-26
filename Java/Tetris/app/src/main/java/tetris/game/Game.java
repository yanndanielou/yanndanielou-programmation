package tetris.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tetris.core.GameManager;
import tetris.gameboard.Matrix;

public class Game {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();
	private ArrayList<GameListener> gameListeners = new ArrayList<>();

	private Matrix gameBoard;

	private GameManager gameManager;

	protected boolean paused = false;

	// private GamePausablePeriodicDelayedTask autoPlayTask;

	private List<PauseReason> pauseReasons = new ArrayList<>();

	public Game(GameManager gameManager, Matrix gameBoard) {
		this.gameManager = gameManager;
		this.gameBoard = gameBoard;
		gameBoard.setGame(this);
	}

	public void addGameStatusListener(GameStatusListener listener) {
		listener.onListenToGameStatus(this);
		gameStatusListeners.add(listener);
	}

	public void addGameListener(GameListener listener) {
		gameListeners.add(listener);
	}

	public void cancel() {
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameCancelled(this));
	}

	public Matrix getGameBoard() {
		return gameBoard;
	}

	public void addPauseReason(PauseReason pauseReason) {
		pauseReasons.add(pauseReason);
		pause();
	}

	private boolean pause() {
		if (!paused) {
			LOGGER.info("Pause game");
			paused = true;
			gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGamePaused(this));
			return true;
		} else {
			LOGGER.info("Game is already paused, cannot pause!");
			return false;
		}
	}

	public void removePauseReason(PauseReason pauseReason) {
		pauseReasons.remove(pauseReason);
		if (pauseReasons.isEmpty()) {
			resume();
		}
	}

	private boolean resume() {
		if (paused) {
			LOGGER.info("Resume game");
			paused = false;
			gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameResumed(this));

			return true;
		} else {
			LOGGER.info("Game is not paused, cannot resume!");
			return false;
		}
	}

	public boolean isBegun() {
		return true;
	}
	

}
