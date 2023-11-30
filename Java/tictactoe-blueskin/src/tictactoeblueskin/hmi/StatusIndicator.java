package tictactoeblueskin.hmi;


import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tictactoeblueskin.game.Game;
import tictactoeblueskin.game.Square;

public class StatusIndicator extends HBox {
	private final ImageView playerToken = new ImageView();
	private final Label playerLabel = new Label("Current Player: ");

	StatusIndicator(Game game) {
		getStyleClass().add("status-indicator");

		bindIndicatorFieldsToGame(game);

		playerToken.setFitHeight(32);
		playerToken.setPreserveRatio(true);

		playerLabel.getStyleClass().add("info");

		getChildren().addAll(playerLabel, playerToken);
	}

	private void bindIndicatorFieldsToGame(Game game) {
		playerToken.imageProperty()
				.bind(Bindings.when(game.currentPlayerProperty().isEqualTo(Square.State.NOUGHT))
						.then(SquareSkin.noughtImage)
						.otherwise(Bindings.when(game.currentPlayerProperty().isEqualTo(Square.State.CROSS))
								.then(SquareSkin.crossImage).otherwise((Image) null)));

		playerLabel.textProperty()
				.bind(Bindings.when(game.gameOverProperty().not()).then("Current Player: ")
						.otherwise(Bindings.when(game.winnerProperty().isEqualTo(Square.State.EMPTY)).then("Draw")
								.otherwise("Winning Player: ")));
	}
}
