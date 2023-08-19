package main.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.core.GameManager;
import main.hmi.TowerDefenseMainViewFrame;

public class TowerDefenseMain {

	private static final Logger LOGGER = LogManager.getLogger(TowerDefenseMain.class);

	public static void main(String[] args) {
		LOGGER.info("Application start info");

		TowerDefenseMainViewFrame DesktopTowerDefenseMainView = new TowerDefenseMainViewFrame();
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