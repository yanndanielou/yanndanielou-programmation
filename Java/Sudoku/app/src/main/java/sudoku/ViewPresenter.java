package sudoku;

import java.util.concurrent.TimeUnit;

import javafx.stage.Stage;

public class ViewPresenter {

	private Stage stage;
	private Game game;
	private SudokuApplication sudokuApplication;
	
	public int currentlySelectedDigit = 0;

	public ViewPresenter(SudokuApplication sudokuApplication, Game game, Stage stage) {
		this.stage = stage;
		this.game = game;
		this.sudokuApplication = sudokuApplication;
	}

	public void onGameDurationUpdated(long countUp) {
		stage.setTitle("Sudoku - Time: "
				+ String.valueOf(TimeUnit.SECONDS.convert(countUp, TimeUnit.MILLISECONDS)));
	}

}
