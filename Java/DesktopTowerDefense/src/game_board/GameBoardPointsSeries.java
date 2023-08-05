package game_board;

import java.util.ArrayList;

public class GameBoardPointsSeries {

	protected ArrayList<GameBoardPoint> game_board_points = new ArrayList<GameBoardPoint>();

	private int index;

	public GameBoardPointsSeries(int index) {
		this.index = index;
	}

	public void setSquares(ArrayList<GameBoardPoint> squares) {
		this.game_board_points = squares;
	}

	public ArrayList<GameBoardPoint> getGameBoardPoints() {
		return game_board_points;
	}

	public void addSquare(GameBoardPoint square) {
		game_board_points.add(square);
	}

	public int getIndex() {
		return index;
	}

}
