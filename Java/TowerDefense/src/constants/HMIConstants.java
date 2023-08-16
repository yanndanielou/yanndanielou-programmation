package constants;

import java.awt.Color;

public class HMIConstants {

	private HMIConstants() {
		// no way to instantiate this class
	}

	public static final int EXTERNAL_FRAME_WIDTH = 1;
	public static final int TOP_PANEL_HEIGHT = 45;
	public static final int TOP_PANEL_ELEMENTS_HEIGHT = (int) (0.9 * TOP_PANEL_HEIGHT);

	public static final int MENU_BAR_HEIGHT = 10;

	public static final Color IMPOSSIBLE_CONSTRUCTION_COLOR = new Color(217, 111, 71);
	public static final Color POSSIBLE_CONSTRUCTION_COLOR = new Color(89, 215, 73);

	public static final Color TOP_PANEL_BACKGROUND_COLOR = Color.BLACK;
	public static final int TOP_PANEL_HORIZONTAL_SPACES_BETWEEN_SYMBOLS_AND_TEXT_VALUE = 15;

	public static final Color SCORE_IN_TOP_PANEL_COLOR = Color.WHITE;
	public static final Color NEXT_ATTACKERS_WAVE_COUNTDOWN_IN_TOP_PANEL_COLOR = Color.WHITE;
	public static final Color REMAINING_LIVES_IN_TOP_PANEL_COLOR = Color.RED;
	public static final Color GOLD_IN_TOP_PANEL_COLOR = Color.YELLOW;

	// FIXME
	public static final int NOT_UNDERSTOOD_MISSING_FRAME_HEIGHT = 50;
	public static final int NOT_UNDERSTOOD_MISSING_FRAME_WIDTH = 20;

}
