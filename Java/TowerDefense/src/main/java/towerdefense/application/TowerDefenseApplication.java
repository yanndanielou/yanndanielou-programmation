package towerdefense.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import towerdefense.core.GameManager;
import towerdefense.hmi.TowerDefenseMainViewFrame;

public class TowerDefenseApplication {

	private static final Logger LOGGER = LogManager.getLogger(TowerDefenseApplication.class);

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