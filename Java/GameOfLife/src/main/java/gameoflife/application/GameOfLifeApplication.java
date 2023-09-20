package gameoflife.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.core.GameManager;
import gameoflife.hmi.GameOfLifeMainViewFrame;

public class GameOfLifeApplication {

	private static final Logger LOGGER = LogManager.getLogger(GameOfLifeApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Application start info");

		GameOfLifeMainViewFrame mainView = new GameOfLifeMainViewFrame();
		GameManager.getInstance().setMainViewFrame(mainView);

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainView.createAndShowGUI();
			}
		});

	}

}