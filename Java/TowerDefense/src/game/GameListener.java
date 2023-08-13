package game;


public interface GameListener {

	public void onListenToGame(Game game);

	public void onScoreChanged(Game game, int score);

}
