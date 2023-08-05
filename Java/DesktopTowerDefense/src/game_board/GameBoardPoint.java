package game_board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;
import game.GameBoardPointListener;

public class GameBoardPoint {

	private static final Logger LOGGER = LogManager.getLogger(GameBoardPoint.class);

	private Point location;
	private int row;
	private int column;

	private Game game;

	private ArrayList<GameBoardPointListener> game_board_point_Listeners = new ArrayList<>();

	private HashMap<NeighbourGameBoardPointDirection, GameBoardPoint> neighbour_per_direction = new HashMap<>();

	public GameBoardPoint(Game game, int line, int column) {
		this.game = game;
		this.row = line;
		this.column = column;
	}

	public void addGameBoardPointListener(GameBoardPointListener gameBoardPointListener) {
		game_board_point_Listeners.add(gameBoardPointListener);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setNeighbour(NeighbourGameBoardPointDirection direction, GameBoardPoint neighbour) {
		neighbour_per_direction.put(direction, neighbour);
	}

	public String getShort_description() {
		return "GameBoardPoint :" + "[" + row + "," + column + "]";
	}

	public Collection<GameBoardPoint> getNeighbours() {
		return neighbour_per_direction.values();
	}

	public Game getGame() {
		return game;
	}

}
