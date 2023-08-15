package constants;

import java.awt.Color;

public class HMIConstants {

	private HMIConstants() {
		// no way to instantiate this class
	}

	public static final int EXTERNAL_FRAME_WIDTH = 5;
	public static final int TOP_PANEL_HEIGHT = 30;
	public static final int TOP_PANEL_ELEMENTS_HEIGHT = (int) (0.9 * TOP_PANEL_HEIGHT);

	public static final int MENU_BAR_HEIGHT = 10;

	public static final Color IMPOSSIBLE_CONSTRUCTION_COLOR = new Color(217, 111, 71);
	public static final Color POSSIBLE_CONSTRUCTION_COLOR = new Color(89, 215, 73);

	// FIXME
	public static final int NOT_UNDERSTOOD_MISSING_FRAME_HEIGHT = 50;
	public static final int NOT_UNDERSTOOD_MISSING_FRAME_WIDTH = 20;

}
