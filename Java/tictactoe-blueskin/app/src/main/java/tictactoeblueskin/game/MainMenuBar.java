package tictactoeblueskin.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCombination;

public class MainMenuBar extends javafx.scene.control.MenuBar {

	public MainMenuBar() {

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
		getMenus().addAll(fileMenu, editMenu, helpMenu);

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
