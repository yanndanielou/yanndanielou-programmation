package game_board;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game_board.GameBoardNamedAreaDataModel;
import builders.game_board.RectangleDataModel;
import game.Game;
import geometry.IntegerPoint;
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

	public GameBoardRectangleDefinedArea(Game game, IntegerRectangle rectangleInImageWithRGB,
			GameBoardNamedAreaDataModel gameBoardNamedAreaDataModel) {
		super(game, gameBoardNamedAreaDataModel.getName());
		this.rectangle = rectangleInImageWithRGB;
	}

	@Override
	public List<GameBoardPoint> getAllPoints() {
		List<GameBoardPoint> allPoints = new ArrayList<>();
		for (IntegerPoint point : rectangle.getAllPoints()) {
			allPoints.add(game.getGameBoard().getGameBoardPoint(point.getRow(), point.getColumn()));
		}
		return allPoints;
	}

}
