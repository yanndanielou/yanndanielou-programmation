package tetris.game;

import tetris.gameboard.MatrixCell;

public interface CellListener {
	
	public void onCellAliveStatusChanged(boolean alive, MatrixCell cell);

}
