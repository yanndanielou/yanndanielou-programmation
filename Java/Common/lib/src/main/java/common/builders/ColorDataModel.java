package common.builders;

import java.awt.Color;

import common.color.StringToColorConversion;

public class ColorDataModel extends NamedDataModel {

	Integer red;
	Integer green;
	Integer blue;

	String colorName;

	public Color getColorAsAwtColor() {
		if (colorName != null) {
			return StringToColorConversion.convertStringToColor(colorName);
		}
		return new Color(red, green, blue);
	}

}
