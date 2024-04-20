package sudoku;

import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

public class MainBarMenu extends MenuBar {

	public SudokuApplication sudokuApplication;

	public MainBarMenu(SudokuApplication sudokuApplication) {
		this.sudokuApplication = sudokuApplication;

		createMenu1();
		createMenu2();
		createMenu3();
	}

	private void createMenu1() {

		Menu menu = new Menu("Menu 1");

		MenuItem menuItem1 = new MenuItem("Increase application Width");
		menuItem1.setOnAction(e -> {
			sudokuApplication.stage.setWidth(sudokuApplication.stage.getWidth() + 100);
		});

		MenuItem menuItem2 = new MenuItem("Item 2");

		menu.getItems().add(menuItem1);
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
