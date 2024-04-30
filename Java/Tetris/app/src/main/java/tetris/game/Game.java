package tetris.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import geometry2d.integergeometry.IntegerPrecisionPoint;
import geometry2d.integergeometry.IntegerPrecisionRectangle;
import tetris.core.GameManager;
import tetris.game_objects.patterns.Pattern;
import tetris.game_objects.tetrominoes_types.Tetromino;
import tetris.game_objects.tetrominoes_types.TetrominoOSquare;
import tetris.game_objects.tetrominoes_types.TetrominoType;
import tetris.gameboard.Matrix;
import tetris.gameboard.MatrixCell;
import tetris.time.GamePausablePeriodicDelayedTask;

public class Game {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();
	private ArrayList<GameListener> gameListeners = new ArrayList<>();

	private Matrix gameBoard;

	private GameManager gameManager;

	private Tetromino currentMovingTetromino;

	private List<Tetromino> passedTetrominos = new ArrayList<>();

	protected boolean paused = false;

	private GamePausablePeriodicDelayedTask nextMoveTask;

	private List<PauseReason> pauseReasons = new ArrayList<>();

	int level = 0;

	public Game(GameManager gameManager, Matrix gameBoard) {
		this.gameManager = gameManager;
		this.gameBoard = gameBoard;
		gameBoard.setGame(this);
	}

	private void start() {
		nextMoveTask = new GamePausablePeriodicDelayedTask(this, 1000) {

			@Override
			public void run() {
				playOneStep();
			}
		};

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
			if (nextMoveTask != null) {
				nextMoveTask.cancel();
				nextMoveTask = null;
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

	public boolean isBegun() {
		return true;
	}

	public MatrixCell getUpperLeftCornerOfNewTetriminoCenteredOnGameBoard(TetrominoType tetrominoTypeToDrop) {
		IntegerPrecisionRectangle tetrominoTypeBoundingBox = tetrominoTypeToDrop.getPattern().getRectangleBoundingBox();
		MatrixCell upperLeftCornerOfNewTetrimino = gameBoard
				.getMatrixCellByXAndY((gameBoard.getTotalWidth() - tetrominoTypeBoundingBox.getWidth()) / 2, 0);
		return upperLeftCornerOfNewTetrimino;

	}

	public void playOneStep() {

	}

	public void tryAndDropNewTetrimino(TetrominoType tetriminoTypeToDrop) {
		MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard = getUpperLeftCornerOfNewTetriminoCenteredOnGameBoard(
				tetriminoTypeToDrop);
		if (canNewTetriminoBeDropped(tetriminoTypeToDrop)) {
			Pattern pattern = tetriminoTypeToDrop.getPattern();

			Tetromino tetromino = new TetrominoOSquare(tetriminoTypeToDrop, this,
					upperLeftCornerOfNewTetriminoCenteredOnGameBoard, pattern);
			for (IntegerPrecisionPoint minoCellCoordinate : pattern.getMinoCellsCoordinates()) {
				MatrixCell matrixCell = gameBoard.getMatrixCellByXAndY(
						upperLeftCornerOfNewTetriminoCenteredOnGameBoard.getXAsInt() + minoCellCoordinate.getXAsInt(),
						upperLeftCornerOfNewTetriminoCenteredOnGameBoard.getYAsInt() + minoCellCoordinate.getYAsInt());
				// matrixCell.set

			}

			setCurrentMovingTetromino(tetromino);
		}
	}/*
		 * private void applyPattern(Pattern pattern, int x, int y) { for
		 * (IntegerPrecisionPoint aliveCellCoordinate :
		 * pattern.getAliveCellsCoordinates()) { Cell cellByXAndY =
		 * gameBoard.getCellByXAndY(x + aliveCellCoordinate.getXAsInt(), y +
		 * aliveCellCoordinate.getYAsInt()); cellByXAndY.setAlive(); } }
		 */

	public boolean canNewTetriminoBeDropped(TetrominoType tetrominoType) {
		return true;
	}

	public Tetromino getCurrentMovingTetromino() {
		return currentMovingTetromino;
	}

	public void setCurrentMovingTetromino(Tetromino currentMovingTetromino) {
		this.currentMovingTetromino = currentMovingTetromino;
	}

}
