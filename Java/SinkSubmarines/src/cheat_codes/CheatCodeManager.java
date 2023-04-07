package cheat_codes;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import game.Game;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.Belligerent;
import moving_objects.weapon.Weapon;

public class CheatCodeManager {

	private static CheatCodeManager instance;
	private static final Logger LOGGER = LogManager.getLogger(CheatCodeManager.class);

	private CheatCodeManager() {
	}

	public static CheatCodeManager getInstance() {
		if (instance == null) {
			instance = new CheatCodeManager();
		}
		return instance;
	}

	public boolean try_and_apply_text_cheat_code(String text_cheat_code) {
		LOGGER.info("text_cheat_code_entered:" + text_cheat_code);
		return false;
	}

	public void kill_all_submarines() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			ArrayList<Belligerent> all_submarines = game.get_all_submarines();
			all_submarines.forEach((submarine) -> submarine.impact_now());
		}
	}

	public void stop_all_submarines() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			ArrayList<Belligerent> all_submarines = game.get_all_submarines();
			all_submarines.forEach((submarine) -> submarine.stop_movement());
		}
	}

	public void kill_all_enemies_bombs() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			ArrayList<Weapon> all_submarines_bombs = game.get_all_submarines_bombs();
			all_submarines_bombs.forEach((submarine) -> submarine.impact_now());
		}
	}

	public void more_ally_bombs() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			AllyBoat ally_boat = game.getAlly_boat();
			ally_boat.setMax_number_of_living_bombs(ally_boat.getMax_number_of_living_bombs() + 1);
		}
	}

	public void one_more_life() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			game.setNumber_remaining_lives(game.getNumber_Remaining_lives() + 1);
		}
	}

	public void forbid_enemies_to_fire() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			ArrayList<Belligerent> all_submarines = game.get_all_submarines();
			all_submarines.forEach((submarine) -> submarine.forbid_to_fire());
		}
	}

}
