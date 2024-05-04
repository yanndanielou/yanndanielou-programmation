package sudoku;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
