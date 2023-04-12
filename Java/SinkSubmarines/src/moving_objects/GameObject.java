package moving_objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import game.Game;
import game_board.GameBoard;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.listeners.GameObjectListerner;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;

public abstract class GameObject {
	private static final Logger LOGGER = LogManager.getLogger(GameObject.class);

	@Deprecated
	private Point upper_left_absolute_position_on_complete_board;

	protected Rectangle surrounding_rectangle_absolute_on_complete_board;
	protected int x_speed = 0;
	protected int y_speed = 0;
	protected Game game;

	protected Integer current_destruction_timer_in_milliseconds = null;

	protected ArrayList<GameObjectListerner> movement_listeners = new ArrayList<GameObjectListerner>();

	private BufferedImage ally_boat_normal_buffered_image = null;
	private final String ally_boat_normal_image_path = "Images/AllyBoat.png";

	private BufferedImage simple_submarine_being_destroyed_buffered_image = null;
	private final String simple_submarine_being_destroyed_image_path = "Images/simple_submarine_destroyed.png";
	private BufferedImage simple_submarine_direction_left_buffered_image = null;
	private final String simple_submarine_direction_left_image_path = "Images/simple_submarine_left.png";
	private BufferedImage simple_submarine_direction_right_buffered_image = null;
	private final String simple_submarine_direction_right_image_path = "Images/simple_submarine_right.png";

	private BufferedImage yellow_submarine_being_destroyed_buffered_image = null;
	private final String yellow_submarine_being_destroyed_image_path = "Images/yellow_submarine_destroyed.png";
	private BufferedImage yellow_submarine_direction_left_buffered_image = null;
	private final String yellow_submarine_direction_left_image_path = "Images/yellow_submarine_left.png";
	private BufferedImage yellow_submarine_direction_right_buffered_image = null;
	private final String yellow_submarine_direction_right_image_path = "Images/yellow_submarine_right.png";

	private BufferedImage simple_ally_bomb_in_water_buffered_image = null;
	private final String simple_ally_bomb_in_water_image_path = "Images/ally_simple_bomb_in_water.png";
	private BufferedImage simple_ally_bomb_in_the_air_buffered_image = null;
	private final String simple_ally_bomb_in_the_air_image_path = "Images/ally_simple_bomb_in_air.png";
	private BufferedImage simple_ally_bomb_being_destroyed_buffered_image = null;
	private final String simple_ally_bomb_being_destroyed_image_path = "Images/ally_simple_bomb_destroyed.png";

	private BufferedImage simple_submarine_bomb_in_water_buffered_image = null;
	private final String simple_submarine_bomb_in_water_image_path = "Images/simple_submarine_bomb.png";
	private BufferedImage simple_submarine_bomb_being_destroyed_buffered_image = null;
	private final String simple_submarine_bomb_being_destroyed_image_path = "Images/simple_submarine_destroyed.png";

	private BufferedImage floatting_bomb_at_surface_buffered_image = null;
	private final String floatting_bomb_at_surface_image_path = "Images/floatting_bomb_at_surface.png";
	private BufferedImage floatting_bomb_at_surface_destroyed_buffered_image = null;
	private final String floatting_bomb_at_surface_destroyed_image_path = "Images/floatting_bomb_at_surface_destroyed.png";
	private BufferedImage floatting_bomb_in_water_buffered_image = null;
	private final String floatting_bomb_in_water_image_path = "Images/floatting_bomb_in_water.png";
	private BufferedImage floatting_bomb_in_water_destroyed_buffered_image = null;
	private final String floatting_bomb_in_water_destroyed_image_path = "Images/floatting_bomb_at_surface_destroyed.png";

	public GameObject(Rectangle surrounding_rectangle_absolute_on_complete_board, Game game) {
		this.surrounding_rectangle_absolute_on_complete_board = surrounding_rectangle_absolute_on_complete_board;
		this.game = game;

		initialize_and_load_images();

		LOGGER.info("Created: " + this + " at " + surrounding_rectangle_absolute_on_complete_board);
	}

	private void initialize_ally_boat() {
		File ally_boat_normal_image_file = new File(ally_boat_normal_image_path);

		try {
			ally_boat_normal_buffered_image = ImageIO.read(ally_boat_normal_image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void initialize_simple_submarines() {
		File simple_submarine_being_destroyed_image_file = new File(simple_submarine_being_destroyed_image_path);
		File simple_submarine_direction_left_image_file = new File(simple_submarine_direction_left_image_path);
		File simple_submarine_direction_right_image_file = new File(simple_submarine_direction_right_image_path);

		try {
			simple_submarine_direction_left_buffered_image = ImageIO.read(simple_submarine_direction_left_image_file);
			simple_submarine_direction_right_buffered_image = ImageIO.read(simple_submarine_direction_right_image_file);
			simple_submarine_being_destroyed_buffered_image = ImageIO.read(simple_submarine_being_destroyed_image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void initialize_simple_submarines_bombs() {
		File simple_submarine_bomb_in_water_image_file = new File(simple_submarine_bomb_in_water_image_path);
		File simple_submarine_bomb_being_destroyed_image_file = new File(
				simple_submarine_bomb_being_destroyed_image_path);

		try {
			simple_submarine_bomb_in_water_buffered_image = ImageIO.read(simple_submarine_bomb_in_water_image_file);
			simple_submarine_bomb_being_destroyed_buffered_image = ImageIO
					.read(simple_submarine_bomb_being_destroyed_image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void initialize_floating_submarines_bombs() {

		File floatting_bomb_at_surface_image_file = new File(floatting_bomb_at_surface_image_path);
		File floatting_bomb_at_surface_destroyed_image_file = new File(floatting_bomb_at_surface_destroyed_image_path);
		File floatting_bomb_in_water_image_file = new File(floatting_bomb_in_water_image_path);
		File floatting_bomb_in_water_destroyed_image_file = new File(floatting_bomb_in_water_destroyed_image_path);

		try {
			floatting_bomb_at_surface_buffered_image = ImageIO.read(floatting_bomb_at_surface_image_file);
			floatting_bomb_at_surface_destroyed_buffered_image = ImageIO
					.read(floatting_bomb_at_surface_destroyed_image_file);
			floatting_bomb_in_water_buffered_image = ImageIO.read(floatting_bomb_in_water_image_file);
			floatting_bomb_in_water_destroyed_buffered_image = ImageIO
					.read(floatting_bomb_in_water_destroyed_image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void initialize_yellow_submarines() {
		File yellow_submarine_being_destroyed_image_file = new File(yellow_submarine_being_destroyed_image_path);
		File yellow_submarine_direction_left_image_file = new File(yellow_submarine_direction_left_image_path);
		File yellow_submarine_direction_right_image_file = new File(yellow_submarine_direction_right_image_path);

		try {
			yellow_submarine_direction_left_buffered_image = ImageIO.read(yellow_submarine_direction_left_image_file);
			yellow_submarine_direction_right_buffered_image = ImageIO.read(yellow_submarine_direction_right_image_file);
			yellow_submarine_being_destroyed_buffered_image = ImageIO.read(yellow_submarine_being_destroyed_image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void initialize_and_load_images() {
		initialize_ally_boat();
		initialize_simple_submarines();
		initialize_yellow_submarines();
		initialize_simple_ally_bombs();
		initialize_simple_submarines_bombs();
		initialize_floating_submarines_bombs();
	}

	private void initialize_simple_ally_bombs() {
		File simple_ally_bomb_in_water_image_file = new File(simple_ally_bomb_in_water_image_path);
		File simple_ally_bomb_in_the_air_image_file = new File(simple_ally_bomb_in_the_air_image_path);
		File simple_ally_bomb_being_destroyed_image_file = new File(simple_ally_bomb_being_destroyed_image_path);

		try {
			simple_ally_bomb_in_water_buffered_image = ImageIO.read(simple_ally_bomb_in_water_image_file);
			simple_ally_bomb_in_the_air_buffered_image = ImageIO.read(simple_ally_bomb_in_the_air_image_file);
			simple_ally_bomb_being_destroyed_buffered_image = ImageIO.read(simple_ally_bomb_being_destroyed_image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public BufferedImage getAllyBoatImage(AllyBoat ally_boat) {
		return ally_boat_normal_buffered_image;
	}

	public BufferedImage getSimpleSubmarineImage(SimpleSubMarine simpleSubMarine) {
		if (simpleSubMarine.is_being_destroyed()) {
			return simple_submarine_being_destroyed_buffered_image;
		}
		BufferedImage submarine_image = simpleSubMarine.getX_speed() > 0
				? simple_submarine_direction_right_buffered_image
				: simple_submarine_direction_left_buffered_image;

		return submarine_image;
	}

	public BufferedImage getYellowSubmarineImage(YellowSubMarine yellow_submarine) {
		if (yellow_submarine.is_being_destroyed()) {
			return yellow_submarine_being_destroyed_buffered_image;
		}
		BufferedImage submarine_image = yellow_submarine.getX_speed() > 0
				? yellow_submarine_direction_right_buffered_image
				: yellow_submarine_direction_left_buffered_image;

		return submarine_image;
	}

	public BufferedImage getSimpleAllyBombImage(SimpleAllyBomb simpleAllyBomb) {
		BufferedImage image = null;

		if (simpleAllyBomb.is_being_destroyed()) {
			image = simple_ally_bomb_being_destroyed_buffered_image;
		} else if (simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY() < 0) {
			image = simple_ally_bomb_in_the_air_buffered_image;
		} else {
			image = simple_ally_bomb_in_water_buffered_image;
		}

		return image;
	}

	public BufferedImage getSimpleSubmarineBombImage(SimpleSubmarineBomb submarine_bomb) {
		BufferedImage image = null;

		if (submarine_bomb.is_being_destroyed()) {
			image = simple_submarine_bomb_being_destroyed_buffered_image;
		} else {
			image = simple_submarine_bomb_in_water_buffered_image;
		}

		return image;
	}

	public BufferedImage getFloatingSubmarineBombImage(FloatingSubmarineBomb submarine_bomb) {
		BufferedImage image = null;

		if (submarine_bomb.is_being_destroyed()) {
			if (submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getY() > 0) {
				image = floatting_bomb_in_water_destroyed_buffered_image;
			} else {
				image = floatting_bomb_at_surface_destroyed_buffered_image;
			}
		} else {
			if (submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getY() > 0) {
				image = floatting_bomb_in_water_buffered_image;
			} else {
				image = floatting_bomb_at_surface_buffered_image;
			}
		}

		return image;
	}

	public boolean is_being_destroyed() {
		return current_destruction_timer_in_milliseconds != null;
	}

	public abstract void impact_now();

	@Deprecated
	public Point getUpper_left_absolute_position_on_complete_board() {
		return upper_left_absolute_position_on_complete_board;
	}

	@Deprecated
	public void setUpper_left_absolute_position_on_complete_board(
			Point upper_left_absolute_position_on_complete_board) {
		this.upper_left_absolute_position_on_complete_board = upper_left_absolute_position_on_complete_board;
	}

	public int get_highest_point_altitude() {
		return get_highest_point_depth() * -1;
	}

	public int get_highest_point_depth() {
		return (int) surrounding_rectangle_absolute_on_complete_board.getY() - game.getGameboard().get_water_level_y();
	}

	public int get_lowest_point_depth() {
		return (int) surrounding_rectangle_absolute_on_complete_board.getMaxY()
				- game.getGameboard().get_water_level_y();
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
		int game_board_width = GameManager.getInstance().getGame().getGameboard().getWidth();

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
			if (object_right + getX_speed() > game_board_width) {

				surrounding_rectangle_absolute_on_complete_board.setLocation((int) (game_board_width - object_width),
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

	public boolean proceed_vertical_movement() {
		boolean has_moved = false;

		if (getY_speed() < 0) {

			surrounding_rectangle_absolute_on_complete_board.translate(0, getY_speed());
			has_moved = true;
			if (surrounding_rectangle_absolute_on_complete_board.getY() <= game.getGameboard().get_water_level_y()) {
				top_of_object_reaches_surface();
			}

		} else if (getY_speed() > 0) {

			GameBoard gameboard = getGame().getGameboard();
			surrounding_rectangle_absolute_on_complete_board.translate(0, getY_speed());

			// FIXME: this checks only if one of the two extremities reach the rock: but
			// rocks can also be reached at middle of object
			int lowest_point_depth = get_lowest_point_depth();
			if (lowest_point_depth >= gameboard
					.get_top_of_the_rock_depth((int) (surrounding_rectangle_absolute_on_complete_board.getX()))
					|| lowest_point_depth >= gameboard.get_top_of_the_rock_depth(
							(int) (surrounding_rectangle_absolute_on_complete_board.getMaxX()))) {
				rocks_reached();
			}
			if (lowest_point_depth >= gameboard.getOceanBedBelowRocksDepth()) {
				ocean_bed_reached();
			}

			has_moved = true;
		}
		return has_moved;
	}

	protected abstract void rocks_reached();

	protected abstract void ocean_bed_reached();

	protected abstract void top_of_object_reaches_surface();

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

	public abstract void notify_movement();

	public abstract void notify_end_of_destruction_and_clean();

	public boolean is_completely_destroyed() {
		return is_being_destroyed() && current_destruction_timer_in_milliseconds <= 0;
	}

	public void decrement_destruction_timer(int number_of_milliseconds) {
		if (current_destruction_timer_in_milliseconds != null) {
			current_destruction_timer_in_milliseconds -= number_of_milliseconds;
			current_destruction_timer_in_milliseconds = Math.max(0, current_destruction_timer_in_milliseconds);
		}

	}

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

}
