package tetris.hmi.javafx.views;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScoreFrame extends BorderPane {
	
	Label scoreStaticText = new Label("Score:");
	Label currentScore = new Label();
	Label levelStaticText = new Label("Level:");
	Label currentLevel = new Label();
	Label linesStaticText = new Label("Lines:");
	Label currentLines= new Label();
	
	
	
	public ScoreFrame() {

		VBox scoreVBox = new VBox();
		// scoreVBox.resize(100,100);
		scoreVBox.setMinSize(100, 100);
		scoreVBox.setMaxSize(150, 200);
		this.setCenter(scoreVBox);
		scoreVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		scoreVBox.getChildren().addAll(scoreStaticText, currentScore,levelStaticText, currentLevel, linesStaticText, currentLines);
	}
}
