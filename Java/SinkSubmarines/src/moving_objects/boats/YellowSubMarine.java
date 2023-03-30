package moving_objects.boats;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardDataModel;
import builders.genericobjects.GenericObjectDataModel;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import moving_objects.GameObjectListerner;

public class YellowSubMarine extends Belligerent {
	private static final Logger LOGGER = LogManager.getLogger(YellowSubMarine.class);

	public YellowSubMarine(ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel,
			GenericObjectDataModel simple_submarine_data_model, GameBoardDataModel gameBoardDataModel) {
		super(new Rectangle(scenarioLevelEnnemyCreationDataModel.getX(),
				scenarioLevelEnnemyCreationDataModel.getDepth(), simple_submarine_data_model.getWidth(),
				simple_submarine_data_model.getHeight()));
		setX_speed(scenarioLevelEnnemyCreationDataModel.getSpeed());
	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner allyBoatListener : movement_listeners) {
			allyBoatListener.on_simple_submarine_moved();
		}
	}

	@Override
	protected void right_border_of_game_board_reached() {
		setX_speed(getX_speed() * -1);
	}

	@Override
	protected void left_border_of_game_board_reached() {
		setX_speed(getX_speed() * -1);
	}

	@Override
	protected void ocean_bed_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void water_surface_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact_now() {
		this.current_destruction_timer_in_seconds = 5;
	}

}
