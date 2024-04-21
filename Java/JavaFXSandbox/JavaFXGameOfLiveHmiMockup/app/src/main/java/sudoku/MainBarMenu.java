package sudoku;

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

public class MainBarMenu extends MenuBar {

	public GameOfLifeHmiMockupApplication sudokuApplication;

	public MainBarMenu(GameOfLifeHmiMockupApplication sudokuApplication) {
		this.sudokuApplication = sudokuApplication;

		createMenu1();
		createMenu2();
		createMenu3();
	}

	private void createMenu1() {

		Menu menu = new Menu("Menu 1");

		Menu applicationSizeSubMenu = new Menu("Application size");
		menu.getItems().add(applicationSizeSubMenu);
		Menu applicationWidthSubMenu = new Menu("width");
		applicationSizeSubMenu.getItems().add(applicationWidthSubMenu);

		MenuItem increaseApplicationWidthSubMenu = new MenuItem("Increase application Width");
		increaseApplicationWidthSubMenu.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN));
		increaseApplicationWidthSubMenu.setOnAction(e -> {
			sudokuApplication.primaryStage.setWidth(sudokuApplication.primaryStage.getWidth() + 100);
		});
		applicationWidthSubMenu.getItems().add(increaseApplicationWidthSubMenu);

		MenuItem decreaseApplicationWidthSubMenu = new MenuItem("Decrease application Width");
		decreaseApplicationWidthSubMenu.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN));
		decreaseApplicationWidthSubMenu.setOnAction(e -> {
			sudokuApplication.primaryStage.setWidth(sudokuApplication.primaryStage.getWidth() - 100);
		});
		applicationWidthSubMenu.getItems().add(decreaseApplicationWidthSubMenu);

		MenuItem menuItem2 = new MenuItem("Item 2");
		menu.getItems().add(menuItem2);

		Menu subMenu = new Menu("Menu 1.1");
		MenuItem menuItem11 = new MenuItem("Item 1.1.1");
		subMenu.getItems().add(menuItem11);
		MenuItem menuItem12 = new MenuItem("Item 1.1.2");
		subMenu.getItems().add(menuItem12);
		menu.getItems().add(subMenu);

		getMenus().add(menu);
	}

	private void createMenu2() {
		Menu menu = new Menu("Menu 2");

		RadioMenuItem choice1Item = new RadioMenuItem("Choice 1");
		RadioMenuItem choice2Item = new RadioMenuItem("Choice 2");
		RadioMenuItem choice3Item = new RadioMenuItem("Choice 3");

		ToggleGroup toggleGroup = new ToggleGroup();
		toggleGroup.getToggles().add(choice1Item);
		toggleGroup.getToggles().add(choice2Item);
		toggleGroup.getToggles().add(choice3Item);

		menu.getItems().add(choice1Item);
		menu.getItems().add(choice2Item);
		menu.getItems().add(choice3Item);

		getMenus().add(menu);
	}

	private void createMenu3() {
		Menu menu = new Menu("Menu 3");

		Slider slider = new Slider(0, 100, 50);

		CustomMenuItem customMenuItem = new CustomMenuItem();
		customMenuItem.setContent(slider);
		customMenuItem.setHideOnClick(false);
		menu.getItems().add(customMenuItem);

		Button button = new Button("Custom Menu Item Button");
		CustomMenuItem customMenuItem2 = new CustomMenuItem();
		customMenuItem2.setContent(button);
		customMenuItem2.setHideOnClick(false);
		menu.getItems().add(customMenuItem2);

		getMenus().add(menu);
	}

}
