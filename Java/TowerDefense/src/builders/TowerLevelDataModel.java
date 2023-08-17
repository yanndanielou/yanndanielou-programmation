package builders;

import builders.gameboard.FireSpeedType;

public class TowerLevelDataModel {

	private String level_number;
	private Integer cost;
	private Integer damage;
	private Integer range;
	private FireSpeedType fire_speed;
	private Integer sell_value;

	public String getLevel_number() {
		return level_number;
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

	public FireSpeedType getFire_speed() {
		return fire_speed;
	}

	public Integer getSell_value() {
		return sell_value;
	}

}
