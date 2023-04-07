package builders.scenariolevel;

public class ScenarioLevelEnnemyCreationDataModel {

	private int x;
	private int altitude;
	private int speed;
	private int creation_delay_in_seconds;
	private int maximum_fire_frequency_in_milliseconds;
	private SubmarineFireStrategyType fire_strategy_type;
	private int ammunition_y_speed;
	private int maximum_number_of_alive_ammunitions_allowed;
	private int score_prize_money_on_destruction;

	public int getX() {
		return x;
	}

	public int getAltitude() {
		return altitude;
	}

	public int getDepth() {
		return altitude * -1;
	}

	public int getSpeed() {
		return speed;
	}

	public int getCreation_delay_in_seconds() {
		return creation_delay_in_seconds;
	}

	public int getMaximum_fire_frequency_in_milliseconds() {
		return maximum_fire_frequency_in_milliseconds;
	}

	public SubmarineFireStrategyType getFire_strategy_type() {
		return fire_strategy_type;
	}

	public int getAmmunition_y_speed() {
		return ammunition_y_speed;
	}

	public int getMaximum_number_of_alive_ammunitions_allowed() {
		return maximum_number_of_alive_ammunitions_allowed;
	}

	public int getScore_prize_money_on_destruction() {
		return score_prize_money_on_destruction;
	}

}
