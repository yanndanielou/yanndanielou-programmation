package game_board;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameField {

	private static final Logger LOGGER = LogManager.getLogger(GameField.class);

	private int width = 0;
	private int height = 0;

	private ArrayList<SquaresColumn> squares_columns = new ArrayList<>();
	private ArrayList<SquaresRow> squares_rows = new ArrayList<>();

	private ArrayList<Square> all_squares_as_ordered_list = new ArrayList<Square>();

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
				squares_rows.add(squaresRow);

				Square square = new Square(line, column);

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

	public ArrayList<SquaresRow> getSquaresRows() {
		return squares_rows;
	}

	public Square getSquare(int row, int column) {
		List<Square> square_matching_column_and_row = all_squares_as_ordered_list.stream()
				.filter(item -> !item.isDiscovered()).collect(Collectors.toList());

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
}
