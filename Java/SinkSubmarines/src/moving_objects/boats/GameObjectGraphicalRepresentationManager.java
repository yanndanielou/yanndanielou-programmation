package moving_objects.boats;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;

public class GameObjectGraphicalRepresentationManager {
	private static GameObjectGraphicalRepresentationManager instance;
	//private static final Logger LOGGER = LogManager.getLogger(GameObjectsMovementOrchestor.class);

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

	private GameObjectGraphicalRepresentationManager() {
		initialize_and_load_images();
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

	public static GameObjectGraphicalRepresentationManager getInstance() {
		if (instance == null) {
			instance = new GameObjectGraphicalRepresentationManager();
		}
		return instance;
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

	public Image getYellowSubmarineImage(YellowSubMarine yellow_submarine) {
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

	public Image getSimpleSubmarineBombImage(SimpleSubmarineBomb submarine_bomb) {
		BufferedImage image = null;

		if (submarine_bomb.is_being_destroyed()) {
			image = simple_submarine_bomb_being_destroyed_buffered_image;
		} else {
			image = simple_submarine_bomb_in_water_buffered_image;
		}

		return image;
	}

	public Image getFloatingSubmarineBombImage(FloatingSubmarineBomb submarine_bomb) {
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

}
