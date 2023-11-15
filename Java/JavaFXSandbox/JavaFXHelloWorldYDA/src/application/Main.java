package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
	private Label label;

	@Override
	public void start(Stage stage) {
		// Creating a Label
		Label sampleLabel = new Label("Sample label");
		// Setting font to the label
		Font font = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 25);
		sampleLabel.setFont(font);
		// Filling color to the label
		sampleLabel.setTextFill(Color.BROWN);
		// Setting the position
		sampleLabel.setTranslateX(150);
		sampleLabel.setTranslateY(25);

		
		String javaVersion = System.getProperty("java.version");
		String javafxVersion = System.getProperty("javafx.version");
		Label versionLabel = new Label();
		versionLabel.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
		versionLabel.setTranslateX(50);
		versionLabel.setTranslateY(50);

		Group root = new Group();
		root.getChildren().add(sampleLabel);
		root.getChildren().add(versionLabel);
		// Setting the stage
		Scene scene = new Scene(root, 595, 150, Color.BEIGE);
		stage.setTitle("Label Example");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
