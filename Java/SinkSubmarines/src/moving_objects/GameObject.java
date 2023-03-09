package moving_objects;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class GameObject {

	private Point upper_left_absolute_position_on_complete_board;
	private Rectangle surrounding_rectangle_absolute_on_complete_board;
	private int x_speed = 0;
	private int y_speed = 0;


	public GameObject() {
		// TODO Auto-generated constructor stub
	}

}
