package tetris.hmi.javafx.logic;

import tetris.game.Game;
import tetris.hmi.javafx.views.MainViewPane;

public class HmiController {

	private MainViewPane mainViewPane;
	private Game game;

	public HmiController(MainViewPane mainViewPane) {
		// TODO Auto-generated constructor stub
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
