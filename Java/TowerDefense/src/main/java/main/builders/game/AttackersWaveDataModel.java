package main.builders.game;

import main.game.AttackersEntryAreaStrategy;

public class AttackersWaveDataModel {
	private String attackersType;
	private int numberOfAttackers;
	private String attackersEntryAreasAsComaSeparatedList;

	private AttackersEntryAreaStrategy attackersEntryAreaStrategy;
	private int attackersEvolutionLevel;

	public String getAttackersType() {
		return attackersType;
	}

	public int getNumberOfAttackers() {
		return numberOfAttackers;
	}

	public String getAttackersEntryAreasAsComaSeparatedList() {
		return attackersEntryAreasAsComaSeparatedList;
	}

	public AttackersEntryAreaStrategy getAttackersEntryAreaStrategy() {
		return attackersEntryAreaStrategy;
	}

	public int getAttackersEvolutionLevel() {
		return attackersEvolutionLevel;
	}
}
