package game_board;

import java.util.ArrayList;

public abstract class SquaresSeries {

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
