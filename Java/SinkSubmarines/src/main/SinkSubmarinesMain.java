package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hmi.SinkSubmarinesMainView;
import time.TimeManager;

public class SinkSubmarinesMain {

	private static final Logger LOGGER = LogManager.getLogger(SinkSubmarinesMain.class);

	public static void main(String[] args) {
		LOGGER.info("Application start info");

		// Log4JConfig config = new Log4JConfig();
		TimeManager timeManager = new TimeManager();
		SinkSubmarinesMainView sinkSubmarinesMainView = new SinkSubmarinesMainView("GridLayoutDemo");
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sinkSubmarinesMainView.createAndShowGUI();
			}
		});

	}

}