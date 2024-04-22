module tictactoe {
	requires javafx.controls;
	requires org.apache.logging.log4j;
	
	opens tetris.application to javafx.graphics, javafx.fxml;
}
