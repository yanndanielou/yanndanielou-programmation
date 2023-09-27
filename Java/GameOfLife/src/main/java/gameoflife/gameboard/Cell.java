package gameoflife.gameboard;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.GenericIntegerGameBoardPoint;
import gameoflife.game.CellListener;
import gameoflife.game.Game;

public class Cell extends GenericIntegerGameBoardPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353270231660749618L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Cell.class);

	private boolean alive = false;

	private ArrayList<CellListener> gameBoardPointListeners = new ArrayList<>();

	public Cell(Game game, int x, int y) {
		super(x, y);
	}

	public void addGameBoardPointListener(CellListener gameBoardPointListener) {
		gameBoardPointListeners.add(gameBoardPointListener);
	}

	public void setAlive() {
		setAlive(true);
	}

	public void setDead() {
		setAlive(false);
	}

	private void setAlive(boolean alive) {
		if (this.alive != alive) {
			this.alive = alive;
			gameBoardPointListeners
					.forEach((gameStatusListener) -> gameStatusListener.onCellAliveStatusChanged(alive, this));

		}
	}

	public boolean isAlive() {
		return alive;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cell [alive=").append(alive).append(", x=").append(x).append(", y=").append(y).append("]");
		return builder.toString();
	}

	
}
