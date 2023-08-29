package main.game;

public interface GameStatusListener {

	public default void onListenToGameStatus(Game game) {
	}

	public default void onGameStarted(Game game) {
	}

	public default void onGameCancelled(Game game) {
	}

	public default void onGameLost(Game game) {
	}

	public default void onGameWon(Game game) {
	}

	public default void onGamePaused(Game game) {
	}

	public default void onGameResumed(Game game) {
	}

}
