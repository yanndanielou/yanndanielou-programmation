package tetris.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.exceptions.BadLogicException;
import game.gameboard.GenericIntegerGameBoardPoint;
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

	private void moveCurrentTetromino(NeighbourGameBoardPointDirection direction) {
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
	}

	public boolean tryAndMoveCurrentTetromino(NeighbourGameBoardPointDirection direction) {
		if (currentMovingTetromino != null && canMoveCurrentTetromino(direction)) {
			moveCurrentTetromino(direction);

			if (!canMoveCurrentTetromino(NeighbourGameBoardPointDirection.SOUTH)) {
				cancelCurrentDropMinoTask();

				int lockDelayInMilliseconds = gameMode.getLockDelayInMilliseconds();
				if (lockDelayInMilliseconds == 0) {
					endCurrentTetromino();
				} else {

					currentOneShotTask = new GamePausableOneShotDelayedTask(this, lockDelayInMilliseconds) {

						@Override
						public void run() {
							endCurrentTetromino();
						}
					};

				}
			}
			return true;
		} else {
			LOGGER.info(() -> "Cannot move tetromino : " + currentMovingTetromino);
			return false;
		}

	}

	private void endCurrentTetromino() {
		LOGGER.info(() -> " endCurrentTetromino");
		lockCurrentTetromino();
		clearFullLines();
		currentMovingTetromino = null;
		tryAndDropNewRandomTetrimino();
	}

	private void lockCurrentTetromino() {
		LOGGER.info(() -> " lockCurrentTetromino");
		currentMovingTetromino.lock();
		currentOneShotTask = null;
	}

	private Set<Integer> getCurrentMovingTetrominoLines() {
		Set<Integer> linesPresentInTetromino = new HashSet<Integer>();
		currentMovingTetromino.getMinos().forEach(e -> {
			linesPresentInTetromino.add(e.getLocationOnMatrix().getYAsInt());
		});
		return linesPresentInTetromino;
	}

	private Set<Integer> getLinesNumberFullAmongLines(Set<Integer> linesNumberToCheckIfFull) {
		Set<Integer> linesNumberFull = new HashSet<Integer>();
		for (Integer lineNumber : linesNumberToCheckIfFull) {
			if (isLineFull(lineNumber)) {
				LOGGER.info("Line: " + lineNumber + " is full");
				linesNumberFull.add(lineNumber);
			}
		}
		return linesNumberFull;

	}

	private void clearLine(int lineNumberToClear) {
		LOGGER.info(() -> " clear line:" + lineNumberToClear);
		gameBoard.getGameBoardPointsByY(lineNumberToClear).stream().map(MatrixCell.class::cast).forEach(matrixCell -> {
			Mino mino = matrixCell.getMino();
			mino.getTetromino().removeMino(mino);
			matrixCell.setVoid();
		});

		// Moves all lines above, below
		for (Integer lineNumber = lineNumberToClear - 1; lineNumber >= 0; lineNumber--) {
			gameBoard.getGameBoardPointsByY(lineNumber).stream().map(MatrixCell.class::cast).map(MatrixCell::getMino)
					.filter(Objects::nonNull).forEach(e -> {
						e.moveTo((MatrixCell) gameBoard.getNeighbourGameBoardPoint(e.getLocationOnMatrix(),
								NeighbourGameBoardPointDirection.SOUTH));
					});
		}

	}

	private int clearFullLines() {
		Set<Integer> linesPresentInTetromino = getCurrentMovingTetrominoLines();
		Set<Integer> linesNumberFull = getLinesNumberFullAmongLines(linesPresentInTetromino);
		List<Integer> linesNumberFullOrderdFromTopToBottom = new ArrayList<Integer>(linesNumberFull);
		Collections.sort(linesNumberFullOrderdFromTopToBottom);

		for (Integer lineNumberToClear : linesNumberFullOrderdFromTopToBottom) {
			clearLine(lineNumberToClear);
		}

		return linesNumberFullOrderdFromTopToBottom.size();
	}

	private boolean isLineFull(int lineNumber) {
		List<GenericIntegerGameBoardPoint> gameBoardPointsByY = gameBoard.getGameBoardPointsByY(lineNumber);
		return gameBoardPointsByY.stream().map(MatrixCell.class::cast).map(MatrixCell::getMino).filter(Objects::isNull)
				.collect(Collectors.toList()).isEmpty();
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

	private void tryAndDropNewRandomTetrimino() {
		LOGGER.info(() -> "tryAndDropNewRandomTetrimino");
		tryAndDropNewTetrimino(TetrominoType.O_SQUARE);
	}

	public void tryAndDropNewTetrimino(TetrominoType tetriminoTypeToDrop) {
		MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard = getUpperLeftCornerOfNewTetriminoCenteredOnGameBoard(
				tetriminoTypeToDrop);
		if (canNewTetriminoBeDropped(upperLeftCornerOfNewTetriminoCenteredOnGameBoard, tetriminoTypeToDrop)) {
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
		} else {
			LOGGER.info("Game Over");
			gameOver();
		}
	}/*
		 * private void applyPattern(Pattern pattern, int x, int y) { for
		 * (IntegerPrecisionPoint aliveCellCoordinate :
		 * pattern.getAliveCellsCoordinates()) { Cell cellByXAndY =
		 * gameBoard.getCellByXAndY(x + aliveCellCoordinate.getXAsInt(), y +
		 * aliveCellCoordinate.getYAsInt()); cellByXAndY.setAlive(); } }
		 */

	private void gameOver() {
		cancelCurrentDropMinoTask();
	}

	public int getCurrentDifficultyLevel() {
		return difficultyLevelManager.getCurrentLevel(this);
	}

	private boolean canNewTetriminoBeDropped(MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard,
			TetrominoType tetrominoType) {
		return true;
	}

	private void setCurrentMovingTetromino(Tetromino currentMovingTetromino) {
		this.currentMovingTetromino = currentMovingTetromino;
	}

	public void dropCompletelyCurrentTetromino() {
		LOGGER.info(() -> " dropCompletelyCurrentTetromino");
		cancelCurrentDropMinoTask();
		while (canMoveCurrentTetromino(NeighbourGameBoardPointDirection.SOUTH)) {
			moveCurrentTetromino(NeighbourGameBoardPointDirection.SOUTH);
		}
		endCurrentTetromino();
	}

}
