package tetris.hmi.javafx.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import tetris.hmi.generic.logic.KeyboardListener;

public class JavaFXKeyboardListener extends KeyboardListener implements EventHandler<KeyEvent> {

	private static final Logger LOGGER = LogManager.getLogger(JavaFXKeyboardListener.class);
	private Scene scene;
	

	public JavaFXKeyboardListener(Scene scene, HmiController hmiController) {
		super(hmiController);
		this.scene = scene;
		scene.setOnKeyPressed((javafx.event.EventHandler<? super KeyEvent>) this);
		scene.setOnKeyReleased((javafx.event.EventHandler<? super KeyEvent>) this);
	}

	@Override
	public void handle(KeyEvent event) {
		LOGGER.info(()-> "Key event character:"  + event.getCharacter() + ", code:" + event.getCode() +  ", text:" + event.getText() +  ", control down:" + event.isControlDown() +  ", shift down:" + event.isControlDown() +  ", event:" + event);
		
		switch (event.getCode()) {
		case LEFT:
			leftArrowPressed();
			break;
		case RIGHT:
			rightArrowPressed();
			break;
		default:
			break;
		}
	}
}
