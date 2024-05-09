package tetris.rules;

public abstract class GameMode {
	public abstract DropSpeed getDropSpeedPerLevelNumber(int levelNumber);

	public abstract int getLockDelayInMilliseconds();
	
	/**
	 *https://tetris.wiki/ARE 
	 * @return
	 */
	public int getEntryDelayInMilliseconds() {
		return getLockDelayInMilliseconds();
	}


}
