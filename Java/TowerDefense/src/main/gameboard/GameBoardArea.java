package main.gameboard;

import java.util.List;

import game.builders.GameBoardNamedAreaDataModel;
import game.builders.RectangleDataModel;
import geometry2d.integergeometry.IntegerPrecisionRectangle;

public abstract class GameBoardArea {

	protected IntegerPrecisionRectangle rectangleDefinedArea = null;
	protected List<GameBoardPoint> pointsDefinedArea = null;

	protected GameBoard gameBoard;
	protected String name;

	public GameBoardArea(GameBoard gameBoard, String name, List<GameBoardPoint> points) {
		this.gameBoard = gameBoard;
		this.name = name;
		this.pointsDefinedArea = points;
	}

	protected GameBoardArea(GameBoard gameBoard, RectangleDataModel rectangleDataModel) {
		this.gameBoard = gameBoard;
		this.name = rectangleDataModel.getName();
		this.rectangleDefinedArea = new IntegerPrecisionRectangle(rectangleDataModel.getRectangle());
	}

	protected GameBoardArea(GameBoard gameBoard, IntegerPrecisionRectangle rectangleInImageWithRGB,
			GameBoardNamedAreaDataModel gameBoardNamedAreaDataModel) {
		this.gameBoard = gameBoard;
		this.name = gameBoardNamedAreaDataModel.getName();
		this.rectangleDefinedArea = rectangleInImageWithRGB;
	}

	protected GameBoardArea(GameBoard gameBoard, IntegerPrecisionRectangle rectangleInImageWithRGB, String name) {
		this.gameBoard = gameBoard;
		this.name = name;
		this.rectangleDefinedArea = rectangleInImageWithRGB;
	}

	public String getName() {
		return name;
	}

	public List<GameBoardPoint> getAllPoints() {
		if (rectangleDefinedArea != null) {
			return gameBoard.getGameBoardPoints(rectangleDefinedArea.getAllPoints());
		} else {
			return pointsDefinedArea;
		}
	}

	public IntegerPrecisionRectangle getRectangleDefinedArea() {
		return rectangleDefinedArea;
	}
}
