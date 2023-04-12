package core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;
import moving_objects.GameObject;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.Belligerent;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.Weapon;
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
	public void on_20ms_tick() {
	}

	@Override
	public void on_50ms_tick() {
		proceed_all_objects_movements();
	}

	@Override
	public void on_100ms_tick() {
		proceed_destruction_timer_decrementation(100);
		proceed_destroyed_objects_cleaning();
	}

	@Override
	public void on_second_tick() {
	}

	private void proceed_destruction_timer_decrementation(int number_of_milliseconds) {
		for (GameObject gameObject : GameManager.getInstance().getGame().getGame_objects()) {
			if (gameObject.is_being_destroyed()) {
				gameObject.decrement_destruction_timer(number_of_milliseconds);
			}
		}
	}

	private int proceed_all_objects_movements() {
		int number_of_objects_moved = 0;
		for (GameObject gameObject : GameManager.getInstance().getGame().getGame_objects()) {
			if (!gameObject.is_being_destroyed()) {
				if (gameObject.is_in_movement()) {
					gameObject.proceed_movement();
					check_if_collision();
				}
			}
		}
		return number_of_objects_moved;
	}

	private void proceed_destroyed_objects_cleaning_by_type(ArrayList<? extends GameObject> objects_to_clean) {
		List<? extends GameObject> objects_completely_destroyed_to_clean = objects_to_clean.stream()
				.filter(item -> item.is_completely_destroyed()).collect(Collectors.toList());

		for (GameObject object_completely_destroyed_to_clean : objects_completely_destroyed_to_clean) {
			object_completely_destroyed_to_clean.end_of_destroy_and_clean();
		}

		objects_to_clean.removeAll(objects_completely_destroyed_to_clean);

	}

	private void proceed_destroyed_objects_cleaning() {
		Game game = GameManager.getInstance().getGame();

		proceed_destroyed_objects_cleaning_by_type(game.getSimple_submarines());
		proceed_destroyed_objects_cleaning_by_type(game.getYellow_submarines());
		proceed_destroyed_objects_cleaning_by_type(game.getSimple_ally_bombs());
		proceed_destroyed_objects_cleaning_by_type(game.getSimple_submarine_bombs());
		proceed_destroyed_objects_cleaning_by_type(game.getFloating_submarine_bombs());

	}

	private boolean check_if_collision() {
		boolean collision_detected = false;
		Game game = GameManager.getInstance().getGame();
		AllyBoat ally_boat = game.getAlly_boat();

		for (Weapon submarine_bomb : game.get_all_submarines_bombs()) {
			if (!submarine_bomb.is_being_destroyed()) {
				if (submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board()
						.intersects(ally_boat.getSurrounding_rectangle_absolute_on_complete_board())) {
					LOGGER.info("Collision detected between ally boat " + ally_boat + "("
							+ ally_boat.getSurrounding_rectangle_absolute_on_complete_board() + ")"
							+ " and submarine bomb:" + submarine_bomb + "("
							+ submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board() + ")");
					if (!ally_boat.is_being_destroyed()) {
						ally_boat.impact_now();
					}
					submarine_bomb.impact_now();
				}
			}

		}

		for (SimpleAllyBomb simpleAllyBomb : new ArrayList<SimpleAllyBomb>(game.getSimple_ally_bombs())) {
			if (!simpleAllyBomb.is_being_destroyed()) {

				for (Belligerent subMarine : game.get_all_submarines()) {
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

				for (FloatingSubmarineBomb floating_submarine_bomb : game.getFloating_submarine_bombs()) {
					if (!floating_submarine_bomb.is_being_destroyed()) {
						if (simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().intersects(
								floating_submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board())) {
							LOGGER.info("Collision detected between simple ally bomb " + simpleAllyBomb
									+ " and floating submarine bomb:" + floating_submarine_bomb);
							simpleAllyBomb.impact_now();
							floating_submarine_bomb.impact_now();
						}
					}
				}
			}
		}

		return collision_detected;
	}

	@Override
	public void on_pause() {

	}

}
