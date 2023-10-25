package game;

public interface GameStatusListener {

	public void onGamePaused(Game game);

	public void onGameResumed(Game game);

	public void onListenToGameStatus(Game game);

	public void onGameCancelled(Game game);

	public void onGameOver(Game game);

}
