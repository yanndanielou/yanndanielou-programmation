package game_board;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game_board.RectangleDataModel;
import game.Game;

public class GameBoardRectangleDefinedWall extends GameBoardRectangleDefinedArea {

	private GameBoardRectangleDefinedArea gameBoardRectangleDefinedArea = null;
	private GameBoardPointsListDefinedArea gameBoardPointsListDefinedArea = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8239100426793054601L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardRectangleDefinedWall.class);

	@Deprecated
	public GameBoardRectangleDefinedWall(Game game, Rectangle rectangle, String name) {
		super(game, rectangle, name);
	}

	public GameBoardRectangleDefinedWall(Game game, RectangleDataModel rectangleDataModel) {
		super(game, rectangleDataModel);
	}

}
