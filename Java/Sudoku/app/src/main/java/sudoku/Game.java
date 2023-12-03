package sudoku;

import java.util.Calendar;
import java.util.Date;

public class Game {
	public long countUp = 0;
	public Date startDate;

	public Game(SudokuApplication sudokuApplication) {
	}

	public void start() {
		startDate = Calendar.getInstance().getTime();		
	}


}
