package builders;

import builders.gameboard.FireSpeedType;

public class TowerLevelDataModel {

	private String levelNumber;
	private Integer cost;
	private Integer damage;
	private Integer range;
	private FireSpeedType fireSpeed;
	private Integer sellValue;

	public String getLevelNumber() {
		return levelNumber;
	}

	public Integer getCost() {
		return cost;
	}

	public Integer getDamage() {
		return damage;
	}

	public Integer getRange() {
		return range;
	}

	public FireSpeedType getFireSpeed() {
		return fireSpeed;
	}

	public Integer getSellValue() {
		return sellValue;
	}

}
