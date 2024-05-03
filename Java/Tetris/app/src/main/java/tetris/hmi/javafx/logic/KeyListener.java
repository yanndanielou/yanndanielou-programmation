package tetris.hmi.javafx.logic;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import tetris.game.Game;
import tetris.hmi.javafx.views.MainViewPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.EventHandler;

public class KeyListener implements EventHandler<KeyEvent> {

	private static final Logger LOGGER = LogManager.getLogger(KeyListener.class);
	private Scene scene;
	private Game game;
	private HmiController hmiController;

	public KeyListener(Scene scene, HmiController hmiController) {
		this.scene = scene;
		this.hmiController = hmiController;
		scene.setOnKeyReleased((javafx.event.EventHandler<? super KeyEvent>) this);
	}

	@Override
	public void handle(KeyEvent event) {
		LOGGER.info(()-> "Key event " + event.getCharacter() + " " + event.getCode() +  " " + event.getText() +  " " + event);
		switch (event.getCode()) {
		case UP:
			break;
		case DOWN:
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		case SHIFT:
			break;
		}
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
