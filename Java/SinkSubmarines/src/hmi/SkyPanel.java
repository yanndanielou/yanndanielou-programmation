package hmi;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;

import builders.gameboard.GameBoardDataModel;

public class SkyPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898026190790239124L;
	private Container parentContainer = null;

	public SkyPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel,
			JPanel pannel_above) {

		super(parentContainer, gameBoardDataModel, gameBoardDataModel.getSky_game_board_area_data_model(),
				pannel_above);

	}

}
