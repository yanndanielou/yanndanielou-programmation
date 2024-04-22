package tetris.hmi;

import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import tetris.application.TetrisApplication;

public class MainBarMenu extends MenuBar {

	public TetrisApplication tetrisApplication;

	public MainBarMenu(TetrisApplication sudokuApplication) {
		this.tetrisApplication = sudokuApplication;

		createMenuGame();
		createMenuSkill();
		createMenuOptions();
		createMenuHelp();
	}

	private void createMenuGame() {

		Menu menu = new Menu("Game");

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
