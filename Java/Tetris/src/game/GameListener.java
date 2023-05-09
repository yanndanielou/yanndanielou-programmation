package game;


public interface GameListener {

	public void on_listen_to_game(Game game);

	public void on_score_changed(Game game, int score);

}
