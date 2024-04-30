package tetris.game_objects;

import tetris.game.Game;
import tetris.game_objects.tetrominoes_types.Tetromino;
import tetris.gameboard.MatrixCell;

public class Mino {

	private Tetromino tetromino;
	protected Game game;
	protected MatrixCell locationOnMatrix;

	public Mino(MatrixCell matrixCell) {
		this.locationOnMatrix = matrixCell;
		matrixCell.setMino(this);
	}

	public Tetromino getTetromino() {
		return tetromino;
	}

	public void setTetromino(Tetromino tetromino) {
		this.tetromino = tetromino;
	}

}
