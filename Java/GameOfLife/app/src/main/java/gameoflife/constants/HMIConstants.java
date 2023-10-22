package gameoflife.constants;

import java.awt.Color;
import java.awt.Dimension;

public class HMIConstants {

	private HMIConstants() {
		// no way to instantiate this class
	}

	public static final Dimension MINIMUM_WINDOW_DIMENSION = new Dimension(400, 600);

	public static final Dimension SPACE_BETWEEN_COMMANDS_DIMENSION = new Dimension(5, 5);

	public static final int EXTERNAL_FRAME_WIDTH = 1;
	public static final int TOP_PANEL_HEIGHT = 45;
	public static final int TOP_PANEL_ELEMENTS_HEIGHT = (int) (0.9 * TOP_PANEL_HEIGHT);

	public static final int MENU_BAR_HEIGHT = 10;

	public static final Color TOP_PANEL_BACKGROUND_COLOR = Color.GRAY;
	public static final int TOP_PANEL_HORIZONTAL_SPACES_BETWEEN_SYMBOLS_AND_TEXT_VALUE = 15;

	public static final int MINIMUM_CELL_SIZE_IN_PIXELS = 1;
	public static final int MAXIMUM_CELL_SIZE_IN_PIXELS = 100;
	public static final int INITIAL_CELL_SIZE_IN_PIXELS = Math.max(1, MAXIMUM_CELL_SIZE_IN_PIXELS / 20);

	
	public static final int TOP_PANNEL_HEIGHT = 40;
	public static final int BOTTOM_PANNEL_HEIGHT = TOP_PANNEL_HEIGHT;

	// FIXME
	public static final int NOT_UNDERSTOOD_MISSING_FRAME_HEIGHT = 50;
	public static final int NOT_UNDERSTOOD_MISSING_FRAME_WIDTH = 20;

}
