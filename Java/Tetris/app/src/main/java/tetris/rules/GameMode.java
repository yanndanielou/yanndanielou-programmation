package tetris.rules;

public abstract class GameMode {
	public abstract DropSpeed getDropSpeedPerLevelNumber(int levelNumber);

	public abstract int getLockDelayInMilliseconds();
	public int getDelayBeforeLaunchNewTetrominoInMilliseconds() {
		return getLockDelayInMilliseconds();
	}


}
