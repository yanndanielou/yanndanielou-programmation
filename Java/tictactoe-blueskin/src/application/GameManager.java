package application;

import javafx.scene.Scene;

public class GameManager {
	private Scene gameScene;
	private Game game;

	GameManager() {
		newGame();
	}

	public void newGame() {
		game = new Game(this);

		if (gameScene == null) {
			gameScene = new Scene(game.getSkin());
		} else {
			gameScene.setRoot(game.getSkin());
		}
	}

	public void quit() {
		gameScene.getWindow().hide();
	}

	public Game getGame() {
		return game;
	}

	public Scene getGameScene() {
		return gameScene;
	}
}