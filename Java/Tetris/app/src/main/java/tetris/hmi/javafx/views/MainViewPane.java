package tetris.hmi.javafx.views;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tetris.core.GameManager;
import tetris.game.Game;
import tetris.hmi.TetrisMainViewGeneric;
import tetris.hmi.javafx.MainBarMenuManager;
import tetris.hmi.javafx.logic.HmiController;
import tetris.hmi.javafx.logic.JavaFXKeyboardListener;

public class MainViewPane extends Pane implements TetrisMainViewGeneric {

	private static final Logger LOGGER = LogManager.getLogger(MainViewPane.class);

	private static final int APPLICATION_WIDTH = 500;
	private static final int APPLICATION_HEIGHT = 500;

	MainBarMenuManager mainBarMenuManager;
	Stage primaryStage;
	ScoreFrame scoreFrame;
	MatrixView matrixView;

	JavaFXKeyboardListener keyListener;

	private HmiController hmiController; 
	
	public MainViewPane(Stage primaryStage) {

		this.primaryStage = primaryStage;
	}

	public void initialise() {
		mainBarMenuManager = new MainBarMenuManager();

		MainViewBorderPane mainViewBorderPane = new MainViewBorderPane();
		mainBarMenuManager.addToMainFrame(mainViewBorderPane);

		matrixView = new MatrixView();
		mainViewBorderPane.setCenter(matrixView);

		Scene scene = new Scene(mainViewBorderPane, APPLICATION_WIDTH, APPLICATION_HEIGHT);
		scene.getStylesheets().add("application.css");


		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setTitle("Tetris - " + common.string.utils.StringUtils.getISO8601CurrentLocalTime());

		scoreFrame = new ScoreFrame();
		mainViewBorderPane.setLeft(scoreFrame);
		
		hmiController = new HmiController(this);
		keyListener = new JavaFXKeyboardListener(scene, hmiController);



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
		hmiController.setGame(game);
	}

}
