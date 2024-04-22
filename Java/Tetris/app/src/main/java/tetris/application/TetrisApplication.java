package tetris.application;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tetris.hmi.MainBarMenu;

/***
 * Source:
 * https://steemit.com/programming/@satoshio/conway-s-game-of-life-implementation-in-javafx
 */
public class TetrisApplication extends Application {
	private static final Logger LOGGER = LogManager.getLogger(TetrisApplication.class);

	public Stage primaryStage;

	private static final int APPLICATION_WIDTH = 500;
	private static final int APPLICATION_HEIGHT = 500;
	private static float CELL_SIZE = 10;
	private static boolean SHOW_BORDERS = true;

	private int RUN_SPEED_IN_MILLISECONDS = 500;

	private static float ZOOM_FACTOR = 1;

	@Override
	public void start(Stage primaryStage) {

		// Creates a reference to the primaryStage to be
		// able to manipulate it in other methods
		this.primaryStage = primaryStage;

		defineApplicationIcon();

		MainBarMenu mainBarMenu = new MainBarMenu(this);

		BorderPane mainViewBorderPane = new BorderPane();
		mainViewBorderPane.setTop(mainBarMenu);
//		mainViewBorderPane.setCenter(scrollPane);

		HBox controlButtonsHBox = new HBox();
		
		Scene scene = new Scene(mainViewBorderPane, APPLICATION_WIDTH, APPLICATION_HEIGHT);
		scene.getStylesheets().add("application.css");

		primaryStage.setScene(scene);
		primaryStage.show();
		

	}

	private void defineApplicationIcon() {
		// Application icon
		String applicationIconPath = "applicationIcon.png";
		LOGGER.info(() -> "defineApplicationIcon:" + applicationIconPath);
		try {
			Image applicationIcon = new Image(applicationIconPath);
			primaryStage.getIcons().add(applicationIcon);
		} catch (IllegalArgumentException illegalArgumentException) {
			LOGGER.error(() -> "Could not defineApplicationIcon:" + applicationIconPath);
		}
	}

	/**
	 * Main method for the Sudoku game
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
