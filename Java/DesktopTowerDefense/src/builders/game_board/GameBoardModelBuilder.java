package builders.game_board;

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

import game_board.GameBoard;
import game_board.GameBoardAttackersEntryArea;
import game_board.GameBoardAttackersExitArea;
import game_board.GameBoardNonPlayableArea;
import game_board.GameBoardPoint;
import game_board.GameBoardWallArea;
import geometry.IntegerPoint;
import geometry.IntegerRectangle;

public class GameBoardModelBuilder {
	private static final Logger LOGGER = LogManager.getLogger(GameBoardModelBuilder.class);

	private Gson gson = new Gson();

	private GameBoardDataModel gameBoardDataModel;
	private BufferedImage gameBoardFullBackgroundImageAsBufferedImage;

	public GameBoardDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	public GameBoardModelBuilder(String game_board_data_model_json_file) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(game_board_data_model_json_file));
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
		for (RectangleDataModel attackersEntryAreaDataModel : gameBoardDataModel.getAttackersEntryAreasAsRectangles()) {
			GameBoardAttackersEntryArea attackersEntryArea = new GameBoardAttackersEntryArea(gameBoard,
					attackersEntryAreaDataModel);
			gameBoard.addGameBoardAttackersEntryArea(attackersEntryArea);
		}
		for (GameBoardAreasByRGBImageRecognitionDataModel attackersEntryAreaDataModel : gameBoardDataModel
				.getRectangleDefinedAttackersEntryAreasAsRGBInImageToParse()) {
			IntegerRectangle rectangleInImageWithRGB = getRectangleInImageWithRGB(attackersEntryAreaDataModel);
			GameBoardAttackersEntryArea attackersEntryArea = new GameBoardAttackersEntryArea(gameBoard,
					rectangleInImageWithRGB, attackersEntryAreaDataModel);
			gameBoard.addGameBoardAttackersEntryArea(attackersEntryArea);
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
				int pixel_rgb = -1;
				try {
					pixel_rgb = imageBufferedImage.getRGB(x, y);

				} catch (ArrayIndexOutOfBoundsException e) {
					LOGGER.fatal("x:" + x + ", y:" + y + ", " + e.getLocalizedMessage());
					// e.printStackTrace();
					continue;
				}
				Color pixelColor = new Color(pixel_rgb);

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
