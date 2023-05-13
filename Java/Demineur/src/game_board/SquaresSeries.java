package game_board;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SquaresSeries {

	private static final Logger LOGGER = LogManager.getLogger(SquaresSeries.class);

	protected ArrayList<Square> squares = new ArrayList<Square>();

	private int index;

	public SquaresSeries(int index) {
		this.index = index;
	}

	public void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	public void addSquare(Square square) {
		squares.add(square);
	}

	public int getIndex() {
		return index;
	}

}
