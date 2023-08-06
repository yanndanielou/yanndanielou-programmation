package game_board;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.RectangleDataModel;
import game.Game;

public class GameBoardRectangleArea extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = -128890507157976138L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardRectangleArea.class);

	protected Game game;
	protected String name;

	@Deprecated
	public GameBoardRectangleArea(Game game, Rectangle rectangle, String name) {
		super(rectangle);
		this.game = game;
		this.name = name;
	}

	public GameBoardRectangleArea(Game game, RectangleDataModel rectangleDataModel) {
		super(rectangleDataModel.getRectangle());
		this.game = game;
		this.name = rectangleDataModel.getName();
	}

	public Game getGame() {
		return game;
	}
}
