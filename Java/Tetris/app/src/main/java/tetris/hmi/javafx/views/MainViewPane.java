package tetris.hmi.javafx.views;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import tetris.application.TetrisJavaFxApplication;
import tetris.core.GameManager;
import tetris.game.Game;
import tetris.hmi.TetrisMainViewGeneric;
import tetris.hmi.javafx.MainBarMenu;

public class MainViewPane extends Pane implements TetrisMainViewGeneric {

	private static final Logger LOGGER = LogManager.getLogger(MainViewPane.class);

	private static final int APPLICATION_WIDTH = 500;
	private static final int APPLICATION_HEIGHT = 500;

	MainBarMenu mainBarMenu;
	Stage primaryStage;
	ScoreFrame scoreFrame;
	MatrixView matrixView;

	public MainViewPane(Stage primaryStage) {

		this.primaryStage = primaryStage;
	}

	public void initialise() {
		mainBarMenu = new MainBarMenu(null);

		BorderPane mainViewBorderPane = new BorderPane();
		mainViewBorderPane.setTop(mainBarMenu);
		
		matrixView = new MatrixView();
		mainViewBorderPane.setCenter(matrixView);

		Scene scene = new Scene(mainViewBorderPane, APPLICATION_WIDTH, APPLICATION_HEIGHT);
		scene.getStylesheets().add("application.css");

		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setTitle("Tetris - " + common.string.utils.StringUtils.getISO8601CurrentLocalTime());

		scoreFrame = new ScoreFrame();
		mainViewBorderPane.setLeft(scoreFrame);

		defineApplicationIcon();

		GameManager.getInstance().setMainViewFrame(this);

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

	@Override
	public void registerToGame(Game game) {
matrixView.initialize(game);
	}

}
