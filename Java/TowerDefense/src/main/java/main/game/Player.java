package main.game;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.builders.belligerents.TowerDataModel;

public class Player {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Player.class);

	private Game game;
	private int remainingNumberOfLives;
	private int gold;

	private ArrayList<PlayerListener> playerlisteners = new ArrayList<>();

	public Player(Game game) {
		this.game = game;
		gold = 0;
	}

	public void addPlayerListener(PlayerListener playerListener) {
		playerlisteners.add(playerListener);
	}

	public int getRemainingNumberOfLives() {
		return remainingNumberOfLives;
	}

	public void setRemainingNumberOfLives(int remainingNumberOfLives) {
		if (this.remainingNumberOfLives != remainingNumberOfLives) {
			this.remainingNumberOfLives = remainingNumberOfLives;
			playerlisteners
					.forEach((playerlistener) -> playerlistener.onRemaningLivesChange(this, remainingNumberOfLives));
		}
	}

	public boolean canAffordToConstruct(TowerDataModel towerDataModelToCreate) {
		Integer towerCost = towerDataModelToCreate.getLevels().get(0).getCost();
		return gold >= towerCost;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		if (this.gold != gold) {
			this.gold = gold;
			playerlisteners.forEach((playerlistener) -> playerlistener.onGoldChange(this, gold));
		}
	}

	public void addGold(int addedMoney) {
		setGold(gold + addedMoney);
	}

	public void addOneLife() {
		setRemainingNumberOfLives(remainingNumberOfLives + 1);
	}

	public void loseOneLife() {
		setRemainingNumberOfLives(remainingNumberOfLives - 1);
	}

}
