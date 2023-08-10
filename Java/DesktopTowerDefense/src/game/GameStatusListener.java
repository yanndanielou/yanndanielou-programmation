package game;

public interface GameStatusListener {

	public void onListenToGameStatus(Game game);

	public void onGameCancelled(Game game);

	public void onGameLost(Game game);
	public void on_game_won(Game game);

}
