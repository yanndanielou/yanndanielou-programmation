module tictactoe {
	requires javafx.controls;
	
	opens tictactoeblueskin.application to javafx.graphics, javafx.fxml;
}
