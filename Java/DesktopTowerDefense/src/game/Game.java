package game;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.Tower;
import belligerents.listeners.AttackerListener;
import belligerents.listeners.TowerListener;
import builders.GameObjectsDataModel;
import game_board.GameBoard;

public class Game implements TowerListener, AttackerListener {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameListener> game_listeners = new ArrayList<>();
	private ArrayList<GameStatusListener> game_status_listeners = new ArrayList<>();

	private ArrayList<Tower> towers = new ArrayList<>();

	private GameBoard gameBoard;

	private boolean lost = false;
	private boolean over = false;
	private boolean won = false;

	private boolean begun = false;

	private GameObjectsDataModel game_objects_data_model;

	public Game(GameBoard gameBoard, GameObjectsDataModel game_objects_data_model) {
		this.gameBoard = gameBoard;
		this.game_objects_data_model = game_objects_data_model;
		gameBoard.setGame(this);
	}

	public void add_game_listener(GameListener listener) {
		listener.on_listen_to_game(this);
		game_listeners.add(listener);
	}

	public void add_game_status_listener(GameStatusListener listener) {
		listener.on_listen_to_game_status(this);
		game_status_listeners.add(listener);
	}

	public void cancel() {
		game_status_listeners.forEach((game_status_listener) -> game_status_listener.on_game_cancelled(this));
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

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public GameObjectsDataModel getGame_objects_data_model() {
		return game_objects_data_model;
	}

	@Override
	public void on_listen_to_tower(Tower tower) {
		towers.add(tower);
	}

	@Override
	public void on_attacker_end_of_destruction_and_clean(Attacker attacker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_listen_to_attacker(Attacker attacker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_attacker_moved(Attacker attacker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_attacker_beginning_of_destruction(Attacker attacker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_tower_removal(Tower tower) {
		// TODO Auto-generated method stub
		
	}

}
