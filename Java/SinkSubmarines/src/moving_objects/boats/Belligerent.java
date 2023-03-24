package moving_objects.boats;

import java.awt.Rectangle;

import moving_objects.GameObject;

public abstract class Belligerent extends GameObject {

	public Belligerent(Rectangle surrounding_rectangle_absolute_on_complete_board) {
		super(surrounding_rectangle_absolute_on_complete_board);
	}

}
