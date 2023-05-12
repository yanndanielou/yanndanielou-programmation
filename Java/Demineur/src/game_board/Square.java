package game_board;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.GameDifficultyChosen;
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

	private ArrayList<SquareListener> squareListeners = new ArrayList<>();

	public Square(int line, int column) {
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
		LOGGER.info("Square :" + "["+ row +  "," + column + "]" + " question marked: " + question_marked);
		squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
	}

	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;
		LOGGER.info("Square :" + "["+ row +  "," + column + "]" + " discovered: " + discovered);
		squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
		LOGGER.info("Square :" + "["+ row +  "," + column + "]" + " flagged: " + flagged);
		squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
		LOGGER.info("Square :" + "["+ row +  "," + column + "]" + " has exploded " + exploded);
		squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
	}

	public void setNumber_of_neighbor_mines(int number_of_neighbor_mines) {
		this.number_of_neighbor_mines = number_of_neighbor_mines;
		LOGGER.debug("Square :" + "["+ row +  "," + column + "]" + " has " + number_of_neighbor_mines + " neighbors mines");
		squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
	}

}
