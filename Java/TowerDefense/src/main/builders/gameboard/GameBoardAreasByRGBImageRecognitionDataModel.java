package main.builders.gameboard;

import java.awt.Color;

public class GameBoardAreasByRGBImageRecognitionDataModel extends GameBoardNamedAreaDataModel {

	private String imageToParsePath;

	private ColorDefinition rgbColorDefinition;

	class ColorDefinition {
		int red;
		int green;
		int blue;
	}

	public String getImageToParsePath() {
		return imageToParsePath;
	}

	public Color getBackgroundColorAsAwtColor() {
		Color color = null;
		if (rgbColorDefinition != null) {
			color = new Color(rgbColorDefinition.red, rgbColorDefinition.green, rgbColorDefinition.blue);
		}
		return color;
	}

}
