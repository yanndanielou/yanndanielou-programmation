package tetris.hmi.javafx.dialogs;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import tetris.core.GameManager;
import tetris.game.Game;

public class NewGameWhileGameIsInProgressPopup {

	private static final Logger LOGGER = LogManager.getLogger(NewGameWhileGameIsInProgressPopup.class);

	public NewGameWhileGameIsInProgressPopup() {

		if (GameManager.hasGameInProgress()) {

			Game game = GameManager.getInstance().getGame();
			if (game.isBegun()) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("New game while game is in progress confirmation");
				alert.setHeaderText("A game is already in progress");
				alert.setContentText("Do you want to abort current game and start a new one?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					LOGGER.info("Abort current game");
					GameManager.getInstance().abortCurrentGame();
				} else {
					LOGGER.info("Do not abort current game. Resume");
					return;
				}
			} else {
				GameManager.getInstance().abortCurrentGame();
			}

		}

		GameManager.getInstance().newGame();
	}

}
