package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import hmi.SinkSubmarinesMainView;

public class SinkSubmarinesMain {

	private static final Logger LOGGER = LogManager.getLogger(SinkSubmarinesMain.class);

	public static void main(String[] args) {
		LOGGER.info("Application start info");

		SinkSubmarinesMainView sinkSubmarinesMainView = new SinkSubmarinesMainView();
		GameManager.getInstance().setSinkSubmarinesMainView(sinkSubmarinesMainView);

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sinkSubmarinesMainView.createAndShowGUI();
			}
		});

	}

}