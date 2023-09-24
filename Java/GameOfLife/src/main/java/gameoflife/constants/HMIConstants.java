package gameoflife.constants;

import java.awt.Color;

public class HMIConstants {

	private HMIConstants() {
		// no way to instantiate this class
	}

	public static final int EXTERNAL_FRAME_WIDTH = 1;
	public static final int TOP_PANEL_HEIGHT = 45;
	public static final int TOP_PANEL_ELEMENTS_HEIGHT = (int) (0.9 * TOP_PANEL_HEIGHT);

	public static final int MENU_BAR_HEIGHT = 10;

	public static final Color TOP_PANEL_BACKGROUND_COLOR = Color.GRAY;
	public static final int TOP_PANEL_HORIZONTAL_SPACES_BETWEEN_SYMBOLS_AND_TEXT_VALUE = 15;
	
	public static final int CELL_HEIGHT_IN_PIXELS = 10;
	public static final int CELL_WIDTH_IN_PIXELS = 10;

	// FIXME
	public static final int NOT_UNDERSTOOD_MISSING_FRAME_HEIGHT = 50;
	public static final int NOT_UNDERSTOOD_MISSING_FRAME_WIDTH = 20;

}
