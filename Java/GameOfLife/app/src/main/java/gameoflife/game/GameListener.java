package gameoflife.game;

public interface GameListener {
	public default void onAutoPlaySpeedPerSecondChanged(Game game, int autoPlaySpeedPerSecond) {
	}

}
