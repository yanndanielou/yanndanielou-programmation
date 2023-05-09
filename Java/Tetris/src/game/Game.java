package game;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game_board.GameField;

public class Game {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private int score;

	private ArrayList<GameListener> game_listeners = new ArrayList<>();
	private ArrayList<GameStatusListener> game_status_listeners = new ArrayList<>();

	private boolean paused = false;
	private GameField gameField;

	public Game(GameField gameField) {
		this.gameField = gameField;
	}

	public void add_game_listener(GameListener listener) {
		listener.on_listen_to_game(this);
		game_listeners.add(listener);
		
	}

	public void add_game_status_listener(GameStatusListener listener) {
		listener.on_listen_to_game_status(this);
		game_status_listeners.add(listener);
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

	public void abort() {
		game_status_listeners.forEach((game_status_listener) -> game_status_listener.on_game_cancelled(this));
	}

}
