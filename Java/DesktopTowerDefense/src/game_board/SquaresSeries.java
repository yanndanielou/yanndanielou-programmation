package game_board;

import java.util.ArrayList;

public abstract class SquaresSeries {

	protected ArrayList<GameBoardPoint> squares = new ArrayList<GameBoardPoint>();

	private int index;

	public SquaresSeries(int index) {
		this.index = index;
	}

	public void setSquares(ArrayList<GameBoardPoint> squares) {
		this.squares = squares;
	}

	public ArrayList<GameBoardPoint> getSquares() {
		return squares;
	}

	public void addSquare(GameBoardPoint square) {
		squares.add(square);
	}

	public int getIndex() {
		return index;
	}

}
