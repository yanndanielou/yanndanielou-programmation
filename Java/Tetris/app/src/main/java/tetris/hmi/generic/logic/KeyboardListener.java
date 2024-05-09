package tetris.hmi.generic.logic;

import game.gameboard.NeighbourGameBoardPointDirection;
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
		hmiController.leftArrowPressed();
	}

	protected void rightArrowPressed() {
		hmiController.rightArrowPressed();
	}

	protected void downArrowPressed() {
		hmiController.downArrowPressed();
	}

	protected void dKeyPressed() {
		hmiController.dKeyPressed();
	}

	protected void spaceKeyPressed() {
		hmiController.spaceKeyPressed();
	}

}
