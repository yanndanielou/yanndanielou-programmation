package game;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankAccount {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(BankAccount.class);

	private ArrayList<BankAccountListener> bankAccountlisteners = new ArrayList<>();
	private Player player;

	private int bankAccountMoney;

	public BankAccount(Player player) {
		this.player = player;
		bankAccountMoney = 0;
	}

	public int getBankAccountMoney() {
		return bankAccountMoney;
	}
}
