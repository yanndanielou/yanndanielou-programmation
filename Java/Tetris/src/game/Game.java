package game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game_board.GameField;

public class Game {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private int score;

	private boolean paused = false;
	private GameField gameField;

	public Game(GameField gameField) {
		this.gameField = gameField;
	}

	public void add_game_listener(GameListener listener) {
		listener.on_listen_to_game(this);
	}

	public void add_game_status_listener(GameStatusListener listener) {
		listener.on_listen_to_game_status(this);
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public GameField getGameField() {
		return gameField;
	}

}
