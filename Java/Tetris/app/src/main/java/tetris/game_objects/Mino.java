package tetris.game_objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.exceptions.BadLogicException;
import tetris.game.Game;
import tetris.game_objects.tetrominoes_types.Tetromino;
import tetris.gameboard.MatrixCell;

public class Mino {

	private static final Logger LOGGER = LogManager.getLogger(Mino.class);

	
	private Tetromino tetromino;
	protected Game game;
	public MatrixCell locationOnMatrix;

	public Mino(Tetromino tetromino, MatrixCell matrixCell) {
		this.tetromino = tetromino;
		moveTo(matrixCell);
	}

	public Tetromino getTetromino() {
		return tetromino;
	}

	public MatrixCell getLocationOnMatrix() {
		return locationOnMatrix;
	}

	public void moveTo(MatrixCell futureLocationOnMatrix) {
		LOGGER.info(() -> "Move mino " + this + " from " + locationOnMatrix + " to " + futureLocationOnMatrix);
		if (locationOnMatrix != null) {
			locationOnMatrix.setMino(null);
		}
		if (futureLocationOnMatrix.getMino() != null) {
			throw new BadLogicException(
					"There is already mino " + futureLocationOnMatrix.getMino() + " on " + futureLocationOnMatrix + " cannot place " + this);
		}
		futureLocationOnMatrix.setMino(this);
		locationOnMatrix = futureLocationOnMatrix;
	}

}
