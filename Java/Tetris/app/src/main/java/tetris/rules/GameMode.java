package tetris.rules;

import tetris.game.Game;

public abstract class GameMode {
	public abstract DropSpeed getDropSpeedPerLevelNumber(int levelNumber);

	public abstract int getLockDelayInMilliseconds();


}
