package tictactoeblueskin.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GameControls extends HBox {
	GameControls(final GameManager gameManager, final Game game) {
		getStyleClass().add("game-controls");

		visibleProperty().bind(game.gameOverProperty());

		Label playAgainLabel = new Label("Play Again?");
		playAgainLabel.getStyleClass().add("info");

		Button playAgainButton = new Button("Yes");
		playAgainButton.getStyleClass().add("play-again");
		playAgainButton.setDefaultButton(true);
		playAgainButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				gameManager.newGame();
			}
		});

		Button exitButton = new Button("No");
		playAgainButton.getStyleClass().add("exit");
		exitButton.setCancelButton(true);
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				gameManager.quit();
			}
		});

		getChildren().setAll(playAgainLabel, playAgainButton, exitButton);
	}
}
