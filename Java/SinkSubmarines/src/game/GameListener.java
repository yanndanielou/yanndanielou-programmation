package game;

public interface GameListener {

	public void on_game_paused(Game game);

	public void on_number_of_remaining_lives_changed(Game game);

}
