package tetris.hmi;

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
import tetris.application.TetrisApplication;



public class MainViewPane extends Pane{
	
	private static final Logger LOGGER = LogManager.getLogger(MainViewPane.class);

	private static final int APPLICATION_WIDTH = 500;
	private static final int APPLICATION_HEIGHT = 500;
	
	MainBarMenu mainBarMenu ;
	Stage primaryStage;
	
	public MainViewPane(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
	}
	
	public void initialise() {
		mainBarMenu = new MainBarMenu(null);

		BorderPane mainViewBorderPane = new BorderPane();
		mainViewBorderPane.setTop(mainBarMenu);
		mainViewBorderPane.setCenter(this);
		

		Scene scene = new Scene(mainViewBorderPane, APPLICATION_WIDTH, APPLICATION_HEIGHT);
		scene.getStylesheets().add("application.css");

		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setTitle("Tetris - " + common.string.utils.StringUtils.getISO8601CurrentLocalTime());

		VBox scoreVBox = new VBox();
		// scoreVBox.resize(100,100);
		scoreVBox.setMinSize(100, 100);
		this.getChildren().add(scoreVBox);
		scoreVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		scoreVBox.getChildren().add(new Label("Score:"));
		
		defineApplicationIcon();


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


}
