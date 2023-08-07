package game_board;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game_board.RectangleDataModel;
import game.Game;
import geometry.IntegerRectangle;

public class GameBoardRectangleDefinedArea extends GameBoardArea {

	protected IntegerRectangle rectangle;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardRectangleDefinedArea.class);

	@Deprecated
	public GameBoardRectangleDefinedArea(Game game, Rectangle rectangle, String name) {
		super(game, name);
		this.rectangle = new IntegerRectangle(rectangle);
	}

	public GameBoardRectangleDefinedArea(Game game, RectangleDataModel rectangleDataModel) {
		super(game, rectangleDataModel.getName());
		this.rectangle = new IntegerRectangle(rectangleDataModel.getRectangle());
	}

	@Override
	public List<Point> getAllPoints() {
		return rectangle.getAllPoints();
	}

}
