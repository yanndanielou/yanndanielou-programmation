package tetris.game;

import tetris.game_objects.Mino;
import tetris.gameboard.MatrixCell;

public interface CellListener {
	void onCellContentChanged(MatrixCell matrixCell, Mino mino);

}
