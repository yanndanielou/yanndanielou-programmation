package moving_objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class GameObject {

	private Point upper_left_absolute_position_on_complete_board;
	private Rectangle surrounding_rectangle_absolute_on_complete_board;
	protected int x_speed = 0;
	private int y_speed = 0;

	public GameObject(Rectangle surrounding_rectangle_absolute_on_complete_board) {
		this.surrounding_rectangle_absolute_on_complete_board = surrounding_rectangle_absolute_on_complete_board;
	}

	public Point getUpper_left_absolute_position_on_complete_board() {
		return upper_left_absolute_position_on_complete_board;
	}

	public void setUpper_left_absolute_position_on_complete_board(
			Point upper_left_absolute_position_on_complete_board) {
		this.upper_left_absolute_position_on_complete_board = upper_left_absolute_position_on_complete_board;
	}

	public Rectangle getSurrounding_rectangle_absolute_on_complete_board() {
		return surrounding_rectangle_absolute_on_complete_board;
	}

	public void setSurrounding_rectangle_absolute_on_complete_board(
			Rectangle surrounding_rectangle_absolute_on_complete_board) {
		this.surrounding_rectangle_absolute_on_complete_board = surrounding_rectangle_absolute_on_complete_board;
	}

	public int getX_speed() {
		return x_speed;
	}

	public void setX_speed(int x_speed) {
		this.x_speed = x_speed;
	}

	public int getY_speed() {
		return y_speed;
	}

	public void setY_speed(int y_speed) {
		this.y_speed = y_speed;
	}

	public abstract void notify_movement();

}
