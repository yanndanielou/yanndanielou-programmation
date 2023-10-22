package gameoflife.hmi;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeyBoardInputs implements KeyListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(KeyBoardInputs.class);

	@SuppressWarnings("unused")
	private Container parent;
	
	private HmiPresenter hmiPresenter;

	public KeyBoardInputs(Container parent, HmiPresenter hmiPresenter) {
		this.parent = parent;
		this.hmiPresenter = hmiPresenter;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		LOGGER.info("Key typed:" + KeyEvent.getKeyText(e.getKeyCode()) + " event:" + e);

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			LOGGER.info("Escape key typed:" + KeyEvent.getKeyText(e.getKeyCode()) + " event:" + e);
			hmiPresenter.setDrawActionInProgress(null);
		}
		// new
		// NewGameWhileGameIsInProgressPopup(gameOfLifeMainView).displayOptionPane();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		LOGGER.info("keyPressed:" + KeyEvent.getKeyText(e.getKeyCode()) + " event:" + e);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// LOGGER.info("keyReleased:" + KeyEvent.getKeyText(e.getKeyCode()) + " event:"
		// + e);

	}

}
