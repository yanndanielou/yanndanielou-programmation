package main.builders.gameboard;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import main.gameboard.GameBoard;
import main.gameboard.GameBoardAttackersEntryArea;
import main.gameboard.GameBoardAttackersExitArea;
import main.gameboard.GameBoardInitiallyConstructibleMacroArea;
import main.gameboard.GameBoardNonPlayableArea;
import main.gameboard.GameBoardPoint;
import main.gameboard.GameBoardWallArea;
import main.geometry2d.integergeometry.IntegerPoint;
import main.geometry2d.integergeometry.IntegerRectangle;

public class GameBoardModelBuilder {
	private static final Logger LOGGER = LogManager.getLogger(GameBoardModelBuilder.class);

	private Gson gson = new Gson();

	private GameBoardDataModel gameBoardDataModel;
	private BufferedImage gameBoardFullBackgroundImageAsBufferedImage;

	public GameBoardDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	public GameBoardModelBuilder(String gameBoardDataModelJsonFile) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(gameBoardDataModelJsonFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gameBoardDataModel = gson.fromJson(br, GameBoardDataModel.class);

		File gameBoardFullBackgroundImageFile = new File(gameBoardDataModel.getGameBoardFullBackgroundImagePath());
		try {
			gameBoardFullBackgroundImageAsBufferedImage = ImageIO.read(gameBoardFullBackgroundImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void buildAllAreas(GameBoard gameBoard) {

		for (RectangleDataModel wallDataModel : gameBoardDataModel.getWallsAsRectangles()) {
			GameBoardWallArea wall = new GameBoardWallArea(gameBoard, wallDataModel);
			gameBoard.addWall(wall);
		}
		for (GameBoardAreasByRGBImageRecognitionDataModel wallDataModel : gameBoardDataModel
				.getPointsDefinedWallAreaAsRGBInImageToParse()) {
			List<IntegerPoint> listOfPixelsInImageWithRGBAsPoint = getListOfPixelsInImageWithRGB(wallDataModel);
			List<GameBoardPoint> listOfPixelsInImageWithRGBAsGameBoardPoints = gameBoard
					.getGameBoardPoints(listOfPixelsInImageWithRGBAsPoint);
			GameBoardWallArea wallArea = new GameBoardWallArea(gameBoard, wallDataModel.getName(),
					listOfPixelsInImageWithRGBAsGameBoardPoints);
			gameBoard.addWall(wallArea);
		}
		for (RectangleDataModel attackersExitAreaDataModel : gameBoardDataModel.getAttackersExitAreasAsRectangles()) {
			GameBoardAttackersExitArea attackersExitArea = new GameBoardAttackersExitArea(gameBoard,
					attackersExitAreaDataModel);
			gameBoard.addGameBoardAttackersExitArea(attackersExitArea);
		}
		for (GameBoardAreasByRGBImageRecognitionDataModel attackersExitAreaDataModel : gameBoardDataModel
				.getRectangleDefinedAttackersExitAreasAsRGBInImageToParse()) {
			IntegerRectangle rectangleInImageWithRGB = getRectangleInImageWithRGB(attackersExitAreaDataModel);
			GameBoardAttackersExitArea attackersExitArea = new GameBoardAttackersExitArea(gameBoard,
					rectangleInImageWithRGB, attackersExitAreaDataModel);
			gameBoard.addGameBoardAttackersExitArea(attackersExitArea);
		}
		for (GameBoardAttackersEntryAreasAsRectangleDataModel attackersEntryAreaDataModel : gameBoardDataModel
				.getAttackersEntryAreasAsRectangles()) {
			GameBoardAttackersEntryArea attackersEntryArea = new GameBoardAttackersEntryArea(gameBoard,
					attackersEntryAreaDataModel, attackersEntryAreaDataModel.getAssociatedExitAreaName());
			gameBoard.addGameBoardAttackersEntryArea(attackersEntryArea);
		}
		for (GameBoardAttackersEntryAreasByRGBImageRecognitionDataModel attackersEntryAreaDataModel : gameBoardDataModel
				.getRectangleDefinedAttackersEntryAreasAsRGBInImageToParse()) {
			IntegerRectangle rectangleInImageWithRGB = getRectangleInImageWithRGB(attackersEntryAreaDataModel);
			GameBoardAttackersEntryArea attackersEntryArea = new GameBoardAttackersEntryArea(gameBoard,
					rectangleInImageWithRGB, attackersEntryAreaDataModel,
					attackersEntryAreaDataModel.getAssociatedExitAreaName());
			gameBoard.addGameBoardAttackersEntryArea(attackersEntryArea);
		}
		for (GameBoardAreasByRGBImageRecognitionDataModel constructibleAreaDataModel : gameBoardDataModel
				.getRectangleDefinedConstructibleAreasAsRGBInImageToParse()) {
			IntegerRectangle rectangleInImageWithRGB = getRectangleInImageWithRGB(constructibleAreaDataModel);
			GameBoardInitiallyConstructibleMacroArea constructibleArea = new GameBoardInitiallyConstructibleMacroArea(
					gameBoard, rectangleInImageWithRGB, constructibleAreaDataModel);
			gameBoard.addGameBoardInitiallyConstructibleMacroArea(constructibleArea);
		}
		for (GameBoardAreasByRGBImageRecognitionDataModel nonPlayableAreaDataModel : gameBoardDataModel
				.getPointsDefinedNonPlayableAreasAsRGBInImageToParse()) {
			List<IntegerPoint> listOfPixelsInImageWithRGBAsPoint = getListOfPixelsInImageWithRGB(
					nonPlayableAreaDataModel);
			List<GameBoardPoint> listOfPixelsInImageWithRGBAsGameBoardPoints = gameBoard
					.getGameBoardPoints(listOfPixelsInImageWithRGBAsPoint);
			GameBoardNonPlayableArea nonPlayableArea = new GameBoardNonPlayableArea(gameBoard,
					nonPlayableAreaDataModel.getName(), listOfPixelsInImageWithRGBAsGameBoardPoints);
			gameBoard.addNonPlayableArea(nonPlayableArea);
		}
	}

	private List<IntegerPoint> getListOfPixelsInImageWithRGB(
			GameBoardAreasByRGBImageRecognitionDataModel gameBoardAreasByRGBImageRecognitionDataModel) {

		Color searchedColor = gameBoardAreasByRGBImageRecognitionDataModel.getBackgroundColorAsAwtColor();

		List<IntegerPoint> pointsInImageWithRGB = new ArrayList<>();

		File imageFile = new File(gameBoardAreasByRGBImageRecognitionDataModel.getImageToParsePath());
		BufferedImage imageBufferedImage = null;
		try {
			imageBufferedImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int imageWidth = imageBufferedImage.getWidth();
		int imageHeight = imageBufferedImage.getHeight();

		for (int x = 0; x < imageWidth; x++) {

			for (int y = 0; y < imageHeight; y++) {
				int pixelRgb = -1;
				try {
					pixelRgb = imageBufferedImage.getRGB(x, y);

				} catch (ArrayIndexOutOfBoundsException e) {
					LOGGER.fatal("x:" + x + ", y:" + y + ", " + e.getLocalizedMessage());
					continue;
				}
				Color pixelColor = new Color(pixelRgb);

				if (searchedColor.equals(pixelColor)) {
					pointsInImageWithRGB.add(new IntegerPoint(x, y));
				}
			}
		}

		return pointsInImageWithRGB;
	}

	private IntegerRectangle getRectangleInImageWithRGB(
			GameBoardAreasByRGBImageRecognitionDataModel gameBoardAreasByRGBImageRecognitionDataModel) {

		List<IntegerPoint> listOfPixelsInImageWithRGB = getListOfPixelsInImageWithRGB(
				gameBoardAreasByRGBImageRecognitionDataModel);
		IntegerRectangle rectangle = new IntegerRectangle(listOfPixelsInImageWithRGB);
		return rectangle;
	}

	public int getGameBoardTotalHeight() {
		return gameBoardFullBackgroundImageAsBufferedImage.getHeight();
	}

	public int getGameBoardTotalWidth() {
		return gameBoardFullBackgroundImageAsBufferedImage.getWidth();
	}

}
