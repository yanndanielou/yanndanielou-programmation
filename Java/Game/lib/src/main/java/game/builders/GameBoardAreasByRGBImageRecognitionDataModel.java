package game.builders;

import java.awt.Color;

import common.builders.NamedDataModel;

public class GameBoardAreasByRGBImageRecognitionDataModel extends NamedDataModel {

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
