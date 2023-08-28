package main.game;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.Attacker;
import main.belligerents.Tower;
import main.belligerents.listeners.AttackerListener;
import main.belligerents.listeners.TowerListener;
import main.builders.GameObjectsDataModel;
import main.core.GameManager;
import main.gameboard.GameBoard;
import main.time.PausableTimeManager;
import main.time.TimeManager;

public class Game implements TowerListener, AttackerListener {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameListener> gameListeners = new ArrayList<>();
	private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();

	private ArrayList<Tower> towers = new ArrayList<>();
	private ArrayList<Attacker> attackers = new ArrayList<>();

	private GameBoard gameBoard;

	private boolean lost = false;
	private boolean over = false;
	private boolean won = false;

	private boolean begun = false;

	private boolean paused = false;

	private GameObjectsDataModel gameObjectsDataModel;

	private Player player;
	private GameManager gameManager;

	private Duration gameDuration;

	private int numberOfSecondsSinceGameStart;

	private PausableTimeManager pausableTimeManager;

	public Game(GameManager gameManager, GameBoard gameBoard, GameObjectsDataModel gameObjectsDataModel) {
		this.gameManager = gameManager;
		this.gameBoard = gameBoard;
		this.gameObjectsDataModel = gameObjectsDataModel;
		gameBoard.setGame(this);
		player = new Player(this);
		addGameStatusListener(TimeManager.getInstance());
		pausableTimeManager = new PausableTimeManager();

	}

	public void addGameListener(GameListener listener) {
		listener.onListenToGame(this);
		gameListeners.add(listener);
	}

	public void addGameStatusListener(GameStatusListener listener) {
		listener.onListenToGameStatus(this);
		gameStatusListeners.add(listener);
	}

	public void cancel() {
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameCancelled(this));
	}

	public void setLost() {
		LOGGER.info("Game is lost!");
		lost = true;
		over = true;
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameLost(this));
	}

	public void setWon() {
		LOGGER.info("Game is won!");
		won = true;
		over = true;
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameWon(this));
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

	public void start() {
		if (begun) {
			throw new IllegalStateException("Game already started!");
		}
		this.begun = true;
		numberOfSecondsSinceGameStart = 0;
		LOGGER.info("Game has started. " + this);
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameStarted(this));

		pausableTimeManager.start();
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public GameObjectsDataModel getGameObjectsDataModel() {
		return gameObjectsDataModel;
	}

	@Override
	public void onListenToTower(Tower tower) {
		towers.add(tower);
	}

	@Override
	public void onAttackerEndOfDestructionAndClean(Attacker attacker) {
		// Auto-generated method stub

	}

	@Override
	public void onListenToAttacker(Attacker attacker) {
		attackers.add(attacker);
	}

	@Override
	public void onAttackerMoved(Attacker attacker) {
		// Auto-generated method stub

	}

	@Override
	public void onAttackerBeginningOfDestruction(Attacker attacker) {
		attackers.remove(attacker);
	}

	@Override
	public void onTowerRemoval(Tower tower) {
		towers.remove(tower);
	}

	public List<Attacker> getAttackers() {
		return attackers;
	}

	public Player getPlayer() {
		return player;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	@Override
	public void onAttackerEscape(Attacker attacker) {
		attackers.remove(attacker);
		player.loseOneLife();
		if (player.getRemainingNumberOfLives() <= 0) {
			setLost();
		}
	}

	public Duration getGameDuration() {
		return gameDuration;
	}

	public void pause() {
		if (!paused) {
			paused = true;
			pausableTimeManager.pause();
		} else {
			LOGGER.info("Game is already paused, cannot pause!");
		}
	}

	public void resume() {
		if (paused) {
			paused = false;
			pausableTimeManager.resume();
		} else {
			LOGGER.info("Game is not paused, cannot resume!");
		}
	}

	public PausableTimeManager getTimeManager() {
		return pausableTimeManager;
	}
}
