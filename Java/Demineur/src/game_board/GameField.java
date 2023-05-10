package game_board;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameField{

	private static final Logger LOGGER = LogManager.getLogger(GameField.class);

	private int width = 0;
	private int height = 0;


	public GameField(int width, int height) {

		this.width = width;
		this.height = height;

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
