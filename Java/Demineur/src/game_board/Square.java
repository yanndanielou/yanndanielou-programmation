package game_board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

	private boolean highlighted_because_click_in_progress_on_neighbour = false;
	
	private boolean highlighting_neighbours_during_click_in_progress = false;

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

	public int getNumber_of_neighbour_mines() {
		return number_of_neighbor_mines;
	}

	public int getNumber_of_neighbour_flags() {
		List<Square> neighbour_flags = neighbour_per_direction.values().stream().filter(square -> square.isFlagged())
				.collect(Collectors.toList());
		return neighbour_flags.size();
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

	public String getShort_description() {
		return "Square :" + "[" + row + "," + column + "]";
	}

	public Collection<Square> getNeighbours() {
		return neighbour_per_direction.values();
	}

	public Game getGame() {
		return game;
	}

	public void highlight_unrevealed_neighbours_because_click_in_progress() {
		setHighlighting_neighbours_during_click_in_progress(true);
		neighbour_per_direction.values().stream().filter(neighbour_square -> !neighbour_square.isDiscovered())
				.forEach(undiscovered_neighbour_square -> undiscovered_neighbour_square.setHighlighted_because_click_in_progress_on_neighbour(true));
	}

	public void unhighlight_unrevealed_neighbours_after_click() {
		setHighlighting_neighbours_during_click_in_progress(false);
		neighbour_per_direction.values().stream()
				.forEach(undiscovered_neighbour_square -> undiscovered_neighbour_square.setHighlighted_because_click_in_progress_on_neighbour(false));
	}

	public boolean isHighlighted_because_click_in_progress_on_neighbour() {
		return highlighted_because_click_in_progress_on_neighbour;
	}

	public void setHighlighted_because_click_in_progress_on_neighbour(
			boolean highlighted_because_click_in_progress_on_neighbour) {
		if (this.highlighted_because_click_in_progress_on_neighbour != highlighted_because_click_in_progress_on_neighbour) {

			this.highlighted_because_click_in_progress_on_neighbour = highlighted_because_click_in_progress_on_neighbour;
			squareListeners.forEach((squareListener) -> squareListener.on_square_status_changed(this));
			LOGGER.info(getShort_description() + " setHighlighted_because_click_in_progress_on_neighbour "
					+ highlighted_because_click_in_progress_on_neighbour);
		}
	}

	public boolean isHighlighting_neighbours_during_click_in_progress() {
		return highlighting_neighbours_during_click_in_progress;
	}

	public void setHighlighting_neighbours_during_click_in_progress(
			boolean highlighting_neighbours_during_click_in_progress) {
		this.highlighting_neighbours_during_click_in_progress = highlighting_neighbours_during_click_in_progress;
	}

}
