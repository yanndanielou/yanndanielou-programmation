package moving_objects.boats;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameObjectsMovementOrchestor;
import moving_objects.weapon.SimpleAllyBomb;

public class GameObjectGraphicalRepresentationManager {
	private static GameObjectGraphicalRepresentationManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameObjectsMovementOrchestor.class);

	private BufferedImage ally_boat_normal_buffered_image = null;
	private File ally_boat_normal_image_file = null;
	private final String ally_boat_normal_image_path = "Images/AllyBoat.png";

	private BufferedImage simple_submarine_being_destroyed_buffered_image = null;
	private File simple_submarine_being_destroyed_image_file = null;
	private final String simple_submarine_being_destroyed_image_path = "Images/simple_submarine_destroyed.png";

	private BufferedImage simple_submarine_direction_left_buffered_image = null;
	private File simple_submarine_direction_left_image_file = null;
	private final String simple_submarine_direction_left_image_path = "Images/simple_submarine_left.png";

	private BufferedImage simple_submarine_direction_right_buffered_image = null;
	private File simple_submarine_direction_right_image_file = null;
	private final String simple_submarine_direction_right_image_path = "Images/simple_submarine_right.png";

	private BufferedImage simple_ally_bomb_in_water_buffered_image = null;
	private File simple_ally_bomb_in_water_image_file = null;
	private final String simple_ally_bomb_in_water_image_path = "Images/ally_simple_bomb_in_water.png";

	private BufferedImage simple_ally_bomb_in_the_air_buffered_image = null;
	private File simple_ally_bomb_in_the_air_image_file = null;
	private final String simple_ally_bomb_in_the_air_image_path = "Images/ally_simple_bomb_in_air.png";

	private BufferedImage simple_ally_bomb_being_destroyed_buffered_image = null;
	private File simple_ally_bomb_being_destroyed_image_file = null;
	private final String simple_ally_bomb_being_destroyed_image_path = "Images/ally_simple_bomb_destroyed.png";

	private GameObjectGraphicalRepresentationManager() {
		initialize_and_load_images();
	}

	private void initialize_ally_boat() {
		ally_boat_normal_image_file = new File(ally_boat_normal_image_path);

		try {
			ally_boat_normal_buffered_image = ImageIO.read(ally_boat_normal_image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void initialize_simple_submarines() {
		simple_submarine_being_destroyed_image_file = new File(simple_submarine_being_destroyed_image_path);
		simple_submarine_direction_left_image_file = new File(simple_submarine_direction_left_image_path);
		simple_submarine_direction_right_image_file = new File(simple_submarine_direction_right_image_path);

		try {
			simple_submarine_direction_left_buffered_image = ImageIO.read(simple_submarine_direction_left_image_file);
			simple_submarine_direction_right_buffered_image = ImageIO.read(simple_submarine_direction_right_image_file);
			simple_submarine_being_destroyed_buffered_image = ImageIO.read(simple_submarine_being_destroyed_image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void initialize_and_load_images() {
		initialize_ally_boat();
		initialize_simple_submarines();
		initialize_simple_ally_bombs();
	}

	private void initialize_simple_ally_bombs() {
		simple_ally_bomb_in_water_image_file = new File(simple_ally_bomb_in_water_image_path);
		simple_ally_bomb_in_the_air_image_file = new File(simple_ally_bomb_in_the_air_image_path);
		simple_ally_bomb_being_destroyed_image_file = new File(simple_ally_bomb_being_destroyed_image_path);

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
		if(simpleSubMarine.is_being_destroyed()) {
			return simple_submarine_being_destroyed_buffered_image;
		}
		BufferedImage submarine_image = simpleSubMarine.getX_speed() > 0
				? simple_submarine_direction_right_buffered_image
				: simple_submarine_direction_left_buffered_image;

		return submarine_image;
	}

	public BufferedImage getSimpleAllyBombImage(SimpleAllyBomb simpleAllyBomb) {
		BufferedImage image = null;
		if (simpleAllyBomb.is_being_destroyed()) {
			image = simple_ally_bomb_being_destroyed_buffered_image;
		} else if (simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY() > 0) {
			image = simple_ally_bomb_in_water_buffered_image;
		} else {
			image = simple_ally_bomb_in_water_buffered_image;
		}

		return image;
	}

}
