package tetris.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.exceptions.BadLogicException;
import game.gameboard.NeighbourGameBoardPointDirection;
import geometry2d.integergeometry.IntegerPrecisionRectangle;
import tetris.core.DifficultyLevelManager;
import tetris.game_objects.Mino;
import tetris.game_objects.tetrominoes_types.Tetromino;
import tetris.game_objects.tetrominoes_types.TetrominoOSquare;
import tetris.game_objects.tetrominoes_types.TetrominoType;
import tetris.gameboard.Matrix;
import tetris.gameboard.MatrixCell;
import tetris.rules.GameMode;
import tetris.rules.MarathonMode;
import tetris.time.GamePausableOneShotDelayedTask;
import tetris.time.GamePausablePeriodicDelayedTask;

public class Game {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();
	private ArrayList<GameListener> gameListeners = new ArrayList<>();

	private Matrix gameBoard;

	private Tetromino currentMovingTetromino;

	private List<Tetromino> passedTetrominos = new ArrayList<>();

	protected boolean paused = false;

	private GamePausablePeriodicDelayedTask currentDropMinoPeriodicTask;
	private GamePausableOneShotDelayedTask currentOneShotTask;

	private List<PauseReason> pauseReasons = new ArrayList<>();

	private int numberOfLinesCleared = 0;

	private GameMode gameMode;
	private DifficultyLevelManager difficultyLevelManager;

	int level = 0;

	public Game(Matrix gameBoard) {
		this.gameBoard = gameBoard;
		gameBoard.setGame(this);
		gameMode = new MarathonMode();
		difficultyLevelManager = new DifficultyLevelManager(this);
		tryAndDropNewRandomTetrimino();
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
		LOGGER.info(() -> "addPauseReason " + pauseReason);
		pauseReasons.add(pauseReason);
		pause();
	}

	public void togglePauseReason(PauseReason pauseReason) {
		if (pauseReasons.indexOf(pauseReason) == -1) {
			addPauseReason(pauseReason);
		} else {
			removePauseReason(pauseReason);
		}
	}

	private boolean pause() {
		if (!paused) {
			LOGGER.info("Pause game");
			paused = true;
			if (currentDropMinoPeriodicTask != null) {
				currentDropMinoPeriodicTask.pause();
			}
			gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGamePaused(this));
			return true;
		} else {
			LOGGER.info("Game is already paused, cannot pause!");
			return false;
		}
	}

	public void removePauseReason(PauseReason pauseReason) {
		LOGGER.info(() -> "removePauseReason " + pauseReason);
		pauseReasons.remove(pauseReason);
		if (pauseReasons.isEmpty()) {
			resume();
		}
	}

	private boolean resume() {
		if (paused) {
			LOGGER.info("Resume game");
			paused = false;

			if (currentDropMinoPeriodicTask != null) {
				currentDropMinoPeriodicTask.resume();
			}
			if (currentOneShotTask != null) {
				currentOneShotTask.resume();
			}

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

	public void tryAndMoveCurrentTetromino(NeighbourGameBoardPointDirection direction) {
		if (currentMovingTetromino != null && canMoveCurrentTetromino(direction)) {
			LOGGER.info(() -> "Move tetromino : " + currentMovingTetromino + " " + direction);
			List<Mino> minosSortedDependingOnMovementDirection = null;

			switch (direction) {
			case EAST:
				minosSortedDependingOnMovementDirection = currentMovingTetromino.getMinosSortedFromRightToLeft();
				break;
			case SOUTH:
				minosSortedDependingOnMovementDirection = currentMovingTetromino.getMinosSortedFromBottomToTop();
				break;
			case WEST:
				minosSortedDependingOnMovementDirection = currentMovingTetromino.getMinosSortedFromLeftToRight();
				break;
			default:
				throw new BadLogicException("Unsupported direction:" + direction);
			}

			for (Mino mino : minosSortedDependingOnMovementDirection) {
				MatrixCell currentLocationOnMatrix = mino.getLocationOnMatrix();
				MatrixCell futureLocationOnMatrix = (MatrixCell) gameBoard
						.getNeighbourGameBoardPoint(currentLocationOnMatrix, direction);

				mino.moveTo(futureLocationOnMatrix);
			}

			if (!canMoveCurrentTetromino(NeighbourGameBoardPointDirection.SOUTH)) {
				cancelCurrentDropMinoTask();

				int lockDelayInMilliseconds = gameMode.getLockDelayInMilliseconds();
				if (lockDelayInMilliseconds == 0) {
					lockCurrentTetromino();
				} else {

					currentOneShotTask = new GamePausableOneShotDelayedTask(this, lockDelayInMilliseconds) {

						@Override
						public void run() {
							lockCurrentTetromino();
						}
					};

				}
			}
		} else {
			LOGGER.info(() -> "Cannot move tetromino : " + currentMovingTetromino);
		}

	}

	private void lockCurrentTetromino() {
		currentMovingTetromino.lock();
		currentOneShotTask = null;

		clearFullLines();

	}

	private int clearFullLines() {
		int fullLinesClearedByCurrentTetromino = 0;

		Set<Integer> linesPresentInTetromino = new HashSet<Integer>();
		currentMovingTetromino.getMinos().forEach(e -> {
			linesPresentInTetromino.add(e.getLocationOnMatrix().getYAsInt());
		});

		currentMovingTetromino = null;
		return fullLinesClearedByCurrentTetromino;
	}

	private void cancelCurrentDropMinoTask() {
		LOGGER.info(() -> "cancelDropCurrentMinoTask");
		currentDropMinoPeriodicTask.cancel();
		currentDropMinoPeriodicTask = null;
	}

	private boolean canMoveCurrentTetromino(NeighbourGameBoardPointDirection direction) {
		if (currentMovingTetromino == null) {
			return false;
		}
		if (currentMovingTetromino.isLocked()) {
			return false;
		}
		for (Mino mino : currentMovingTetromino.getMinos()) {
			MatrixCell currentLocationOnMatrix = mino.getLocationOnMatrix();

			if (gameBoard.hasNeighbourGameBoardPoint(currentLocationOnMatrix, direction)) {

				MatrixCell futureLocationOnMatrix = (MatrixCell) gameBoard
						.getNeighbourGameBoardPoint(currentLocationOnMatrix, direction);

				if (futureLocationOnMatrix.getMino() != null
						&& futureLocationOnMatrix.getMino().getTetromino() != currentMovingTetromino) {
					LOGGER.info(() -> "Cannot move tetromino : " + currentMovingTetromino + " " + direction
							+ " because blocked by " + futureLocationOnMatrix.getMino().getTetromino());
					return false;
				}

			} else {
				return false;
			}

		}
		return true;

	}

	public void tryAndDropNewRandomTetrimino() {
		tryAndDropNewTetrimino(TetrominoType.O_SQUARE);
	}

	public void tryAndDropNewTetrimino(TetrominoType tetriminoTypeToDrop) {
		MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard = getUpperLeftCornerOfNewTetriminoCenteredOnGameBoard(
				tetriminoTypeToDrop);
		if (canNewTetriminoBeDropped(tetriminoTypeToDrop)) {
			Tetromino tetromino = new TetrominoOSquare(tetriminoTypeToDrop, this,
					upperLeftCornerOfNewTetriminoCenteredOnGameBoard);

			setCurrentMovingTetromino(tetromino);

			int numberOfMillisecondsBetweenEachStepDown = gameMode
					.getDropSpeedPerLevelNumber(getCurrentDifficultyLevel())
					.getNumberOfMillisecondsBetweenEachStepDown();
			LOGGER.info(() -> "numberOfMillisecondsBetweenEachStepDown: " + numberOfMillisecondsBetweenEachStepDown);
			currentDropMinoPeriodicTask = new GamePausablePeriodicDelayedTask(this,
					numberOfMillisecondsBetweenEachStepDown) {

				@Override
				public void run() {
					tryAndMoveCurrentTetromino(NeighbourGameBoardPointDirection.SOUTH);
				}
			};
		}
	}/*
		 * private void applyPattern(Pattern pattern, int x, int y) { for
		 * (IntegerPrecisionPoint aliveCellCoordinate :
		 * pattern.getAliveCellsCoordinates()) { Cell cellByXAndY =
		 * gameBoard.getCellByXAndY(x + aliveCellCoordinate.getXAsInt(), y +
		 * aliveCellCoordinate.getYAsInt()); cellByXAndY.setAlive(); } }
		 */

	public int getCurrentDifficultyLevel() {
		return difficultyLevelManager.getCurrentLevel(this);
	}

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
