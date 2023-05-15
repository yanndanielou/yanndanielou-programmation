package game;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game_board.GameField;

public class Game {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameListener> game_listeners = new ArrayList<>();
	private ArrayList<GameStatusListener> game_status_listeners = new ArrayList<>();

	private GameDifficulty gameDifficulty;

	private GameField gameField;

	private boolean lost = false;
	private boolean over = false;
	private boolean won = false;

	private boolean begun = false;

	public Game(GameDifficulty gameDifficultyChosen, GameField gameField) {
		this.gameField = gameField;
		this.gameDifficulty = gameDifficultyChosen;
		gameField.setGame(this);
	}

	public void add_game_listener(GameListener listener) {
		listener.on_listen_to_game(this);
		game_listeners.add(listener);
	}

	public void add_game_status_listener(GameStatusListener listener) {
		listener.on_listen_to_game_status(this);
		game_status_listeners.add(listener);
	}

	public GameField getGameField() {
		return gameField;
	}

	public void cancel() {
		game_status_listeners.forEach((game_status_listener) -> game_status_listener.on_game_cancelled(this));
	}

	public GameDifficulty getDifficulty() {
		return gameDifficulty;
	}

	public void setLost() {
		LOGGER.info("Game is lost!");
		lost = true;
		over = true;
		game_status_listeners.forEach((game_status_listener) -> game_status_listener.on_game_lost(this));
	}

	public void setWon() {
		LOGGER.info("Game is won!");
		won = true;
		over = true;
		game_status_listeners.forEach((game_status_listener) -> game_status_listener.on_game_won(this));
	}

	public boolean isLost() {
		return lost;
	}

	public boolean isOver() {
		return over;
	}

	public boolean isWon() {
		return won;
	}

	public boolean isBegun() {
		return begun;
	}

	public void setBegun() {
		this.begun = true;
		LOGGER.info("Game has begun. " + this);
	}

}
