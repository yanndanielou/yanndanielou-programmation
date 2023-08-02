package game;

public interface GameStatusListener {

	public void on_listen_to_game_status(Game game);

	public void on_game_cancelled(Game game);

	public void on_game_lost(Game game);
	public void on_game_won(Game game);

}
