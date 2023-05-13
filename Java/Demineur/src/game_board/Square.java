package game_board;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;
import game.SquareListener;

public class Square {

	private static final Logger LOGGER = LogManager.getLogger(Square.class);

	private boolean contains_mine;
	private boolean discovered = false;
	private boolean flagged = false;
	private boolean question_marked = false;
	private boolean exploded = false;
	private int number_of_neighbor_mines;

	private int row;
	private int column;

	private Game game;

	private ArrayList<SquareListener> squareListeners = new ArrayList<>();

	private HashMap<NeighbourSquareDirection, Square> neighbour_per_direction = new HashMap<>();

	public Square(Game game, int line, int column) {
		this.game = game;
		this.row = line;
		this.column = column;
	}

	public void addSquareListener(SquareListener squareListener) {
		squareListeners.add(squareListener);
		squareListener.on_listen_to_square(this);
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

	public int getNumber_of_neighbor_mines() {
		return number_of_neighbor_mines;
	}

	public void setContains_mine(boolean contains_mine) {
		this.contains_mine = contains_mine;
	}

	public boolean isQuestion_marked() {
		return question_marked;
	}

	public void setQuestion_marked(boolean question_marked) {
		this.question_marked = question_marked;
		LOGGER.info("Square :" + "[" + row + "," + column + "]" + " question marked: " + question_marked);
		squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
	}

	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;
		LOGGER.info("Square :" + "[" + row + "," + column + "]" + " discovered: " + discovered);
		squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
		LOGGER.info("Square :" + "[" + row + "," + column + "]" + " flagged: " + flagged);
		squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
		LOGGER.info("Square :" + "[" + row + "," + column + "]" + " has exploded " + exploded);
		squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setNeighbour(NeighbourSquareDirection direction, Square neighbour) {
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

}
