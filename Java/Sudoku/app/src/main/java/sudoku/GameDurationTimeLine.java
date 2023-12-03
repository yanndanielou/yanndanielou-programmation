package sudoku;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameDurationTimeLine {

	private Timeline timeline;

	public GameDurationTimeLine(SudokuApplication sudokuApplication, Stage stage) {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			sudokuApplication.game.updateDuration(e);
		}));

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public void stop() {
		timeline.stop();
	}

}
