package core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import moving_objects.AllyBoat;
import moving_objects.GameObject;
import time.TimeManager;
import time.TimeManagerListener;

public class GameObjectsMovementOrchestor implements TimeManagerListener {
	private static GameObjectsMovementOrchestor instance;
	private static final Logger LOGGER = LogManager.getLogger(GameObjectsMovementOrchestor.class);

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

	private boolean proceed_ally_boat_movement() {

		AllyBoat ally_boat = GameManager.getInstance().getGame().getAlly_boat();

		return true;
	}

	private int proceed_all_objects_movements() {
		int number_of_objects_moved = 0;
		for (GameObject gameObject : GameManager.getInstance().getGame().getGame_objects()) {
			if (gameObject.getX_speed() != 0 || gameObject.getY_speed() != 0) {
				gameObject.proceed_movement();
			}
		}
		return number_of_objects_moved;
	}

	@Override
	public void on_50ms_tick() {
		// proceed_ally_boat_movement();
		proceed_all_objects_movements();

	}

	@Override
	public void on_20ms_tick() {
		// TODO Auto-generated method stub

	}

}
