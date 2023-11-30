package tictactoeblueskin.hmi;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import tictactoeblueskin.application.GameManager;
import tictactoeblueskin.game.Game;

public class GameSkin extends VBox {
	public GameSkin(GameManager gameManager, Game game, MenuBar menuBar) {
		getChildren().addAll(menuBar, game.getBoard().getSkin(), new StatusIndicator(game),
				new GameControls(gameManager, game));
	}
}
