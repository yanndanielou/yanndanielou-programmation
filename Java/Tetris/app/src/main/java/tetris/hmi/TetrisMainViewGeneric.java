package tetris.hmi;

import tetris.game.Game;
import tetris.game.GameStatusListener;

public interface TetrisMainViewGeneric extends GameStatusListener {

	public void registerToGame(Game game);

}
