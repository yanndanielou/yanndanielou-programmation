package game_board;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.RectangleDataModel;
import game.Game;

public class GameBoardAttackersExitArea extends GameBoardRectangleArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3187118291142484504L;
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardAttackersExitArea.class);

	public GameBoardAttackersExitArea(Game game, RectangleDataModel rectangleDataModel) {
		super(game, rectangleDataModel);
	}
}
