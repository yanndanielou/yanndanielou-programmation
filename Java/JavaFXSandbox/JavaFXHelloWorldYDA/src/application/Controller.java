package application;

import javafx.scene.control.Label;

public class Controller {

	private Label label;

	public void initialize() {
		String javaVersion = System.getProperty("java.version");
		String javafxVersion = System.getProperty("javafx.version");
		label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
	}
}
