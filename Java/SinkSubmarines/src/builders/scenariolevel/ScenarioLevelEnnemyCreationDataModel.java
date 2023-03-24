package builders.scenariolevel;

public class ScenarioLevelEnnemyCreationDataModel {

	int x;
	int altitude;
	int speed;
	int creation_delay_in_seconds;

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

}
