package tetris.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tetris.core.GameManager;
import tetris.hmi.javafx.MainBarMenu;
import tetris.hmi.javafx.views.MainViewPane;

/***
 * Source:
 * https://steemit.com/programming/@satoshio/conway-s-game-of-life-implementation-in-javafx
 */
public class TetrisJavaFxApplication extends Application {
	private static final Logger LOGGER = LogManager.getLogger(TetrisJavaFxApplication.class);

	public Stage primaryStage; 

	@Override
	public void start(Stage primaryStage) {

		// Creates a reference to the primaryStage to be
		// able to manipulate it in other methods
		this.primaryStage = primaryStage;

		MainViewPane mainViewPane = new MainViewPane(primaryStage);
		mainViewPane.initialise();
		
	}

	/**
	 * Main method for the Sudoku game
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
