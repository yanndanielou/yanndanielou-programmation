package tetris.hmi.javafx.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.EventHandler;
import javafx.event.EventType;
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
		LOGGER.info(() -> {
			EventType<KeyEvent> eventType = event.getEventType();
			return "Key event character:" + event.getCharacter() + ", code:" + event.getCode() + ", text:"
					+ event.getText() + ", type:" + eventType + ", control down:" + event.isControlDown()
					+ ", shift down:" + event.isControlDown() + ", event:" + event;
		});

		switch (event.getCode()) {
		case LEFT:
			if (event.getEventType() == KeyEvent.KEY_PRESSED) {
				leftArrowPressed();
			}
			break;
		case RIGHT:
			if (event.getEventType() == KeyEvent.KEY_PRESSED) {
				rightArrowPressed();
			}
			break;
		case DOWN:
			if (event.getEventType() == KeyEvent.KEY_PRESSED) {
				downArrowPressed();
			}
			break;
		case D:
			if (event.getEventType() == KeyEvent.KEY_PRESSED) {
				dKeyPressed();
			}
			break;
		case SPACE:
			if (event.getEventType() == KeyEvent.KEY_PRESSED) {
				spaceKeyPressed();
			}
			break;
		default:
			break;
		}
	}
}
