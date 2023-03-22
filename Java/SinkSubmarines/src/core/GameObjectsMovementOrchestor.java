package core;

import java.util.ArrayList;

import moving_objects.GameObject;
import time.TimeManager;
import time.TimeManagerListener;

public class GameObjectsMovementOrchestor implements TimeManagerListener {
	private static GameObjectsMovementOrchestor instance;

	private GameObjectsMovementOrchestor() {
		TimeManager.getInstance().add_listener(this);
	}

	public static GameObjectsMovementOrchestor getInstance() {
		if (instance == null) {
			instance = new GameObjectsMovementOrchestor();
		}
		return instance;
	}

	@Override
	public void on_10ms_tick() {

	}

	@Override
	public void on_100ms_tick() {

	}

	@Override
	public void on_second_tick() {
	}

	@Override
	public void on_50ms_tick() {
		for (GameObject gameObject : GameManager.getInstance().getGame().getGame_objects()) {
			if (gameObject.getX_speed() != 0 || gameObject.getY_speed() != 0) {
				gameObject.getSurrounding_rectangle_absolute_on_complete_board().translate(gameObject.getX_speed(),
						gameObject.getY_speed());
				gameObject.notify_movement();
				// gameObject.getUpper_left_absolute_position_on_complete_board().translate(gameObject.getX_speed(),gameObject.getY_speed());
			}
		}
	}

	@Override
	public void on_20ms_tick() {
		// TODO Auto-generated method stub

	}

}
