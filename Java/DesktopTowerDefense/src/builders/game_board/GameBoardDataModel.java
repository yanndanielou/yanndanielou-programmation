package builders.game_board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import common.BadLogicException;

public class GameBoardDataModel {

	private int game_board_total_width;
	private int game_board_total_height;

	private ArrayList<RectangleDataModel> attackersEntryAreasAsRectangles;
	private ArrayList<RectangleDataModel> attackersExitAreasAsRectangles;
	private ArrayList<RectangleDataModel> wallsAsRectangles;
	private ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> pointsDefinedWallAreaAsRGBInImageToParse;
	private ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> rectangleDefinedAttackersEntryAreasAsRGBInImageToParse;
	private ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> rectangleDefinedAttackersExitAreasAsRGBInImageToParse;
	private ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> pointsDefinedNonPlayableAreasAsRGBInImageToParse;

	public int getGame_board_total_width() {
		return game_board_total_width;
	}

	public int getGame_board_total_height() {
		return game_board_total_height;
	}

	RectangleDataModel getAttackersEntryAreaByName(String name) {
		List<RectangleDataModel> found = attackersEntryAreasAsRectangles.stream()
				.filter(item -> Objects.equals(item.getName(), name)).collect(Collectors.toList());
		if (found.size() != 1) {
			throw new BadLogicException("Should not find " + found.size() + " AttackersEntryArea with name" + name);
		}
		return found.get(0);
	}

	public RectangleDataModel getOneRandomEntryArea() {
		RectangleDataModel rectangleDataModel = attackersEntryAreasAsRectangles.get(0);
		return rectangleDataModel;
	}

	public RectangleDataModel getOneRandomExitArea() {
		RectangleDataModel rectangleDataModel = attackersExitAreasAsRectangles.get(0);
		return rectangleDataModel;
	}

	RectangleDataModel getAttackersExitAreaByName(String name) {
		List<RectangleDataModel> found = attackersExitAreasAsRectangles.stream()
				.filter(item -> Objects.equals(item.getName(), name)).collect(Collectors.toList());
		if (found.size() != 1) {
			throw new BadLogicException("Should not find " + found.size() + " AttackersExitArea with name" + name);
		}
		return found.get(0);
	}

	private ArrayList<RectangleDataModel> emptyListIfNullRectangleDataModell(
			ArrayList<RectangleDataModel> initialList) {
		return initialList == null ? new ArrayList<>() : initialList;
	}

	private ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> emptyListIfNullGameBoardAreasByRGBImageRecognitionDataModel(
			ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> initialList) {
		return initialList == null ? new ArrayList<>() : initialList;
	}

	public ArrayList<RectangleDataModel> getWallsAsRectangles() {
		return emptyListIfNullRectangleDataModell(wallsAsRectangles);
	}

	public ArrayList<RectangleDataModel> getAttackersEntryAreasAsRectangles() {
		return emptyListIfNullRectangleDataModell(attackersEntryAreasAsRectangles);
	}

	public ArrayList<RectangleDataModel> getAttackersExitAreasAsRectangles() {
		return emptyListIfNullRectangleDataModell(attackersExitAreasAsRectangles);
	}

	public ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> getPointsDefinedNonPlayableAreasAsRGBInImageToParse() {
		return emptyListIfNullGameBoardAreasByRGBImageRecognitionDataModel(
				pointsDefinedNonPlayableAreasAsRGBInImageToParse);
	}

	public ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> getPointsDefinedWallAreaAsRGBInImageToParse() {
		return emptyListIfNullGameBoardAreasByRGBImageRecognitionDataModel(pointsDefinedWallAreaAsRGBInImageToParse);
	}

	public ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> getRectangleDefinedAttackersEntryAreasAsRGBInImageToParse() {
		return emptyListIfNullGameBoardAreasByRGBImageRecognitionDataModel(
				rectangleDefinedAttackersEntryAreasAsRGBInImageToParse);
	}

	public ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> getRectangleDefinedAttackersExitAreasAsRGBInImageToParse() {
		return emptyListIfNullGameBoardAreasByRGBImageRecognitionDataModel(
				rectangleDefinedAttackersExitAreasAsRGBInImageToParse);
	}

}
