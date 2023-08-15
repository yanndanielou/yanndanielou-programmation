package game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Player.class);

	private Game game;
	private BankAccount bankAccount;
	private int remainingNumberOfLives;

	public Player(Game game) {
		this.game = game;
		bankAccount = new BankAccount(this);
	}

	public int getRemainingNumberOfLives() {
		return remainingNumberOfLives;
	}

	public void setRemainingNumberOfLives(int remainingNumberOfLives) {
		this.remainingNumberOfLives = remainingNumberOfLives;
	}
}
