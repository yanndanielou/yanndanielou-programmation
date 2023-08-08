package builders.game_board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;

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

	public List<RectangleDataModel> getWallsAsRectangles() {
		return ListUtils.emptyIfNull(wallsAsRectangles);
	}

	public List<RectangleDataModel> getAttackersEntryAreasAsRectangles() {
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

	public List<GameBoardAreasByRGBImageRecognitionDataModel> getRectangleDefinedAttackersEntryAreasAsRGBInImageToParse() {
		return ListUtils.emptyIfNull(rectangleDefinedAttackersEntryAreasAsRGBInImageToParse);
	}

	public List<GameBoardAreasByRGBImageRecognitionDataModel> getRectangleDefinedAttackersExitAreasAsRGBInImageToParse() {
		return ListUtils.emptyIfNull(rectangleDefinedAttackersExitAreasAsRGBInImageToParse);
	}

}
