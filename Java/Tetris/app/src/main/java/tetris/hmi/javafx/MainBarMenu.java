package tetris.hmi.javafx;

import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import tetris.application.TetrisJavaFxApplication;
import tetris.core.GameManager;
import tetris.game.PauseReason;
import tetris.game_objects.tetrominoes_types.TetrominoOSquare;
import tetris.game_objects.tetrominoes_types.TetrominoType;
import tetris.hmi.javafx.dialogs.NewGameWhileGameIsInProgressPopup;

public class MainBarMenu extends MenuBar {

	public TetrisJavaFxApplication tetrisApplication;

	public MainBarMenu(TetrisJavaFxApplication sudokuApplication) {
		this.tetrisApplication = sudokuApplication;

		createMenuGame();
		createMenuTest();
		createMenuSkill();
		createMenuOptions();
		createMenuHelp();
	}

	private void createMenuGame() {

		Menu menu = new Menu("Game");
		
		MenuItem newGameMenuItem = new MenuItem("New");
		newGameMenuItem.setOnAction(e -> {
			new NewGameWhileGameIsInProgressPopup();
		});
		newGameMenuItem.setAccelerator(KeyCharacterCombination.valueOf("F2"));
		menu.getItems().add(newGameMenuItem);
		

		MenuItem pauseMenuItem = new MenuItem("Pause");
		pauseMenuItem.setOnAction(e -> {
			if(GameManager.hasGameInProgress()) {
				GameManager.getInstance().getGame().togglePauseReason(PauseReason.PAUSE_REQUESTED_IN_HMI);
			}
		});
		pauseMenuItem.setAccelerator(KeyCharacterCombination.valueOf("P"));
		menu.getItems().add(pauseMenuItem);

		getMenus().add(menu);
	}

	private void createMenuTest() {

		Menu menu = new Menu("Tests");

		Menu dropNewTetromino = new Menu("Drop new Tetrimino");

		MenuItem launchNewTetrominoOSquareMenuItem = new MenuItem("Tetromino O (square)");
		dropNewTetromino.getItems().add(launchNewTetrominoOSquareMenuItem);
		dropNewTetromino.setOnAction(e -> {
			if (GameManager.hasGameInProgress()) {
				GameManager.getInstance().getGame().tryAndDropNewTetrimino(TetrominoType.O_SQUARE);
			}
		});

		launchNewTetrominoOSquareMenuItem.setAccelerator(
				new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

		menu.getItems().add(dropNewTetromino);

		getMenus().add(menu);
	}

	private void createMenuSkill() {
		Menu menu = new Menu("Skill");

		getMenus().add(menu);
	}

	private void createMenuOptions() {
		Menu menu = new Menu("Options");

		getMenus().add(menu);
	}

	private void createMenuHelp() {
		Menu menu = new Menu("Help");

		getMenus().add(menu);
	}

}
