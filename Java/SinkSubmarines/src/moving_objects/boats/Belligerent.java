package moving_objects.boats;

import java.awt.Rectangle;

import moving_objects.GameObject;

public abstract class Belligerent extends GameObject {

	final int maximum_fire_frequency_in_milliseconds;
	
	
	public Belligerent(Rectangle surrounding_rectangle_absolute_on_complete_board, int maximum_fire_frequency_in_seconds) {
		super(surrounding_rectangle_absolute_on_complete_board);
		this.maximum_fire_frequency_in_milliseconds = maximum_fire_frequency_in_seconds;
	}


	public int getMaximum_fire_frequency_in_milliseconds() {
		return maximum_fire_frequency_in_milliseconds;
		
	}

}
