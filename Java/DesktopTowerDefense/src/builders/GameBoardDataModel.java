package builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import common.BadLogicException;

public class GameBoardDataModel {

	private int game_board_total_width;
	private int game_board_total_height;

	private ArrayList<RectangleDataModel> attackersEntryAreas;
	private ArrayList<RectangleDataModel> attackersExitAreas;
	private ArrayList<RectangleDataModel> walls;

	public int getGame_board_total_width() {
		return game_board_total_width;
	}

	public int getGame_board_total_height() {
		return game_board_total_height;
	}

	RectangleDataModel getAttackersEntryAreaByName(String name) {
		List<RectangleDataModel> found = attackersEntryAreas.stream()
				.filter(item -> Objects.equals(item.getName(), name)).collect(Collectors.toList());
		if (found.size() != 1) {
			throw new BadLogicException("Should not find " + found.size() + " AttackersEntryArea with name" + name);
		}
		return found.get(0);
	}

	public RectangleDataModel getOneRandomEntryArea() {
		RectangleDataModel rectangleDataModel = attackersEntryAreas.get(0);
		return rectangleDataModel;
	}

	public RectangleDataModel getOneRandomExitArea() {
		RectangleDataModel rectangleDataModel = attackersExitAreas.get(0);
		return rectangleDataModel;
	}

	RectangleDataModel getAttackersExitAreaByName(String name) {
		List<RectangleDataModel> found = attackersExitAreas.stream()
				.filter(item -> Objects.equals(item.getName(), name)).collect(Collectors.toList());
		if (found.size() != 1) {
			throw new BadLogicException("Should not find " + found.size() + " AttackersExitArea with name" + name);
		}
		return found.get(0);
	}

	public ArrayList<RectangleDataModel> getWalls() {
		return walls;
	}

	public ArrayList<RectangleDataModel> getAttackersEntryAreas() {
		return attackersEntryAreas;
	}

	public ArrayList<RectangleDataModel> getAttackersExitAreas() {
		return attackersExitAreas;
	}
}
