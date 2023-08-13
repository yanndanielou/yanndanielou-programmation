package game;

public interface GameStatusListener {

	public void onListenToGameStatus(Game game);

	public void onGameStarted(Game game);
	public void onGameCancelled(Game game);

	public void onGameLost(Game game);
	public void onGameWon(Game game);

}
