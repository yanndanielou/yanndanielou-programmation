package game_board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;

public class GameField {

	private static final Logger LOGGER = LogManager.getLogger(GameField.class);

	private int width = 0;
	private int height = 0;

	private ArrayList<SquaresColumn> squares_columns = new ArrayList<>();

	// private ArrayList<SquaresRow> squares_rows = new ArrayList<>();

	private ArrayList<Square> all_squares_as_ordered_list = new ArrayList<Square>();

	private Game game;

	public GameField(int width, int height) {

		this.width = width;
		this.height = height;

		create_initial_squares();
	}

	private void create_initial_squares() {

		for (int column = 0; column < width; column++) {
			SquaresColumn squaresColumn = new SquaresColumn(column);
			squares_columns.add(squaresColumn);
			for (int line = 0; line < height; line++) {
				SquaresRow squaresRow = new SquaresRow(line);

				// FIXME: squares_rows.add(squaresRow);

				Square square = new Square(game, line, column);

				squaresColumn.addSquare(square);
				squaresRow.addSquare(square);

				all_squares_as_ordered_list.add(square);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ArrayList<Square> getAll_squares_as_ordered_list() {
		return all_squares_as_ordered_list;
	}

	public ArrayList<SquaresColumn> getSquaresColumns() {
		return squares_columns;
	}

	public Square getSquare(int row, int column) {
		List<Square> square_matching_column_and_row = all_squares_as_ordered_list.stream()
				.filter(item -> item.getRow() == row && item.getColumn() == column).collect(Collectors.toList());

		if (square_matching_column_and_row.size() > 1) {
			LOGGER.fatal("Found more than one (" + square_matching_column_and_row.size() + " squares with row:" + row
					+ " and column:" + column + ". FOund: " + square_matching_column_and_row);
			return null;
		}
		if (square_matching_column_and_row.isEmpty()) {
			LOGGER.error("Could not find square with row:" + row + " and column:" + column);
			return null;
		}

		return square_matching_column_and_row.get(0);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Square getNeighbourSquare(Square reference_square, NeighbourSquareDirection direction) {
		Square neighbour = null;

		int reference_square_column = reference_square.getColumn();
		int reference_square_row = reference_square.getRow();

		boolean is_reference_square_first_of_column = reference_square_column == 0;
		boolean is_reference_square_last_of_column = reference_square_column == height - 1;

		boolean is_reference_square_first_of_row = reference_square_row == 0;
		boolean is_reference_square_last_of_row = reference_square_row == width - 1;

		// left square
		switch (direction) {
		case NORTH:
			if (!is_reference_square_first_of_column) {
				neighbour = getSquare(reference_square_row, reference_square_column - 1);
			}
			break;
		case NORTH_EAST:
			if (!is_reference_square_last_of_row && !is_reference_square_first_of_column) {
				neighbour = getSquare(reference_square_row + 1, reference_square_column - 1);
			}
			break;
		case EAST:
			if (!is_reference_square_last_of_row) {
				neighbour = getSquare(reference_square_row + 1, reference_square_column);
			}
			break;
		case SOUTH_EAST:
			if (!is_reference_square_last_of_row && !is_reference_square_last_of_column) {
				neighbour = getSquare(reference_square_row + 1, reference_square_column + 1);
			}
			break;
		case SOUTH:
			if (!is_reference_square_last_of_column) {
				neighbour = getSquare(reference_square_row, reference_square_column + 1);
			}
			break;
		case SOUTH_WEST:
			if (!is_reference_square_first_of_row && !is_reference_square_last_of_column) {
				neighbour = getSquare(reference_square_row - 1, reference_square_column + 1);
			}
			break;
		case WEST:
			if (!is_reference_square_first_of_row) {
				neighbour = getSquare(reference_square_row - 1, reference_square_column);
			}
			break;
		case NORTH_WEST:
			if (!is_reference_square_first_of_row && !is_reference_square_first_of_column) {
				neighbour = getSquare(reference_square_row - 1, reference_square_column - 1);
			}
			break;
		}
		return neighbour;
	}
}
