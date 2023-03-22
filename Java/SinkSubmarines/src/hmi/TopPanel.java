package hmi;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;

import builders.gameboard.GameBoardDataModel;

public class TopPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898026190790239124L;
	private Container parentContainer = null;

	public TopPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel,
			JPanel pannel_above) {

		super(parentContainer, window_width, 100, Color.LIGHT_GRAY, pannel_above);

	}

}
