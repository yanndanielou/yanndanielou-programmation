package sudoku;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;

public class Game {
	private static final Logger LOGGER = LogManager.getLogger(Game.class);

	public long gameDurationInMilliseconds = 0;
	public Date startDate;
	public ViewPresenter viewPresenter;

	public Game(SudokuApplication sudokuApplication) {
		LOGGER.info(() -> "Create Game");

	}

	public void start() {
		startDate = Calendar.getInstance().getTime();
		LOGGER.info(() -> "Start Game at " + startDate);
	}

	public void updateDuration(ActionEvent e) {
		gameDurationInMilliseconds = Calendar.getInstance().getTime().getTime() - startDate.getTime();
		LOGGER.debug(() -> "Game duration updated to " + gameDurationInMilliseconds);
		viewPresenter.onGameDurationUpdated(gameDurationInMilliseconds);
	}

	public void setViewPresenter(ViewPresenter viewPresenter) {
		this.viewPresenter = viewPresenter;
	}

}
