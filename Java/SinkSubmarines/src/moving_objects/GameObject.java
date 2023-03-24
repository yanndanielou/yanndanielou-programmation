package moving_objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import main.SinkSubmarinesMain;

public abstract class GameObject {
	private static final Logger LOGGER = LogManager.getLogger(GameObject.class);

	private Point upper_left_absolute_position_on_complete_board;
	private Rectangle surrounding_rectangle_absolute_on_complete_board;
	protected int x_speed = 0;
	private int y_speed = 0;

	protected ArrayList<GameObjectListerner> movement_listeners = new ArrayList<GameObjectListerner>();

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
		LOGGER.info(this + " set x speed:" + x_speed);
		this.x_speed = x_speed;
	}

	public int getY_speed() {
		return y_speed;
	}

	public void setY_speed(int y_speed) {
		LOGGER.info(this + " set y speed:" + y_speed);
		this.y_speed = y_speed;
	}

	public boolean proceed_movement() {
		boolean has_moved = false;

		double object_x = surrounding_rectangle_absolute_on_complete_board.getX();
		double object_right = surrounding_rectangle_absolute_on_complete_board.getMaxX();
		double object_width = surrounding_rectangle_absolute_on_complete_board.getWidth();

		if (getX_speed() < 0) {
			if (object_x < getX_speed()) {
				surrounding_rectangle_absolute_on_complete_board.setLocation(0, (int) surrounding_rectangle_absolute_on_complete_board.getY());
				left_border_of_game_board_reached();
				has_moved = true;
			} else {
				surrounding_rectangle_absolute_on_complete_board.translate((int) getX_speed(), 0);
				has_moved = true;
			}
		} else if (getX_speed() > 0) {
			if (object_right + getX_speed() > GameManager.getInstance().getGame().getGameboard().getWidth()) {

				surrounding_rectangle_absolute_on_complete_board.setLocation(
						(int) (GameManager.getInstance().getGame().getGameboard().getWidth() - object_width),(int) surrounding_rectangle_absolute_on_complete_board.getY());

				right_border_of_game_board_reached();
				has_moved = true;
			} else {
				surrounding_rectangle_absolute_on_complete_board.translate((int) getX_speed(), 0);
				has_moved = true;
			}
		}

		if (has_moved) {
			notify_movement();
		}
		return has_moved;
	}

	public void add_movement_listener(GameObjectListerner allyBoatListener) {
		movement_listeners.add(allyBoatListener);
	}

	protected abstract void right_border_of_game_board_reached();

	protected abstract void left_border_of_game_board_reached();

	public abstract void notify_movement();

}
