package core;

import java.util.ArrayList;

import moving_objects.AllyBoat;
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

	private boolean proceed_ally_boat_movement() {

		AllyBoat ally_boat = GameManager.getInstance().getGame().getAlly_boat();
		if (ally_boat.getX_speed() < 0) {
			int new_x = Math.max(0, (int) ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getX()
					- ally_boat.getX_speed());
			if (new_x == 0) {
				ally_boat.setX_speed(0);
				ally_boat.getSurrounding_rectangle_absolute_on_complete_board()
						.translate((int) -ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getX(), 0);
			} else {
				ally_boat.getSurrounding_rectangle_absolute_on_complete_board().translate((int) ally_boat.getX_speed(),
						0);
			}
		} else if (ally_boat.getX_speed() > 0) {
			if (ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getMaxX()
					+ ally_boat.getX_speed() > GameManager.getInstance().getGame().getGameboard().getWidth()) {
				ally_boat.setX_speed(0);
				ally_boat.getSurrounding_rectangle_absolute_on_complete_board()
						.setLocation(
								(int) (GameManager.getInstance().getGame().getGameboard().getWidth()
										- ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getWidth()),
								0);
			} else {
				ally_boat.getSurrounding_rectangle_absolute_on_complete_board().translate((int) ally_boat.getX_speed(),
						0);
			}
		}
		
		ally_boat.notify_movement();

		return true;
	}

	@Override
	public void on_50ms_tick() {
		proceed_ally_boat_movement();

		/*
		 * for (GameObject gameObject :
		 * GameManager.getInstance().getGame().getGame_objects()) { if
		 * (gameObject.getX_speed() != 0 || gameObject.getY_speed() != 0) {
		 * gameObject.getSurrounding_rectangle_absolute_on_complete_board().translate(
		 * gameObject.getX_speed(), gameObject.getY_speed());
		 * gameObject.notify_movement(); //
		 * gameObject.getUpper_left_absolute_position_on_complete_board().translate(
		 * gameObject.getX_speed(),gameObject.getY_speed()); } }
		 */
	}

	@Override
	public void on_20ms_tick() {
		// TODO Auto-generated method stub

	}

}
