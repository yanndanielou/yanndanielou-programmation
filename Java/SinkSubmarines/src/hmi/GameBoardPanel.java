package hmi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelWaveDataModel;
import core.GameManager;
import game.Game;
import game.GameListener;
import moving_objects.GameObject;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.SubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.listeners.GameObjectListerner;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;

//FIXME: try JLayeredPane instead
public class GameBoardPanel extends JLayeredPane implements GameListener, GameObjectListerner {
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPanel.class);

	private JLabel current_scenario_level_label;

	private ImageIcon character_sailor_icon;
	private ImageIcon character_sailor_icon_scalled;
	private ArrayList<JLabel> character_sailor_icons_one_per_remaining_life_as_label = new ArrayList<>();

	private JLabel next_ally_bomb_horizontal_speed_as_label;
	private ImageIcon next_ally_bomb_horizontal_speed_full_force_only_red_content_as_icon;
	private JLabel next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label;

	private ImageIcon remaining_ally_bombs_icon_as_icon;
	private JLabel remaining_ally_bombs_icon_as_label;
	private JLabel remaining_ally_bombs_label;

	private JLabel score_label;

	private ImageIcon gameboard_background_image_as_icon;
	private JLabel gameboard_background_image_as_label;

	private ImageIcon complete_game_board_as_icon;
	private BufferedImage complete_game_board_as_buffered_image;
	private File complete_game_board_as_file;

	private final int TOP_PANEL_ELEMENTS_Y = 10;
	private final int TOP_PANEL_ELEMENTS_HEIGHT = 50;

	private JLabel ally_boat_as_label;

	private HashMap<GameObject, JLabel> game_object_to_its_jlabel_graphical_representation_map = new HashMap<>();
	/*
	 * @Deprecated private enum LAYERS_ORDERED_FROM_BACK_TO_TOP { UNVISIBLE,
	 * BACKGROUND_IMAGE, ROCKS, BELLIGERENTS, BOMBS, UNDER_LABELS, LABELS; }
	 */

	private enum LAYERS_ORDERED_FROM_TOP_TO_BACK {
		LABELS, UNDER_LABELS, BOMBS, BELLIGERENTS, ROCKS, BACKGROUND_IMAGE, UNVISIBLE;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898026190790239124L;

	public GameBoardPanel(JFrame parentFrame, String complete_game_board_png_file_path) {
		complete_game_board_as_icon = new ImageIcon(complete_game_board_png_file_path);
		complete_game_board_as_file = new File(complete_game_board_png_file_path);

		try {
			complete_game_board_as_buffered_image = ImageIO.read(complete_game_board_as_file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initialize_display() {

		setSize(complete_game_board_as_icon.getIconWidth(), complete_game_board_as_icon.getIconHeight());

		setLayout(null);

		// Create the menu bar.
		JMenuBar menuBar = new JMenuBar();

		// Build the first menu.
		JMenu menu = new JMenu("Game");
		menuBar.add(menu);

		// FIXME: go up
		display_gameboard_background_image("Images/entire_gameboard.png");

		current_scenario_level_label = new JLabel("LEVEL:");
		current_scenario_level_label.setSize(100, TOP_PANEL_ELEMENTS_HEIGHT);
		current_scenario_level_label.setLocation(10,
				TOP_PANEL_ELEMENTS_Y - current_scenario_level_label.getHeight() / 2);
		current_scenario_level_label.setForeground(Color.yellow);
		current_scenario_level_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(current_scenario_level_label, LAYERS_ORDERED_FROM_TOP_TO_BACK.LABELS.ordinal());

		character_sailor_icon = new ImageIcon("Images/character_baby_sailor.png");
		Image img = character_sailor_icon.getImage();
		Image character_sailor_icon_scalled_as_image = img.getScaledInstance(20, TOP_PANEL_ELEMENTS_HEIGHT,
				Image.SCALE_SMOOTH);
		character_sailor_icon_scalled = new ImageIcon(character_sailor_icon_scalled_as_image);

		ImageIcon next_ally_bomb_horizontal_speed_no_force_icon = new ImageIcon(
				"Images/next_ally_bomb_horizontal_speed_no_force_icon.png");
		next_ally_bomb_horizontal_speed_as_label = new JLabel(next_ally_bomb_horizontal_speed_no_force_icon);
		next_ally_bomb_horizontal_speed_as_label.setSize(next_ally_bomb_horizontal_speed_no_force_icon.getIconWidth(),
				next_ally_bomb_horizontal_speed_no_force_icon.getIconHeight());
		next_ally_bomb_horizontal_speed_as_label.setLocation(
				getWidth() / 2 - next_ally_bomb_horizontal_speed_as_label.getWidth() / 2,
				TOP_PANEL_ELEMENTS_Y - next_ally_bomb_horizontal_speed_as_label.getHeight() / 2);
		add(next_ally_bomb_horizontal_speed_as_label, LAYERS_ORDERED_FROM_TOP_TO_BACK.LABELS.ordinal());

		next_ally_bomb_horizontal_speed_full_force_only_red_content_as_icon = new ImageIcon(
				"Images/next_ally_bomb_horizontal_speed_full_force_only_red_content.png");
		next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label = new JLabel(
				next_ally_bomb_horizontal_speed_full_force_only_red_content_as_icon);
		next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label.setLocation(
				next_ally_bomb_horizontal_speed_as_label.getX() + 1,
				next_ally_bomb_horizontal_speed_as_label.getY() + 1);

		add(next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label,
				LAYERS_ORDERED_FROM_TOP_TO_BACK.UNDER_LABELS.ordinal());

		//

		remaining_ally_bombs_icon_as_icon = new ImageIcon("Images/remaining_ally_bombs_icon.png");
		remaining_ally_bombs_icon_as_label = new JLabel(remaining_ally_bombs_icon_as_icon);
		remaining_ally_bombs_icon_as_label.setSize(50, remaining_ally_bombs_icon_as_icon.getIconHeight());
		remaining_ally_bombs_icon_as_label
				.setLocation(
						next_ally_bomb_horizontal_speed_as_label.getX()
								+ next_ally_bomb_horizontal_speed_as_label.getWidth() + 10,
						TOP_PANEL_ELEMENTS_Y - remaining_ally_bombs_icon_as_label.getHeight() / 2);
		add(remaining_ally_bombs_icon_as_label, LAYERS_ORDERED_FROM_TOP_TO_BACK.LABELS.ordinal());

		remaining_ally_bombs_label = new JLabel("X");
		remaining_ally_bombs_label.setSize(30, TOP_PANEL_ELEMENTS_HEIGHT);
		remaining_ally_bombs_label.setLocation(
				remaining_ally_bombs_icon_as_label.getX() + remaining_ally_bombs_icon_as_label.getWidth() + 5,
				TOP_PANEL_ELEMENTS_Y - remaining_ally_bombs_label.getHeight() / 2);
		remaining_ally_bombs_label.setForeground(Color.yellow);
		remaining_ally_bombs_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(remaining_ally_bombs_label, LAYERS_ORDERED_FROM_TOP_TO_BACK.LABELS.ordinal());

		score_label = new JLabel("SCORE");
		score_label.setSize(150, TOP_PANEL_ELEMENTS_HEIGHT);
		score_label.setLocation(remaining_ally_bombs_label.getX() + remaining_ally_bombs_label.getWidth() + 50,
				TOP_PANEL_ELEMENTS_Y - score_label.getHeight() / 2);
		score_label.setForeground(Color.yellow);
		score_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(score_label, LAYERS_ORDERED_FROM_TOP_TO_BACK.LABELS.ordinal());

		// setBounds(0, 0, this.getSize().width, this.getSize().height);

		update_next_ally_bomb_horizontal_speed_label();

	}

	private void display_gameboard_background_image(String background_image_path) {
		gameboard_background_image_as_icon = new ImageIcon(background_image_path);

		gameboard_background_image_as_label = new JLabel(gameboard_background_image_as_icon);
		gameboard_background_image_as_label.setSize((int) (gameboard_background_image_as_icon.getIconWidth()),
				(int) (gameboard_background_image_as_icon.getIconHeight()));
		gameboard_background_image_as_label.setLocation(0, 0);
		add(gameboard_background_image_as_label, LAYERS_ORDERED_FROM_TOP_TO_BACK.BACKGROUND_IMAGE.ordinal());
		// add(gameboard_background_image_as_label);
	}

	@Override
	public void on_number_of_remaining_lives_changed(Game game, int remaining_lives) {
		update_number_of_remaining_lives();
	}

	private void update_number_of_remaining_lives() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			int remaining_lives = game.getNumber_Remaining_lives();

			Component object_at_left = current_scenario_level_label;

			while (character_sailor_icons_one_per_remaining_life_as_label.size() < remaining_lives) {

				if (!character_sailor_icons_one_per_remaining_life_as_label.isEmpty()) {
					object_at_left = character_sailor_icons_one_per_remaining_life_as_label
							.get(character_sailor_icons_one_per_remaining_life_as_label.size() - 1);
				}
				int right_of_object_at_left = (int) object_at_left.getBounds().getMaxX();
				JLabel character_sailor_icon_for_one_life_as_label = new JLabel(character_sailor_icon_scalled);
				character_sailor_icons_one_per_remaining_life_as_label.add(character_sailor_icon_for_one_life_as_label);
				character_sailor_icon_for_one_life_as_label.setSize(30, TOP_PANEL_ELEMENTS_HEIGHT);
				int horizontal_space_between_icons = 2;
				character_sailor_icon_for_one_life_as_label.setLocation(
						right_of_object_at_left + horizontal_space_between_icons,
						TOP_PANEL_ELEMENTS_Y - character_sailor_icon_for_one_life_as_label.getHeight() / 2);
				add(character_sailor_icon_for_one_life_as_label);
			}
		}
	}

	private void update_next_ally_bomb_horizontal_speed_label() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();

			int next_ally_bomb_horizontal_speed_relative_percentage = game
					.getNext_ally_bomb_horizontal_speed_relative_percentage();

			next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label.setSize(
					next_ally_bomb_horizontal_speed_full_force_only_red_content_as_icon.getIconWidth()
							* next_ally_bomb_horizontal_speed_relative_percentage / 100,
					next_ally_bomb_horizontal_speed_full_force_only_red_content_as_icon.getIconHeight());

		}

	}

	private void update_current_scenario_level(Game game) {
		if (game == null || game.getCurrent_scenario_level_data_model() == null) {
			current_scenario_level_label.setText("LEVEL:");
		} else {
			ScenarioLevelDataModel current_scenario_level_data_model = game.getCurrent_scenario_level_data_model();

			current_scenario_level_label
					.setText("LEVEL:" + current_scenario_level_data_model.getScenario_level_number());
		}
	}

	private void update_remaining_ally_bombs_label(Game game) {
		int remaining_number_of_living_bombs_allowed_as_int = game.getAlly_boat()
				.get_remaining_number_of_living_bombs_allowed();
		String remaining_number_of_living_bombs_allowed_as_string = Integer
				.toString(remaining_number_of_living_bombs_allowed_as_int);
		remaining_ally_bombs_label.setText(remaining_number_of_living_bombs_allowed_as_string);
	}

	private void update_score_label(Game game) {
		score_label.setText("SCORE:" + Integer.toString(game.getScore()));
	}

	@Override
	public void on_listen_to_game(Game game) {
		update_current_scenario_level(game);
		update_number_of_remaining_lives();
		update_remaining_ally_bombs_label(game);
	}

	@Override
	public void on_new_scenario_level(Game game, ScenarioLevelDataModel scenario_level_data_model) {
		update_current_scenario_level(game);
		update_remaining_ally_bombs_label(game);
	}

	@Override
	public void on_new_scenario_level_wave(Game game, ScenarioLevelWaveDataModel scenario_level_wave) {
	}

	@Override
	public void on_next_ally_bomb_horizontal_speed_changed(Game game, int next_ally_bomb_horizontal_speed) {
		update_next_ally_bomb_horizontal_speed_label();
	}

	@Override
	public void on_ally_boat_moved(AllyBoat allyBoat) {
		move_jlabel_graphical_representation_according_to_game_object_location(allyBoat);
		update_jlabel_graphical_representation_icon(allyBoat);
	}

	@Override
	public void on_simple_submarine_moved(SimpleSubMarine simpleSubMarine) {
		move_jlabel_graphical_representation_according_to_game_object_location(simpleSubMarine);
		update_jlabel_graphical_representation_icon(simpleSubMarine);
	}

	@Override
	public void on_simple_ally_bomb_moved(SimpleAllyBomb simpleAllyBomb) {
		move_jlabel_graphical_representation_according_to_game_object_location(simpleAllyBomb);
		update_jlabel_graphical_representation_icon(simpleAllyBomb);
	}

	@Override
	public void on_weapon_end_of_destruction_and_clean(Weapon weapon) {
		update_remaining_ally_bombs_label(weapon.getGame());
		remove_jlabel_graphical_representation_for_game_object(weapon);
	}

	@Override
	public void on_simple_submarine_bomb_moved(SimpleSubmarineBomb simpleSubmarineBomb) {
		move_jlabel_graphical_representation_according_to_game_object_location(simpleSubmarineBomb);
		update_jlabel_graphical_representation_icon(simpleSubmarineBomb);
	}

	@Override
	public void on_floating_submarine_bomb_moved(FloatingSubmarineBomb floatingSubmarineBomb) {
		move_jlabel_graphical_representation_according_to_game_object_location(floatingSubmarineBomb);
		update_jlabel_graphical_representation_icon(floatingSubmarineBomb);
	}

	@Override
	public void on_yellow_submarine_end_of_destruction_and_clean(YellowSubMarine yellowSubMarine) {
		remove_jlabel_graphical_representation_for_game_object(yellowSubMarine);
		update_jlabel_graphical_representation_icon(yellowSubMarine);
	}

	@Override
	public void on_yellow_submarine_moved(YellowSubMarine yellowSubMarine) {
		move_jlabel_graphical_representation_according_to_game_object_location(yellowSubMarine);
		update_jlabel_graphical_representation_icon(yellowSubMarine);
	}

	@Override
	public void on_listen_to_simple_ally_bomb(SimpleAllyBomb simpleAllyBomb) {
		update_remaining_ally_bombs_label(simpleAllyBomb.getGame());

		create_jlabel_graphical_representation_for_game_object(simpleAllyBomb, LAYERS_ORDERED_FROM_TOP_TO_BACK.BOMBS);
	}

	@Override
	public void on_score_changed(Game game, int score) {
		update_score_label(game);

	}

	@Override
	public void on_simple_ally_bomb_end_of_destruction_and_clean(SimpleAllyBomb simpleAllyBomb) {
		update_remaining_ally_bombs_label(simpleAllyBomb.getGame());
		remove_jlabel_graphical_representation_for_game_object(simpleAllyBomb);
	}

	public BufferedImage getComplete_game_board_as_buffered_image() {
		return complete_game_board_as_buffered_image;
	}

	@Override
	public void on_ally_boat_end_of_destruction_and_clean(AllyBoat allyBoat) {
		remove_jlabel_graphical_representation_for_game_object(allyBoat);
	}

	@Override
	public void on_ally_boat_beginning_of_destruction(AllyBoat allyBoat) {
		update_jlabel_graphical_representation_icon(allyBoat);
	}

	@Override
	public void on_submarine_end_of_destruction_and_clean(SubMarine subMarine) {
		remove_jlabel_graphical_representation_for_game_object(subMarine);
	}

	@Override
	public void on_simple_ally_bomb_beginning_of_destruction(SimpleAllyBomb simpleAllyBomb) {
		update_jlabel_graphical_representation_icon(simpleAllyBomb);
	}

	@Override
	public void on_listen_to_ally_boat(AllyBoat allyBoat) {
		ally_boat_as_label = create_jlabel_graphical_representation_for_game_object(allyBoat,
				LAYERS_ORDERED_FROM_TOP_TO_BACK.BELLIGERENTS);

	}

	private void move_jlabel_graphical_representation_according_to_game_object_location(GameObject game_object) {

		JLabel jLabel_graphical_representation = game_object_to_its_jlabel_graphical_representation_map
				.get(game_object);
		if (jLabel_graphical_representation != null) {
			jLabel_graphical_representation
					.setLocation(game_object.getSurrounding_rectangle_absolute_on_complete_board().getLocation());
		} else {
			LOGGER.error("Could not find jlabel graphical representation for:" + game_object);
		}
	}

	private void remove_jlabel_graphical_representation_for_game_object(GameObject game_object) {
		JLabel jLabel_graphical_representation = game_object_to_its_jlabel_graphical_representation_map
				.get(game_object);
		if (jLabel_graphical_representation != null) {
			jLabel_graphical_representation.setVisible(false);
			remove(jLabel_graphical_representation);
			game_object_to_its_jlabel_graphical_representation_map.remove(game_object);
		} else {
			LOGGER.error("Graphical representation jlabel did not exists for:" + game_object);
		}
	}

	private boolean update_jlabel_graphical_representation_icon(GameObject game_object) {

		JLabel jLabel_graphical_representation = game_object_to_its_jlabel_graphical_representation_map
				.get(game_object);
		if (jLabel_graphical_representation != null) {

			Icon previous_representation_icon = jLabel_graphical_representation.getIcon();
			ImageIcon new_representation_icon = game_object.get_graphical_representation_as_icon();
			if (previous_representation_icon != new_representation_icon) {
				jLabel_graphical_representation.setIcon(new_representation_icon);
				return true;
			}

		} else {
			LOGGER.error("Graphical representation jlabel does not exists for:" + game_object);
		}

		return false;
	}

	private JLabel create_jlabel_graphical_representation_for_game_object(GameObject game_object,
			LAYERS_ORDERED_FROM_TOP_TO_BACK layer) {

		JLabel jLabel_graphical_representation = game_object_to_its_jlabel_graphical_representation_map
				.get(game_object);
		if (jLabel_graphical_representation == null) {

			ImageIcon graphical_representation_as_icon = game_object.get_graphical_representation_as_icon();
			jLabel_graphical_representation = new JLabel(graphical_representation_as_icon);

			jLabel_graphical_representation.setSize(graphical_representation_as_icon.getIconWidth(),
					graphical_representation_as_icon.getIconHeight());

			jLabel_graphical_representation
					.setLocation(game_object.getSurrounding_rectangle_absolute_on_complete_board().getLocation());
			add(jLabel_graphical_representation, layer.ordinal());

			game_object_to_its_jlabel_graphical_representation_map.put(game_object, jLabel_graphical_representation);
		} else {
			LOGGER.error("Graphical representation jlabel already exists for:" + game_object);
		}

		return jLabel_graphical_representation;
	}

	@Override
	public void on_listen_to_submarine(SubMarine subMarine) {
		create_jlabel_graphical_representation_for_game_object(subMarine, LAYERS_ORDERED_FROM_TOP_TO_BACK.BELLIGERENTS);
	}

	@Override
	public void on_simple_submarine_bomb_end_of_destruction_and_clean(SimpleSubmarineBomb simpleSubmarineBomb) {
		remove_jlabel_graphical_representation_for_game_object(simpleSubmarineBomb);
	}

	@Override
	public void on_simple_submarine_bomb_beginning_of_destruction(SimpleSubmarineBomb simpleSubmarineBomb) {
		update_jlabel_graphical_representation_icon(simpleSubmarineBomb);
	}

	@Override
	public void on_listen_to_simple_submarine_bomb(SimpleSubmarineBomb simpleSubmarineBomb) {
		create_jlabel_graphical_representation_for_game_object(simpleSubmarineBomb,
				LAYERS_ORDERED_FROM_TOP_TO_BACK.BOMBS);

	}

	@Override
	public void on_listen_to_floating_submarine_bomb(FloatingSubmarineBomb floatingSubmarineBomb) {
		create_jlabel_graphical_representation_for_game_object(floatingSubmarineBomb,
				LAYERS_ORDERED_FROM_TOP_TO_BACK.BOMBS);
	}

	@Override
	public void on_submarine_beginning_of_destruction(SubMarine subMarine) {
		update_jlabel_graphical_representation_icon(subMarine);
	}

	@Override
	public void on_weapon_beginning_of_destruction(Weapon weapon) {
		update_jlabel_graphical_representation_icon(weapon);
	}

}
