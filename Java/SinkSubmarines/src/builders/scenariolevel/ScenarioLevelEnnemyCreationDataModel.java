package builders.scenariolevel;

public class ScenarioLevelEnnemyCreationDataModel {

	int x;
	int altitude;
	int speed;
	int creation_delay_in_seconds;
	int maximum_fire_frequency_in_seconds;
	SubmarineFireStrategyType fire_strategy_type;
	int ammunition_y_speed;
	int maximum_number_of_alive_ammunitions_allowed;

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

	public int getMaximum_fire_frequency_in_seconds() {
		return maximum_fire_frequency_in_seconds;
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

}
