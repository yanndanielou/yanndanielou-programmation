package gameoflife.game;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.genericgame.GenericGame;
import gameoflife.core.GameManager;
import gameoflife.gameboard.GameBoard;
import gameoflife.time.GameTimeManager;
import main.common.timer.PausableTimeManager;

public class Game extends GenericGame  {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();

	private GameBoard gameBoard;

	private boolean begun = false;

	private boolean paused = false;

	private GameManager gameManager;

	private GameTimeManager gameTimeManager;

	public Game(GameManager gameManager, GameBoard gameBoard) {
		this.gameManager = gameManager;
		this.gameBoard = gameBoard;
		gameBoard.setGame(this);
		gameTimeManager = new GameTimeManager();
	}

	public void addGameStatusListener(GameStatusListener listener) {
		listener.onListenToGameStatus(this);
		gameStatusListeners.add(listener);
	}

	public void cancel() {
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameCancelled(this));
	}

	public void setLost() {
		super.setLost();
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameLost(this));
	}

	public void setWon() {
		super.setWon();
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameWon(this));
	}

	public void start() {
		if (begun) {
			throw new IllegalStateException("Game already started!");
		}
		this.begun = true;
		LOGGER.info("Game has started. " + this);
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameStarted(this));
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}


	public GameManager getGameManager() {
		return gameManager;
	}

	public boolean pause() {
		if (!paused) {
			paused = true;
			gameTimeManager.pause();
			gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGamePaused(this));
			return true;
		} else {
			LOGGER.info("Game is already paused, cannot pause!");
			return false;
		}
	}

	public boolean resume() {
		if (paused) {
			paused = false;
			gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameResumed(this));
			return true;
		} else {
			LOGGER.info("Game is not paused, cannot resume!");
			return false;
		}
	}

	public PausableTimeManager getTimeManager() {
		return gameTimeManager;
	}

}
