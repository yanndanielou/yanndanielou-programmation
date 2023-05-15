package main;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import core.UserChoicesManager;
import hmi.DemineurMainViewFrame;

public class DemineurMain {

	private static final Logger LOGGER = LogManager.getLogger(DemineurMain.class);

	public static void main(String[] args) {
		LOGGER.info("Application start info");
		
		UserChoicesManager.getInstance().initialise();

		DemineurMainViewFrame demineurMainView = new DemineurMainViewFrame();
		GameManager.getInstance().setDemineurMainView(demineurMainView);
		
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				demineurMainView.createAndShowGUI();
			}
		});

	}

}