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
import tetris.hmi.javafx.dialogs.NewGameWhileGameIsInProgressPopup;

public class MainBarMenu extends MenuBar {

	public TetrisJavaFxApplication tetrisApplication;

	public MainBarMenu(TetrisJavaFxApplication sudokuApplication) {
		this.tetrisApplication = sudokuApplication;

		createMenuGame();
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
