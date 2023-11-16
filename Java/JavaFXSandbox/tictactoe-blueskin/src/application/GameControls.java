package application;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;

import java.util.*;

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
