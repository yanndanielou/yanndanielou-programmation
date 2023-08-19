package builders.game;

import java.util.ArrayList;

public class GameDataModel {

	private String name;
	private String comment;
	private int numberOfInitialLives;
	private int numberOfInitialGold;

	private ArrayList<AttackersWaveDataModel> attackersWavesDataModel;

	public ArrayList<AttackersWaveDataModel> getAttackersWavesDataModel() {
		return attackersWavesDataModel;
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public int getNumberOfInitialLives() {
		return numberOfInitialLives;
	}

	public int getNumberOfInitialGold() {
		return numberOfInitialGold;
	}
}
