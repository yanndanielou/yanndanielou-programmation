package main.builders.gameboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;

import main.common.exceptions.BadLogicException;

public class GameBoardDataModel {

	private String gameBoardFullBackgroundImagePath;
	private String commandPanelBackgroundImagePath;

	private DimensionDataModel predefinedConstructionLocationsDimensions;
	private ArrayList<GameBoardAttackersEntryAreasAsRectangleDataModel> attackersEntryAreasAsRectangles;
	private ArrayList<RectangleDataModel> attackersExitAreasAsRectangles;
	private ArrayList<RectangleDataModel> wallsAsRectangles;
	private ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> pointsDefinedWallAreaAsRGBInImageToParse;
	private ArrayList<GameBoardAttackersEntryAreasByRGBImageRecognitionDataModel> rectangleDefinedAttackersEntryAreasAsRGBInImageToParse;
	private ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> rectangleDefinedAttackersExitAreasAsRGBInImageToParse;
	private ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> pointsDefinedNonPlayableAreasAsRGBInImageToParse;
	private ArrayList<GameBoardAreasByRGBImageRecognitionDataModel> rectangleDefinedConstructibleAreasAsRGBInImageToParse;

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

	public List<RectangleDataModel> getWallsAsRectangles() {
		return ListUtils.emptyIfNull(wallsAsRectangles);
	}

	public List<GameBoardAttackersEntryAreasAsRectangleDataModel> getAttackersEntryAreasAsRectangles() {
		return ListUtils.emptyIfNull(attackersEntryAreasAsRectangles);
	}

	public List<RectangleDataModel> getAttackersExitAreasAsRectangles() {
		return ListUtils.emptyIfNull(attackersExitAreasAsRectangles);
	}

	public List<GameBoardAreasByRGBImageRecognitionDataModel> getPointsDefinedNonPlayableAreasAsRGBInImageToParse() {
		return ListUtils.emptyIfNull(pointsDefinedNonPlayableAreasAsRGBInImageToParse);
	}

	public static <T> List<T> emptyIfNull(final List<T> list) {
		return list == null ? Collections.<T>emptyList() : list;
	}

	public List<GameBoardAreasByRGBImageRecognitionDataModel> getPointsDefinedWallAreaAsRGBInImageToParse() {
		return ListUtils.emptyIfNull(pointsDefinedWallAreaAsRGBInImageToParse);
	}

	public List<GameBoardAttackersEntryAreasByRGBImageRecognitionDataModel> getRectangleDefinedAttackersEntryAreasAsRGBInImageToParse() {
		return ListUtils.emptyIfNull(rectangleDefinedAttackersEntryAreasAsRGBInImageToParse);
	}

	public List<GameBoardAreasByRGBImageRecognitionDataModel> getRectangleDefinedAttackersExitAreasAsRGBInImageToParse() {
		return ListUtils.emptyIfNull(rectangleDefinedAttackersExitAreasAsRGBInImageToParse);
	}

	public List<GameBoardAreasByRGBImageRecognitionDataModel> getRectangleDefinedConstructibleAreasAsRGBInImageToParse() {
		return ListUtils.emptyIfNull(rectangleDefinedConstructibleAreasAsRGBInImageToParse);
	}

	public String getGameBoardFullBackgroundImagePath() {
		return gameBoardFullBackgroundImagePath;
	}

	public DimensionDataModel getPredefinedConstructionLocationsDimensions() {
		return predefinedConstructionLocationsDimensions;
	}

	public String getCommandPanelBackgroundImagePath() {
		return commandPanelBackgroundImagePath;
	}
}
