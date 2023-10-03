package gameoflife.hmi;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeyBoardInputs implements KeyListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(KeyBoardInputs.class);

	@SuppressWarnings("unused")
	private GameOfLifeMainViewFrame gameOfLifeMainView;

	public KeyBoardInputs(GameOfLifeMainViewFrame gameOfLifeMainView) {
		this.gameOfLifeMainView = gameOfLifeMainView;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		LOGGER.info("keyTyped:" + KeyEvent.getKeyText(e.getKeyCode()) + " event:" + e);

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameOfLifeMainView.getHmiPresenter().setDrawActionInProgress(null);
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
