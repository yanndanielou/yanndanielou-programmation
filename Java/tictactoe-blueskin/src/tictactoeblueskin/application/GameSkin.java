package tictactoeblueskin.application;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;

class GameSkin extends VBox {
	GameSkin(GameManager gameManager, Game game, MenuBar menuBar) {
		// super(menuBar);
		getChildren().addAll(menuBar, game.getBoard().getSkin(), new StatusIndicator(game),
				new GameControls(gameManager, game));
	}
}
