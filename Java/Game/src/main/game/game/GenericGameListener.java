package main.game.game;

public interface GenericGameListener<T> {

	public void onListenToGame(T game);

	public void onScoreChanged(T game, int score);

}
