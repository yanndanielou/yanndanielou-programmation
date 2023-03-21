package hmi;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeyBoardInputs implements KeyListener {
	private static final Logger LOGGER = LogManager.getLogger(KeyBoardInputs.class);

	private SinkSubmarinesMainView sinkSubmarinesMainView = null;

	public KeyBoardInputs(SinkSubmarinesMainView sinkSubmarinesMainView) {
		this.sinkSubmarinesMainView = sinkSubmarinesMainView;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		LOGGER.info("keyTyped:" + e);
		LOGGER.info(KeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		LOGGER.info("keyPressed:" + e);
		LOGGER.info(KeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		LOGGER.info("keyReleased:" + e);
		LOGGER.info(KeyEvent.getKeyText(e.getKeyCode()));
	}

}
