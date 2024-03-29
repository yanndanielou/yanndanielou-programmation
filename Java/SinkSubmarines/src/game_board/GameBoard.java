package game_board;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;

public class GameBoard {

	private static final Logger LOGGER = LogManager.getLogger(GameBoard.class);

	private int width = 0;
	private int height = 0;

	HashMap<Integer, Integer> ocean_bed_rocks_height_per_abscissa = new HashMap<>();
	Integer sky_height;

	public GameBoard(int width, int height) {

		this.width = width;
		this.height = height;

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getOceanBedBelowRocksDepth() {
		return height - get_water_level_y();
	}

	public int get_top_of_the_rock_depth(int x) {
		Integer rocks_height = ocean_bed_rocks_height_per_abscissa.get(x);
		if (rocks_height == null) {
			LOGGER.error("Cannot compute top_of_the_rock_depth at x:" + x);
		}
		int top_of_the_rock_depth = getHeight() - get_water_level_y() - rocks_height;
		return top_of_the_rock_depth;
	}

	public boolean compute_game_areas_height(BufferedImage bufferedImage) {
		if (getWidth() != bufferedImage.getWidth()) {
			LOGGER.error(
					"Gameboard width " + getWidth() + " is different from image width " + bufferedImage.getWidth());
		}

		if (getHeight() != bufferedImage.getHeight()) {
			LOGGER.error(
					"Gameboard height " + getHeight() + " is different from image height " + bufferedImage.getHeight());
		}

		boolean sky_height_computed = compute_sky_height(bufferedImage);
		boolean rocks_height_computed = compute_rocks_height(bufferedImage);

		return sky_height_computed && rocks_height_computed;
	}

	public boolean compute_rocks_height(BufferedImage bufferedImage) {

		for (int x = 0; x < getWidth(); x++) {

			boolean beginning_of_rocks_found = false;
			for (int y = getHeight() - 1; y > 0 && !beginning_of_rocks_found; y--) {
				int pixel_rgb = -1;
				try {
					pixel_rgb = bufferedImage.getRGB(x, y);
				} catch (ArrayIndexOutOfBoundsException e) {
					LOGGER.fatal("x:" + x + ", y:" + y + ", " + e.getLocalizedMessage());
					// e.printStackTrace();
					continue;
				}
				if (pixel_rgb == HMIConstants.WATER_COLOR.getRGB()) {
					int rock_height = height - y;
					LOGGER.debug("At x:" + x + " , rocks height is " + rock_height);
					ocean_bed_rocks_height_per_abscissa.put(x, rock_height);
					beginning_of_rocks_found = true;
				}
			}
			if (!beginning_of_rocks_found) {
				return false;
			}
		}
		return true;

	}

	public boolean compute_sky_height(BufferedImage bufferedImage) {

		// only check in one absicssa as sky height is always the same
		int x = 0;

		boolean beginning_of_water_found = false;
		for (int y = 0; y < getHeight() && !beginning_of_water_found; y++) {
			int pixel_rgb = bufferedImage.getRGB(x, y);

			if (pixel_rgb == HMIConstants.WATER_COLOR.getRGB()) {
				sky_height = y;
				beginning_of_water_found = true;
			}
		}
		return beginning_of_water_found;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int get_water_level_y() {
		return sky_height;
	}

}
