package gameoflife.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.genericgame.GenericGame;
import gameoflife.core.GameManager;
import gameoflife.gameboard.Cell;
import gameoflife.gameboard.GameBoard;
import gameoflife.time.GamePausablePeriodicDelayedTask;

public class Game extends GenericGame {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();

	private GameBoard gameBoard;

	private GameManager gameManager;

	private int autoPlaySpeedPerSecond;

	private GamePausablePeriodicDelayedTask autoPlayTask;

	private List<PauseReason> pauseReasons = new ArrayList<>();

	public Game(GameManager gameManager, GameBoard gameBoard) {
		this.gameManager = gameManager;
		this.gameBoard = gameBoard;
		gameBoard.setGame(this);
		start();
	}

	public void addGameStatusListener(GameStatusListener listener) {
		listener.onListenToGameStatus(this);
		gameStatusListeners.add(listener);
	}

	public void cancel() {
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameCancelled(this));
	}

	public void start() {
		if (begun) {
			throw new IllegalStateException("Game already started!");
		}
		this.begun = true;
		LOGGER.info("Game has started");
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameStarted(this));
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	@Deprecated
	public GameManager getGameManager() {
		return gameManager;
	}

	public void addPauseReason(PauseReason pauseReason) {
		pauseReasons.add(pauseReason);
		pause();
	}

	private boolean pause() {
		if (!paused) {
			LOGGER.info("Pause game");
			paused = true;
			if (autoPlayTask != null) {
				autoPlayTask.cancel();
				autoPlayTask = null;
			}
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

	public void playOneStep() {
		List<Cell> newlyAliveCells = new ArrayList<>();
		List<Cell> newlyDeadCells = new ArrayList<>();

		for (Cell cell : gameBoard.getAllCells()) {
			int numberOfAliveNeighbours = (int) cell.getNeighbours().stream().map(Cell.class::cast)
					.filter(Cell::isAlive).count();

			if (cell.isAlive()) {
				if (numberOfAliveNeighbours < 2) {
					LOGGER.debug(() -> cell + " dies by underpopulation");
					// Any live cell with fewer than two live neighbours dies, as if by
					// underpopulation.
					newlyDeadCells.add(cell);
				} else if (numberOfAliveNeighbours > 3) {
					LOGGER.debug(() -> cell + " dies by overpopulation");
					// Any live cell with more than three live neighbours dies, as if by
					// overpopulation.
					newlyDeadCells.add(cell);
				}
			} else {

				if (numberOfAliveNeighbours == 3) {
					LOGGER.debug(() -> cell + " becomes alive by reproduction");
					// Any dead cell with exactly three live neighbours becomes a live cell, as if
					// by reproduction.
					newlyAliveCells.add(cell);
				}
			}

		}

		newlyAliveCells.forEach(Cell::setAlive);
		newlyDeadCells.forEach(Cell::setDead);

		gameBoard.getAllDeadCellsThatHaveBeenAlive().forEach(Cell::increaseCurrentDeathPeriod);

		if (gameBoard.getAllAliveCells().isEmpty()) {
			lifeHasEnded();
		}
		if (newlyAliveCells.isEmpty() && newlyDeadCells.isEmpty()) {
			lifeIsStill();
		}

	}

	private void lifeIsStill() {
		over = true;

	}

	private void lifeHasEnded() {
		over = true;

	}

	public void setAutoPlaySpeedPerSecond(int autoPlaySpeedPerSecond) {

		if (this.autoPlaySpeedPerSecond != autoPlaySpeedPerSecond) {
			this.autoPlaySpeedPerSecond = autoPlaySpeedPerSecond;

			if (begun) {
				if (!paused) {
					if (autoPlayTask != null) {
						autoPlayTask.cancel();
						autoPlayTask = null;
						autoPlay(autoPlaySpeedPerSecond);
					}
				}
			}
		}
	}

	public void autoPlay(int autoPlaySpeedPerSecond) {
		this.autoPlaySpeedPerSecond = autoPlaySpeedPerSecond;

		if (paused) {
			resume();
		}

		autoPlayTask = new GamePausablePeriodicDelayedTask(this, 1000 / autoPlaySpeedPerSecond) {

			@Override
			public void run() {
				playOneStep();
			}
		};
	}

}
