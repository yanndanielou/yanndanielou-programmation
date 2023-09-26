package main.game;

public interface PlayerListener {

	public void onGoldChange(Player player, int gold);

	public void onRemaningLivesChange(Player player, int remainingLives);

}
