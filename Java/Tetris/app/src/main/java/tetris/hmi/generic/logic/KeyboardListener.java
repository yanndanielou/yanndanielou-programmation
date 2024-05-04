package tetris.hmi.generic.logic;

import tetris.game.Game;
import tetris.hmi.javafx.logic.HmiController;

public abstract class KeyboardListener {

	private HmiController hmiController;
	private Game game;

	public KeyboardListener(HmiController hmiController) {
		this.hmiController = hmiController;
		// TODO Auto-generated constructor stub
	}

	public void setGame(Game game) {
		this.game = game;
	}

	protected void leftArrowPressed() {
		game.tryAndShiftCurrentTetrominoLeft();
	}

	protected void rightArrowPressed() {
		game.tryAndShiftCurrentTetrominoRight();
	}

}
