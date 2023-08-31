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
import main.common.timer.PausableTimeManager;
import main.core.GameManager;
import main.game.game.GenericGame;
import main.gameboard.GameBoard;
import main.time.GameTimeManager;

public class Game extends GenericGame implements TowerListener, AttackerListener {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	private ArrayList<GameListener> gameListeners = new ArrayList<>();
	private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();

	private ArrayList<Tower> towers = new ArrayList<>();
	private ArrayList<Attacker> attackers = new ArrayList<>();

	private GameBoard gameBoard;

	private boolean begun = false;

	private boolean paused = false;

	private GameObjectsDataModel gameObjectsDataModel;

	private Player player;
	private GameManager gameManager;

	private Duration gameDuration;

	private GameTimeManager gameTimeManager;

	public Game(GameManager gameManager, GameBoard gameBoard, GameObjectsDataModel gameObjectsDataModel) {
		this.gameManager = gameManager;
		this.gameBoard = gameBoard;
		this.gameObjectsDataModel = gameObjectsDataModel;
		gameBoard.setGame(this);
		player = new Player(this);
		gameTimeManager = new GameTimeManager();
		addGameStatusListener(gameTimeManager);

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
		super.setLost();
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameLost(this));
	}

	public void setWon() {
		super.setWon();
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameWon(this));
	}

	public void start() {
		if (begun) {
			throw new IllegalStateException("Game already started!");
		}
		this.begun = true;
		LOGGER.info("Game has started. " + this);
		gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameStarted(this));
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

	public boolean pause() {
		if (!paused) {
			paused = true;
			gameTimeManager.pause();
			gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGamePaused(this));
			return true;
		} else {
			LOGGER.info("Game is already paused, cannot pause!");
			return false;
		}
	}

	public boolean resume() {
		if (paused) {
			paused = false;
			gameStatusListeners.forEach((gameStatusListener) -> gameStatusListener.onGameResumed(this));
			return true;
		} else {
			LOGGER.info("Game is not paused, cannot resume!");
			return false;
		}
	}

	public PausableTimeManager getTimeManager() {
		return gameTimeManager;
	}

}
