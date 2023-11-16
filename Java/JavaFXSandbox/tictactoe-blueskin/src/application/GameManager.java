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