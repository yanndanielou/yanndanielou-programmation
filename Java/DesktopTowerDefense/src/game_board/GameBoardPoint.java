package game_board;

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

	private boolean contains_mine;
	private boolean discovered = false;
	private boolean flagged = false;
	private boolean question_marked = false;
	private boolean exploded = false;
	private int number_of_neighbor_mines;

	private int row;
	private int column;

	private Game game;

	private ArrayList<GameBoardPointListener> squareListeners = new ArrayList<>();

	private HashMap<NeighbourSquareDirection, GameBoardPoint> neighbour_per_direction = new HashMap<>();

	public GameBoardPoint(Game game, int line, int column) {
		this.game = game;
		this.row = line;
		this.column = column;
	}

	public void addSquareListener(GameBoardPointListener squareListener) {
		squareListeners.add(squareListener);
	}

	public boolean isContains_mine() {
		return contains_mine;
	}

	public boolean isDiscovered() {
		return discovered;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public boolean isExploded() {
		return exploded;
	}

	public int getNumber_of_neighbour_mines() {
		return number_of_neighbor_mines;
	}

	public int getNumber_of_neighbour_flags() {
		List<GameBoardPoint> neighbour_flags = neighbour_per_direction.values().stream()
				.filter(square -> square.isFlagged()).collect(Collectors.toList());
		return neighbour_flags.size();
	}

	public void setContains_mine(boolean contains_mine) {
		this.contains_mine = contains_mine;
	}

	public boolean isQuestion_marked() {
		return question_marked;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setNeighbour(NeighbourSquareDirection direction, GameBoardPoint neighbour) {
		neighbour_per_direction.put(direction, neighbour);
	}

	public void compute_Number_of_neighbor_mines() {
		neighbour_per_direction.values().forEach(square -> {
			if (square != null && square.isContains_mine()) {
				number_of_neighbor_mines++;
			}
		});
		LOGGER.debug(
				"Square :" + "[" + row + "," + column + "]" + " has " + number_of_neighbor_mines + " neighbors mines");
	}

	public String getShort_description() {
		return "Square :" + "[" + row + "," + column + "]";
	}

	public Collection<GameBoardPoint> getNeighbours() {
		return neighbour_per_direction.values();
	}

	public Game getGame() {
		return game;
	}

}
