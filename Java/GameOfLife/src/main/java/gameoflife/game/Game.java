package gameoflife.game;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.genericgame.GenericGame;
import gameoflife.core.GameManager;
import gameoflife.gameboard.Cell;
import gameoflife.gameboard.GameBoard;
import gameoflife.time.GameTimeManager;
import main.common.timer.PausableTimeManager;

public class Game extends GenericGame {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();

	private GameBoard gameBoard;

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

	public void playOneStep() {
		List<Cell> newlyAliveCells = new ArrayList<>();
		List<Cell> newlyDeadCells = new ArrayList<>();

		for (Cell cell : gameBoard.getAllCells()) {
			int numberOfAliveNeighbours = (int) cell.getNeighbours().stream().map(Cell.class::cast)
					.filter(Cell::isAlive).count();

			if (cell.isAlive()) {
				if (numberOfAliveNeighbours < 2) {
					LOGGER.info(() -> cell + " dies by underpopulation");
					// Any live cell with fewer than two live neighbours dies, as if by
					// underpopulation.
					newlyDeadCells.add(cell);
				} else if (numberOfAliveNeighbours > 3) {
					LOGGER.info(() -> cell + " dies by overpopulation");
					// Any live cell with more than three live neighbours dies, as if by
					// overpopulation.
					newlyDeadCells.add(cell);
				}
			} else {

				if (numberOfAliveNeighbours == 3) {
					LOGGER.info(cell + " becomes alive by reproduction");
					// Any dead cell with exactly three live neighbours becomes a live cell, as if
					// by reproduction.
					newlyAliveCells.add(cell);
				}
			}

		}

		newlyAliveCells.forEach(e -> e.setAlive());
		newlyDeadCells.forEach(e -> e.setDead());

	}

}
