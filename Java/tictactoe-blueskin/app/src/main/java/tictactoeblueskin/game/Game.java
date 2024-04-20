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

		MainMenuBar mainMenuBar = new MainMenuBar();


		skin = new GameSkin(gameManager, this, mainMenuBar);
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