package builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import common.BadLogicException;

public class GameBoardDataModel {

	private int game_board_attacker_entry_absolute_x;
	private int game_board_attacker_entry_absolute_y;
	private int game_board_total_width;
	private int game_board_total_height;

	private ArrayList<RectangleDataModel> attackers_entry_areas;
	private ArrayList<RectangleDataModel> attackers_exit_areas;

	public int getGame_board_attacker_entry_absolute_x() {
		return game_board_attacker_entry_absolute_x;
	}

	public int getGame_board_attacker_entry_absolute_y() {
		return game_board_attacker_entry_absolute_y;
	}

	public int getGame_board_total_width() {
		return game_board_total_width;
	}

	public int getGame_board_total_height() {
		return game_board_total_height;
	}

	public ArrayList<RectangleDataModel> getAttackers_entry_areas() {
		return attackers_entry_areas;
	}

	RectangleDataModel getAttackersEntryAreaByName(String name) {
		List<RectangleDataModel> found = attackers_entry_areas.stream()
				.filter(item -> Objects.equals(item.getName(), name)).collect(Collectors.toList());
		if (found.size() != 1) {
			throw new BadLogicException("Should not find " + found.size() + " AttackersEntryArea with name" + name);
		}
		return found.get(0);
	}

	public RectangleDataModel getOneRandomEntryArea() {
		RectangleDataModel rectangleDataModel = attackers_entry_areas.get(0);
		return rectangleDataModel;
	}

	public RectangleDataModel getOneRandomExitArea() {
		RectangleDataModel rectangleDataModel = attackers_exit_areas.get(0);
		return rectangleDataModel;
	}

	RectangleDataModel getAttackersExitAreaByName(String name) {
		List<RectangleDataModel> found = attackers_exit_areas.stream()
				.filter(item -> Objects.equals(item.getName(), name)).collect(Collectors.toList());
		if (found.size() != 1) {
			throw new BadLogicException("Should not find " + found.size() + " AttackersExitArea with name" + name);
		}
		return found.get(0);
	}

}
