package tetris.hmi.javafx;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import tetris.application.TetrisJavaFxApplication;
import tetris.core.GameManager;
import tetris.game.PauseReason;
import tetris.game_objects.tetrominoes_types.TetrominoType;
import tetris.hmi.generic.menu.MainBarMenu;
import tetris.hmi.generic.views.MainViewI;
import tetris.hmi.javafx.dialogs.NewGameWhileGameIsInProgressPopup;
import tetris.hmi.javafx.views.MainViewBorderPane;

public class MainBarMenuManager extends MainBarMenu {

	private MenuBar menuBar = new MenuBar(); 

	public MainBarMenuManager() {
		createMenuGame();
		createMenuTest();
		createMenuSkill();
		createMenuOptions();
		createMenuHelp();
	}

	@Override
	public void createMenuGame() {

		Menu menu = new Menu("Game");

		MenuItem newGameMenuItem = new MenuItem("New");
		newGameMenuItem.setOnAction(e -> {
			new NewGameWhileGameIsInProgressPopup();
		});
		newGameMenuItem.setAccelerator(KeyCharacterCombination.valueOf("F2"));
		menu.getItems().add(newGameMenuItem);

		MenuItem pauseMenuItem = new MenuItem("Pause");
		pauseMenuItem.setOnAction(e -> {
			onPauseMenuItemAction();
		});
		pauseMenuItem.setAccelerator(KeyCharacterCombination.valueOf("P"));
		menu.getItems().add(pauseMenuItem);


		MenuItem saveGameMenuItem = new MenuItem("Save");
		saveGameMenuItem.setOnAction(e -> {
			onSaveGameMenuItemAction(); 
		});
		saveGameMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		menu.getItems().add(saveGameMenuItem);

		menuBar.getMenus().add(menu);
	}

	
	@Override
	public void createMenuTest() {

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

		menuBar.getMenus().add(menu);
	}

	@Override
	public void createMenuSkill() {
		Menu menu = new Menu("Skill");

		menuBar.getMenus().add(menu);
	}

	@Override
	public void createMenuOptions() {
		Menu menu = new Menu("Options");

		menuBar.getMenus().add(menu);
	}

	@Override
	public void createMenuHelp() {
		Menu menu = new Menu("Help");

		menuBar.getMenus().add(menu);
	}

	@Override
	public void addToMainFrame(MainViewI mainView) {
		MainViewBorderPane mainViewBorderPane = (MainViewBorderPane) mainView;
		mainViewBorderPane.setTop(menuBar);
	}

}
