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


	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardRectangleDefinedArea.class);

	@Deprecated
	public GameBoardRectangleDefinedArea(Game game, Rectangle rectangle, String name) {
		super(game, name);
		this.rectangleDefinedArea = new IntegerRectangle(rectangle);
	}


	@Override
	public List<GameBoardPoint> getAllPoints() {
		return game.getGameBoard().getGameBoardPoints(rectangleDefinedArea.getAllPoints());
	/*	List<GameBoardPoint> allPoints = new ArrayList<>();
		for (IntegerPoint point : rectangle.getAllPoints()) {
			allPoints.add(game.getGameBoard().getGameBoardPoint(point.getRow(), point.getColumn()));
		}
		return allPoints;
		*/
	}

}
