package game_board;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game_board.GameBoardNamedAreaDataModel;
import builders.game_board.RectangleDataModel;
import game.Game;
import geometry.IntegerRectangle;

public class GameBoardAttackersExitArea extends GameBoardRectangleDefinedArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3187118291142484504L;
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardAttackersExitArea.class);

	public GameBoardAttackersExitArea(Game game, RectangleDataModel rectangleDataModel) {
		super(game, rectangleDataModel);
	}

	public GameBoardAttackersExitArea(Game game, IntegerRectangle rectangleInImageWithRGB,
			GameBoardNamedAreaDataModel gameBoardNamedAreaDataModel) {
		super(game, rectangleInImageWithRGB, gameBoardNamedAreaDataModel);
	}

}
