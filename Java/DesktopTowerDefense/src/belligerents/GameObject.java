package belligerents;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.listeners.GameObjectListerner;
import belligerents.weapon.SimpleTowerBomb;
import belligerents.weapon.Weapon;
import core.GameManager;
import game.Game;
import game_board.GameBoard;

public abstract class GameObject {
	private static final Logger LOGGER = LogManager.getLogger(GameObject.class);

	protected Rectangle surrounding_rectangle_absolute_on_complete_board;
	protected Integer x_speed;
	protected Integer y_speed;
	protected Game game;

	protected boolean being_destroyed = false;

	protected ArrayList<GameObjectListerner> movement_listeners = new ArrayList<GameObjectListerner>();

	private BufferedImage simple_tower_normal_buffered_image = null;
	private final String simple_tower_normal_image_path = "Images/Simple_tower.png";

	private BufferedImage simple_tower_bomb_buffered_image = null;
	private final String simple_tower_bomb_image_path = "Images/ally_simple_bomb_in_water.png";

	private BufferedImage normal_attacker_buffered_image = null;
	private final String normal_attacker_image_path = "Images/Attacker_normal_going_right.png";

	public GameObject(Rectangle surrounding_rectangle_absolute_on_complete_board, Game game) {
		this.surrounding_rectangle_absolute_on_complete_board = surrounding_rectangle_absolute_on_complete_board;
		this.game = game;

		initialize_and_load_images();

		LOGGER.info("Created: " + this + " at " + surrounding_rectangle_absolute_on_complete_board);
	}

	private BufferedImage getBufferedImageFromFilePath(String image_path) {
		File image_file = new File(image_path);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bufferedImage;
	}

	private void initialize_towers() {
		simple_tower_normal_buffered_image = getBufferedImageFromFilePath(simple_tower_normal_image_path);
	}

	private void initialize_and_load_images() {
		initialize_towers();
		initialize_bombs();
		initialize_attackers();
	}

	private void initialize_attackers() {
		normal_attacker_buffered_image = getBufferedImageFromFilePath(normal_attacker_image_path);
	}

	private void initialize_bombs() {
		simple_tower_bomb_buffered_image = getBufferedImageFromFilePath(simple_tower_bomb_image_path);
	}

	public BufferedImage getSimpleTowerNormalImage(Tower simpleTower) {
		return simple_tower_normal_buffered_image;
	}

	public BufferedImage getSimpleTowerBombImage(SimpleTowerBomb simpleTowerBomb) {
		BufferedImage image = null;

		image = simple_tower_bomb_buffered_image;

		return image;
	}

	public boolean is_being_destroyed() {
		return being_destroyed;
	}

	public abstract void impact_now(Weapon weapon);

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

	public boolean is_in_movement() {
		return x_speed != 0 || y_speed != 0;
	}

	public void setX_speed(int x_speed) {
		LOGGER.debug(this + " set x speed:" + x_speed);
		this.x_speed = x_speed;
	}

	public int getY_speed() {
		return y_speed;
	}

	public void setY_speed(int y_speed) {
		LOGGER.info(this + " set y speed:" + y_speed);
		this.y_speed = y_speed;
	}

	public boolean proceed_horizontal_movement() {
		boolean has_moved = false;

		double object_x = surrounding_rectangle_absolute_on_complete_board.getX();
		double object_right = surrounding_rectangle_absolute_on_complete_board.getMaxX();
		double object_width = surrounding_rectangle_absolute_on_complete_board.getWidth();
		int game_board_total_width = GameManager.getInstance().getGame().getGameBoard().getTotalWidth();

		if (getX_speed() < 0) {
			if (object_x < getX_speed()) {
				surrounding_rectangle_absolute_on_complete_board.setLocation(0,
						(int) surrounding_rectangle_absolute_on_complete_board.getY());
				left_border_of_game_board_reached();
				has_moved = true;
			} else {
				surrounding_rectangle_absolute_on_complete_board.translate((int) getX_speed(), 0);
				has_moved = true;
			}
		} else if (getX_speed() > 0) {
			if (object_right + getX_speed() > game_board_total_width) {

				surrounding_rectangle_absolute_on_complete_board.setLocation(
						(int) (game_board_total_width - object_width),
						(int) surrounding_rectangle_absolute_on_complete_board.getY());

				right_border_of_game_board_reached();
				has_moved = true;
			} else {
				surrounding_rectangle_absolute_on_complete_board.translate((int) getX_speed(), 0);
				has_moved = true;
			}
		}
		return has_moved;
	}

	public int get_lowest_point_y() {
		return (int) surrounding_rectangle_absolute_on_complete_board.getMaxY();
	}

	public boolean proceed_vertical_movement() {
		boolean has_moved = false;

		if (getY_speed() < 0) {

			surrounding_rectangle_absolute_on_complete_board.translate(0, getY_speed());
			has_moved = true;

		} else if (getY_speed() > 0) {

			GameBoard gameboard = getGame().getGameBoard();
			surrounding_rectangle_absolute_on_complete_board.translate(0, getY_speed());

			// FIXME: this checks only if one of the two extremities reach the rock: but
			// rocks can also be reached at middle of object
			int lowest_point_y = get_lowest_point_y();
			if (lowest_point_y >= gameboard.getTotalHeight()) {
				down_border_of_game_board_reached();
			}

			has_moved = true;
		}
		return has_moved;
	}

	public abstract BufferedImage get_graphical_representation_as_buffered_image();

	// FIXME: remove BufferedImage and directly create icon from path
	public ImageIcon get_graphical_representation_as_icon() {
		BufferedImage graphical_representation_as_buffered_image = get_graphical_representation_as_buffered_image();
		ImageIcon imageIcon = new ImageIcon(graphical_representation_as_buffered_image);
		return imageIcon;
	}

	public boolean proceed_movement() {
		boolean has_moved = false;

		has_moved = proceed_horizontal_movement() || proceed_vertical_movement();

		if (has_moved) {
			notify_movement();
		}
		return has_moved;
	}

	public void add_movement_listener(GameObjectListerner game_object_listener) {
		movement_listeners.add(game_object_listener);
	}

	protected abstract void right_border_of_game_board_reached();

	protected abstract void left_border_of_game_board_reached();

	protected abstract void down_border_of_game_board_reached();

	public abstract void notify_movement();

	public abstract void notify_end_of_destruction_and_clean();

	public void end_of_destroy_and_clean() {
		notify_end_of_destruction_and_clean();
	}

	public void stop_horizontal_movement() {
		x_speed = 0;
	}

	public void stop_vertical_movement() {
		y_speed = 0;
	}

	public void stop_movement() {
		stop_horizontal_movement();
		stop_vertical_movement();
	}

	public Game getGame() {
		return game;
	}

	public BufferedImage getNormal_attacker_buffered_image(NormalAttacker normalAttacker) {
		return normal_attacker_buffered_image;
	}

}
