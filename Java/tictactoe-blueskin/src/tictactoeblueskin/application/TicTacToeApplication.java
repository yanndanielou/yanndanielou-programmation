package tictactoeblueskin.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoeblueskin.game.GameManager;
import tictactoeblueskin.hmi.SquareSkin;

public class TicTacToeApplication extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		GameManager gameManager = new GameManager();

		Scene scene = gameManager.getGameScene();
		scene.getStylesheets().add(getResource("tictactoeblueskin.css"));

		stage.setTitle("Tic-Tac-Toe");
		stage.getIcons().add(SquareSkin.crossImage);
		stage.setScene(scene);
		stage.show();
	}

	private String getResource(String resourceName) {
		return getClass().getResource(resourceName).toExternalForm();
	}

	public static void main(String[] args) {
		Application.launch(TicTacToeApplication.class);
	}
}

