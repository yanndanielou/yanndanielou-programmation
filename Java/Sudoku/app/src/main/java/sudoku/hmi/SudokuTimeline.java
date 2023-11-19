package sudoku.hmi;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import sudoku.application.SudokuMain;

public class SudokuTimeline {

	private javafx.animation.Timeline timeline;

	public SudokuTimeline(SudokuMain main, Date start, Stage stage) {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			main.countUp = Calendar.getInstance().getTime().getTime() - start.getTime();
			stage.setTitle(
					"Sudoku - Time: " + String.valueOf(TimeUnit.SECONDS.convert(main.countUp, TimeUnit.MILLISECONDS)));
		}));

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

	}

	public void stop() {
		timeline.stop();
	}

}
