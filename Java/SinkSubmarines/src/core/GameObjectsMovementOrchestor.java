package core;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;
import moving_objects.GameObject;
import moving_objects.boats.Belligerent;
import moving_objects.weapon.SimpleAllyBomb;
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
		proceed_destruction_timer_decrementation();
		proceed_destroyed_objects_cleaning();
	}

	private void proceed_destruction_timer_decrementation() {
		for (GameObject gameObject : GameManager.getInstance().getGame().getGame_objects()) {
			if (gameObject.is_being_destroyed()) {
				gameObject.decrement_destruction_timer();
			}
		}
	}

	private int proceed_all_objects_movements() {
		int number_of_objects_moved = 0;
		for (GameObject gameObject : GameManager.getInstance().getGame().getGame_objects()) {
			if (!gameObject.is_being_destroyed()) {
				if (gameObject.getX_speed() != 0 || gameObject.getY_speed() != 0) {
					gameObject.proceed_movement();
					check_if_collision();
				}
			}
		}
		return number_of_objects_moved;
	}

	private void proceed_destroyed_objects_cleaning_by_type(ArrayList<? extends GameObject> objects_to_clean) {

		objects_to_clean.removeAll(
				objects_to_clean.stream().filter(item -> item.is_completely_destroyed()).collect(Collectors.toList()));

	}

	private void proceed_destroyed_objects_cleaning() {
		Game game = GameManager.getInstance().getGame();
		
		proceed_destroyed_objects_cleaning_by_type(game.getSimple_submarines());		
		proceed_destroyed_objects_cleaning_by_type(game.getYellow_submarines());
		proceed_destroyed_objects_cleaning_by_type(game.getSimple_ally_bombs());
		
	}

	private boolean check_if_collision() {
		boolean collision_detected = false;

		for (SimpleAllyBomb simpleAllyBomb : GameManager.getInstance().getGame().getSimple_ally_bombs()) {
			if (!simpleAllyBomb.is_being_destroyed()) {

				for (Belligerent subMarine : GameManager.getInstance().getGame().get_all_submarines()) {
					if (!subMarine.is_being_destroyed()) {
						if (simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board()
								.intersects(subMarine.getSurrounding_rectangle_absolute_on_complete_board())) {
							LOGGER.info("Collision detected between simple ally bomb " + simpleAllyBomb
									+ " and submarine:" + subMarine);
							simpleAllyBomb.impact_now();
							subMarine.impact_now();
						}
					}
				}
			}
		}

		return collision_detected;
	}

	@Override
	public void on_50ms_tick() {
		// proceed_ally_boat_movement();
		proceed_all_objects_movements();

	}

	@Override
	public void on_20ms_tick() {
	}

}
