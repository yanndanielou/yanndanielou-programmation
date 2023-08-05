package game_board;

import java.util.ArrayList;

public class GameBoardPointsSeries {

	protected ArrayList<GameBoardPoint> game_board_points = new ArrayList<GameBoardPoint>();

	private int index;

	public GameBoardPointsSeries(int index) {
		this.index = index;
	}

	public void setGameBoardPoints(ArrayList<GameBoardPoint> gameBoardPoint) {
		this.game_board_points = gameBoardPoint;
	}

	public ArrayList<GameBoardPoint> getGameBoardPoints() {
		return game_board_points;
	}

	public void addGameBoardPoint(GameBoardPoint gameBoardPoint) {
		game_board_points.add(gameBoardPoint);
	}

	public int getIndex() {
		return index;
	}

}
