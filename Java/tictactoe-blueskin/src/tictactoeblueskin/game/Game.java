package tictactoeblueskin.game;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import tictactoeblueskin.hmi.GameSkin;

public class Game {
	private GameSkin skin;
	private Board board = new Board(this);
	private WinningStrategy winningStrategy = new WinningStrategy(board);

	private ReadOnlyObjectWrapper<SquareState> currentPlayer = new ReadOnlyObjectWrapper<>(SquareState.CROSS);

	public ReadOnlyObjectProperty<SquareState> currentPlayerProperty() {
		return currentPlayer.getReadOnlyProperty();
	}
	public SquareState getCurrentPlayer() {
		return currentPlayer.get();
	}

	private ReadOnlyObjectWrapper<SquareState> winner = new ReadOnlyObjectWrapper<>(SquareState.EMPTY);

	public ReadOnlyObjectProperty<SquareState> winnerProperty() {
		return winner.getReadOnlyProperty();
	}

	private ReadOnlyBooleanWrapper drawn = new ReadOnlyBooleanWrapper(false);

	public ReadOnlyBooleanProperty drawnProperty() {
		return drawn.getReadOnlyProperty();
	}

	public boolean isDrawn() {
		return drawn.get();
	}

	private ReadOnlyBooleanWrapper gameOver = new ReadOnlyBooleanWrapper(false);

	public ReadOnlyBooleanProperty gameOverProperty() {
		return gameOver.getReadOnlyProperty();
	}

	public boolean isGameOver() {
		return gameOver.get();
	}

	public Game(GameManager gameManager) {
		gameOver.bind(winnerProperty().isNotEqualTo(SquareState.EMPTY).or(drawnProperty()));

		// Create MenuBar
		MenuBar menuBar = new MenuBar();

		// Create menus
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		Menu helpMenu = new Menu("Help");

		// Create MenuItems
		MenuItem newItem = new MenuItem("New");
		MenuItem openFileItem = new MenuItem("Open File");
		MenuItem exitItem = new MenuItem("Exit");

		// Set Accelerator for Exit MenuItem.
		exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));

		// When user click on the Exit item.
		exitItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		// Add menuItems to the Menus
		fileMenu.getItems().addAll(newItem, openFileItem, exitItem);

		// Add Menus to the MenuBar
		menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

		skin = new GameSkin(gameManager, this, menuBar);
	}

	public Board getBoard() {
		return board;
	}

	public void nextTurn() {
		if (isGameOver())
			return;

		switch (currentPlayer.get()) {
		case EMPTY:
		case NOUGHT:
			currentPlayer.set(SquareState.CROSS);
			break;
		case CROSS:
			currentPlayer.set(SquareState.NOUGHT);
			break;
		}
	}

	private void checkForWinner() {
		winner.set(winningStrategy.getWinner());
		drawn.set(winningStrategy.isDrawn());

		if (isDrawn()) {
			currentPlayer.set(SquareState.EMPTY);
		}
	}

	public void boardUpdated() {
		checkForWinner();
	}

	public Parent getSkin() {
		return skin;
	}
}