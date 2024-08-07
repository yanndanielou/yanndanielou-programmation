package gameoflife.hmi.swing.dialogs;

import java.awt.Component;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.core.GameManager;
import gameoflife.game.Game;
import gameoflife.hmi.swing.menubar.MainViewMenuBarManager;

public class NewGameWhileGameIsInProgressPopup {
	private static final Logger LOGGER = LogManager.getLogger(MainViewMenuBarManager.class);

	private Component parentComponent;

	public NewGameWhileGameIsInProgressPopup(Component parentComponent) {
		this.parentComponent = parentComponent;
	}

	public void displayOptionPane() {

		// TODO Auto-generated constructor stub

		if (GameManager.hasGameInProgress()) {

			Game game = GameManager.getInstance().getGame();
			if (game.isBegun()) {

				Object[] options = { "Yes", "No" };

				int n = JOptionPane.showOptionDialog(parentComponent, "Abort current game?", "Game in progress!",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				LOGGER.info("Abort current game popup answer: " + n);
				if (n == JOptionPane.YES_OPTION) {
					LOGGER.info("Abort current game");
					GameManager.getInstance().abortCurrentGame();
				} else if (n == JOptionPane.NO_OPTION) {
					LOGGER.info("Do not abort current game. Resume");
					return;
				} else {
					LOGGER.info("Default answer (popup closed?)");
					return;
				}
			} else {
				GameManager.getInstance().abortCurrentGame();
			}

		}

		GameManager.getInstance().newGame();
	}
}
