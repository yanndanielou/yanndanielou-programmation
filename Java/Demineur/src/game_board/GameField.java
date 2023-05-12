package game_board;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameField {

	private static final Logger LOGGER = LogManager.getLogger(GameField.class);

	private int width = 0;
	private int height = 0;

	private ArrayList<Square> all_squares_as_ordered_list = new ArrayList<Square>();
	private Square all_squares_as_2D_table[][];

	public GameField(int width, int height) {

		this.width = width;
		this.height = height;

		create_initial_squares();
	}

	private void create_initial_squares() {

		all_squares_as_2D_table = new Square[height][width];

		for (int column = 0; column < all_squares_as_2D_table.length; column++) {
			for (int line = 0; line < all_squares_as_2D_table[column].length; line++) {
				Square square = new Square(line, column);
				all_squares_as_2D_table[column][line] = square;
				getAll_squares_as_ordered_list().add(square);
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

	public Square[][] getAll_squares_as_2D_table() {
		return all_squares_as_2D_table;
	}
}
