package main;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import hmi.TetrisMainViewFrame;

public class TetrisMain {

	private static final Logger LOGGER = LogManager.getLogger(TetrisMain.class);

	public static void main(String[] args) {
		LOGGER.info("Application start info");

		TetrisMainViewFrame tetrisMainView = new TetrisMainViewFrame();
		GameManager.getInstance().setTetrisMainViewFrame(tetrisMainView);

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tetrisMainView.createAndShowGUI();
			}
		});

	}

}