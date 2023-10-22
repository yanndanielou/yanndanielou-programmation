package gameoflife.game;

import gameoflife.gameboard.Cell;

public interface CellListener {
	
	public void onCellAliveStatusChanged(boolean alive, Cell cell);

}
