package main.game.game;

public interface GenericGameStatusListener<T> {

	public default void onListenToGameStatus(T game) {
	}

	public default void onGameStarted(T game) {
	}

	public default void onGameCancelled(T game) {
	}

	public default void onGameLost(T game) {
	}

	public default void onGameWon(T game) {
	}

	public default void onGamePaused(T game) {
	}

	public default void onGameResumed(T game) {
	}

}
