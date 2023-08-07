package game_board;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game_board.RectangleDataModel;
import game.Game;

public class GameBoardAttackersEntryArea extends GameBoardRectangleArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5168484650864604408L;
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardAttackersEntryArea.class);

	public GameBoardAttackersEntryArea(Game game, RectangleDataModel rectangleDataModel) {
		super(game, rectangleDataModel);
	}
}
