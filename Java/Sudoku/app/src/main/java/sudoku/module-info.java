module tictactoe {
	requires javafx.controls;
	requires org.apache.logging.log4j;
	
	opens sudoku to javafx.graphics, javafx.fxml;
}
