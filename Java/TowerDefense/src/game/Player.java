package game;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.TowerDataModel;

public class Player {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Player.class);

	private Game game;
	private int remainingNumberOfLives;
	private int gold;

	private ArrayList<GoldListener> goldlisteners = new ArrayList<>();

	public Player(Game game) {
		this.game = game;
		gold = 0;

	}

	public int getRemainingNumberOfLives() {
		return remainingNumberOfLives;
	}

	public void setRemainingNumberOfLives(int remainingNumberOfLives) {
		this.remainingNumberOfLives = remainingNumberOfLives;
	}

	public boolean canAffordToConstruct(TowerDataModel towerDataModelToCreate) {
		Integer towerCost = towerDataModelToCreate.getLevels().get(0).getCost();
		return gold >= towerCost;
	}

	public int getGold() {
		return gold;
	}

	public void addGold(int addedMoney) {
		gold += addedMoney;
	}

	public void loseOneLife() {
		remainingNumberOfLives--;
	}

}
