package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import hmi.DesktopTowerDefenseMainViewFrame;

public class DesktopTowerDefenseMain {

	private static final Logger LOGGER = LogManager.getLogger(DesktopTowerDefenseMain.class);

	public static void main(String[] args) {
		LOGGER.info("Application start info");

		DesktopTowerDefenseMainViewFrame DesktopTowerDefenseMainView = new DesktopTowerDefenseMainViewFrame();
		GameManager.getInstance().setDesktopTowerDefenseMainView(DesktopTowerDefenseMainView);

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DesktopTowerDefenseMainView.createAndShowGUI();
			}
		});

	}

}