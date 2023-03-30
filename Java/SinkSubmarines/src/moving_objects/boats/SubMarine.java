package moving_objects.boats;

import java.awt.Rectangle;

import moving_objects.GameObjectListerner;
import time.TimeManagerListener;

public abstract class SubMarine extends Belligerent  implements TimeManagerListener  {

	public SubMarine(Rectangle surrounding_rectangle_absolute_on_complete_board, int maximum_fire_frequency_in_seconds) {
		super(surrounding_rectangle_absolute_on_complete_board, maximum_fire_frequency_in_seconds);
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
	public void on_10ms_tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_100ms_tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_second_tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_50ms_tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_20ms_tick() {
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
