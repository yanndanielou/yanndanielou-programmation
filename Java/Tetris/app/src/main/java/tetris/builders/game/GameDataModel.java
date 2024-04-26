package tetris.builders.game;

public class GameDataModel {

	private String name;
	private String comment;
	private int numberOfInitialLives;
	private int numberOfInitialGold;

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public int getNumberOfInitialLives() {
		return numberOfInitialLives;
	}

	public int getNumberOfInitialGold() {
		return numberOfInitialGold;
	}
}
