package builders.gameboard;

import java.awt.Color;

public class GameBoardAreaDataModel {

	private int height;
	private ColorDefinition background_color_definition;
	private String background_image_path;
	private int top_altitude;

	public int getHeight() {
		return height;
	}

	public ColorDefinition getBackground_color_definition() {
		return background_color_definition;
	}

	public Color getBackground_color_as_awt_color() {
		Color color = null;
		if (background_color_definition != null) {
			color = new Color(background_color_definition.red, background_color_definition.green,
					background_color_definition.blue);
		}
		return color;
	}

	public String getBackground_image_path() {
		return background_image_path;
	}

	class ColorDefinition {
		int red;
		int green;
		int blue;
	}

	public GameBoardAreaDataModel() {
	}

	public int getTop_altitude() {
		return top_altitude;
	}

}
