package game;

public abstract class GameDifficulty {

	private String name;

	private int width;
	private int height;
	private int mines;

	protected boolean user_defined;

	public int getMines() {
		return mines;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isUser_defined() {
		return user_defined;
	}

	public String getName() {
		return name;
	}

}
