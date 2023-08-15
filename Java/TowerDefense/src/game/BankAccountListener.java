package game;

public interface BankAccountListener {

	public void onListenToBankAccount(BankAccount bankAccount);

	public void onBankAccountAmountChanged(BankAccount bankAccount, int newAmount);

}
