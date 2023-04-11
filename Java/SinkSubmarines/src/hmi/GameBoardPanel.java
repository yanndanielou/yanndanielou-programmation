package hmi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import builders.gameboard.GameBoardAreaDataModel;
import builders.gameboard.GameBoardDataModel;
import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelWaveDataModel;
import constants.HMIConstants;
import core.GameManager;
import game.Game;
import game.GameListener;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.SubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.listeners.GameObjectListerner;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;

public class GameBoardPanel extends JLayeredPane implements GameListener, GameObjectListerner {

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

	private final int TOP_PANEL_ELEMENTS_Y = 30;
	private final int TOP_PANEL_ELEMENTS_HEIGHT = 50;

	private enum LAYERS {
		UNVISIBLE, BACKGROUND_IMAGE, ROCKS, BELLIGERENTS, BOMBS, LABELS, UNDER_LABELS;
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

		setSize(complete_game_board_as_icon.getIconWidth(), complete_game_board_as_icon.getIconHeight());

		setLayout(null);

		setSize(getWidth(), getHeight());

		display_gameboard_background_image("Images/entire_gameboard.png");
/*
		current_scenario_level_label = new JLabel("LEVEL:");
		current_scenario_level_label.setSize(100, TOP_PANEL_ELEMENTS_HEIGHT);
		current_scenario_level_label.setLocation(10,
				TOP_PANEL_ELEMENTS_Y - current_scenario_level_label.getHeight() / 2);
		current_scenario_level_label.setForeground(Color.yellow);
		current_scenario_level_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(current_scenario_level_label, LAYERS.LABELS);

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
//		next_ally_bomb_horizontal_speed_as_label.setBackground(Color.red);
//		next_ally_bomb_horizontal_speed_as_label.setForeground(Color.red);
		// next_ally_bomb_horizontal_speed_no_force_icon.paintIcon(next_ally_bomb_horizontal_speed_as_label,
		// getGraphics(), window_width, window_width)
		add(next_ally_bomb_horizontal_speed_as_label, LAYERS.LABELS);

		next_ally_bomb_horizontal_speed_full_force_only_red_content_as_icon = new ImageIcon(
				"Images/next_ally_bomb_horizontal_speed_full_force_only_red_content.png");
		next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label = new JLabel(
				next_ally_bomb_horizontal_speed_full_force_only_red_content_as_icon);
		next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label.setLocation(
				next_ally_bomb_horizontal_speed_as_label.getX() + 1,
				next_ally_bomb_horizontal_speed_as_label.getY() + 1);

		add(next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label, LAYERS.UNDER_LABELS);

		//

		remaining_ally_bombs_icon_as_icon = new ImageIcon("Images/remaining_ally_bombs_icon.png");
		remaining_ally_bombs_icon_as_label = new JLabel(remaining_ally_bombs_icon_as_icon);
		remaining_ally_bombs_icon_as_label.setSize(50, remaining_ally_bombs_icon_as_icon.getIconHeight());
		remaining_ally_bombs_icon_as_label
				.setLocation(
						next_ally_bomb_horizontal_speed_as_label.getX()
								+ next_ally_bomb_horizontal_speed_as_label.getWidth() + 10,
						TOP_PANEL_ELEMENTS_Y - remaining_ally_bombs_icon_as_label.getHeight() / 2);
		add(remaining_ally_bombs_icon_as_label, 1);

		remaining_ally_bombs_label = new JLabel("X");
		remaining_ally_bombs_label.setSize(30, TOP_PANEL_ELEMENTS_HEIGHT);
		remaining_ally_bombs_label.setLocation(
				remaining_ally_bombs_icon_as_label.getX() + remaining_ally_bombs_icon_as_label.getWidth() + 5,
				TOP_PANEL_ELEMENTS_Y - remaining_ally_bombs_label.getHeight() / 2);
		remaining_ally_bombs_label.setForeground(Color.yellow);
		remaining_ally_bombs_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(remaining_ally_bombs_label, LAYERS.LABELS);

		score_label = new JLabel("SCORE");
		score_label.setSize(150, TOP_PANEL_ELEMENTS_HEIGHT);
		score_label.setLocation(remaining_ally_bombs_label.getX() + remaining_ally_bombs_label.getWidth() + 50,
				TOP_PANEL_ELEMENTS_Y - score_label.getHeight() / 2);
		score_label.setForeground(Color.yellow);
		score_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(score_label, LAYERS.LABELS);
		// setBounds(0, 0, this.getSize().width, this.getSize().height);

		update_next_ally_bomb_horizontal_speed_label();
		*/

	}

	private void display_gameboard_background_image(String background_image_path) {
		gameboard_background_image_as_icon = new ImageIcon(background_image_path);

		gameboard_background_image_as_label = new JLabel(remaining_ally_bombs_icon_as_icon);
		gameboard_background_image_as_label.setSize(gameboard_background_image_as_icon.getIconWidth(),
				gameboard_background_image_as_icon.getIconHeight());
		gameboard_background_image_as_label.setLocation(0, 0);
		add(gameboard_background_image_as_label, LAYERS.BACKGROUND_IMAGE);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void on_next_ally_bomb_horizontal_speed_changed(Game game, int next_ally_bomb_horizontal_speed) {
		update_next_ally_bomb_horizontal_speed_label();
	}

	@Override
	public void on_ally_boat_moved(AllyBoat allyBoat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_submarine_moved(SimpleSubMarine simpleSubMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_submarine_notify_end_of_destroy_and_clean(SubMarine subMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_ally_bomb_moved(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_weapon_destruction(Weapon weapon) {

		update_remaining_ally_bombs_label(weapon.getGame());

	}

	@Override
	public void on_simple_submarine_bomb_moved(SimpleSubmarineBomb simpleSubmarineBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_floating_bomb_moved(FloatingSubmarineBomb floatingSubmarineBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_yellow_submarine_end_of_destroy_and_clean(SubMarine subMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_yellow_submarine_moved(YellowSubMarine yellowSubMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_listen_to_simple_ally_bomb(SimpleAllyBomb simpleAllyBomb) {
		update_remaining_ally_bombs_label(simpleAllyBomb.getGame());
	}

	@Override
	public void on_score_changed(Game game, int score) {
		update_score_label(game);

	}

	@Override
	public void on_simple_ally_bomb_end_of_destruction_and_clean(SimpleAllyBomb simpleAllyBomb) {
		update_remaining_ally_bombs_label(simpleAllyBomb.getGame());
	}

	@Override
	public void on_simple_ally_bomb_begin_of_destruction(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	public BufferedImage getComplete_game_board_as_buffered_image() {
		return complete_game_board_as_buffered_image;
	}

}
