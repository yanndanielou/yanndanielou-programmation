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

	private Integer lastDeathNumberOfSteps;

	private ArrayList<CellListener> gameBoardPointListeners = new ArrayList<>();

	public Cell(Game game, int x, int y) {
		super(x, y);
	}

	public void addGameBoardPointListener(CellListener gameBoardPointListener) {
		gameBoardPointListeners.add(gameBoardPointListener);
	}

	public boolean setAlive() {
		return setAlive(true);
	}

	public boolean setDead() {
		return setAlive(false);
	}

	public boolean toggleState() {
		return setAlive(!isAlive());
	}

	private boolean setAlive(boolean alive) {
		if (this.alive != alive) {
			this.alive = alive;
			gameBoardPointListeners
					.forEach((gameStatusListener) -> gameStatusListener.onCellAliveStatusChanged(alive, this));

			if (isDead()) {
				lastDeathNumberOfSteps = 0;
			} else {
				lastDeathNumberOfSteps = null;
			}

			return true;
		}
		return false;
	}

	public boolean isDead() {
		return !isAlive();
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isDeadAndWasPreviouslyAliveDuringGame() {
		return lastDeathNumberOfSteps != null;
	}

	public void increaseCurrentDeathPeriod() {
		if (lastDeathNumberOfSteps == null) {
			int a = 1;
		}
		lastDeathNumberOfSteps++;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cell [alive=").append(alive).append(", x=").append(x).append(", y=").append(y).append("]");
		return builder.toString();
	}

}
