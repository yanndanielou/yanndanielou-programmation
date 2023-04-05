package hmi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import builders.gameboard.GameBoardAreaDataModel;
import builders.gameboard.GameBoardDataModel;
import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelWaveDataModel;
import core.GameManager;
import game.Game;
import game.GameListener;

public class TopPanel extends JPanel implements GameListener {

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

	private JLayeredPane layeredPane;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898026190790239124L;

	public TopPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel,
			JPanel pannel_above) {
		GameBoardAreaDataModel top_area_data_model = gameBoardDataModel.getTop_area_data_model();
		setBackground(top_area_data_model.getBackground_color_as_awt_color());
		setSize(gameBoardDataModel.getWidth(), top_area_data_model.getHeight());
		parentContainer.add(this);

		setLayout(null);

		layeredPane = new JLayeredPane();
		add(layeredPane);
		layeredPane.setSize(getWidth(), getHeight());

		current_scenario_level_label = new JLabel("LEVEL:");
		current_scenario_level_label.setSize(100, (int) (getHeight() * 0.8));
		current_scenario_level_label.setLocation(10, getHeight() / 2 - current_scenario_level_label.getHeight() / 2);
		current_scenario_level_label.setForeground(Color.yellow);
		current_scenario_level_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		layeredPane.add(current_scenario_level_label, 1);

		character_sailor_icon = new ImageIcon("Images/character_baby_sailor.png");
		Image img = character_sailor_icon.getImage();
		Image character_sailor_icon_scalled_as_image = img.getScaledInstance(20, (int) (getHeight() * 0.8), Image.SCALE_SMOOTH);
		character_sailor_icon_scalled = new ImageIcon(character_sailor_icon_scalled_as_image);

		ImageIcon next_ally_bomb_horizontal_speed_no_force_icon = new ImageIcon(
				"Images/next_ally_bomb_horizontal_speed_no_force_icon.png");
		next_ally_bomb_horizontal_speed_as_label = new JLabel(next_ally_bomb_horizontal_speed_no_force_icon);
		next_ally_bomb_horizontal_speed_as_label.setSize(next_ally_bomb_horizontal_speed_no_force_icon.getIconWidth(),
				next_ally_bomb_horizontal_speed_no_force_icon.getIconHeight());
		next_ally_bomb_horizontal_speed_as_label.setLocation(
				getWidth() / 2 - next_ally_bomb_horizontal_speed_as_label.getWidth() / 2,
				getHeight() / 2 - next_ally_bomb_horizontal_speed_as_label.getHeight() / 2);
//		next_ally_bomb_horizontal_speed_as_label.setBackground(Color.red);
//		next_ally_bomb_horizontal_speed_as_label.setForeground(Color.red);
		// next_ally_bomb_horizontal_speed_no_force_icon.paintIcon(next_ally_bomb_horizontal_speed_as_label,
		// getGraphics(), window_width, window_width)
		layeredPane.add(next_ally_bomb_horizontal_speed_as_label, 1);

		next_ally_bomb_horizontal_speed_full_force_only_red_content_as_icon = new ImageIcon(
				"Images/next_ally_bomb_horizontal_speed_full_force_only_red_content.png");
		next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label = new JLabel(
				next_ally_bomb_horizontal_speed_full_force_only_red_content_as_icon);
		next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label.setLocation(
				next_ally_bomb_horizontal_speed_as_label.getX() + 1,
				next_ally_bomb_horizontal_speed_as_label.getY() + 1);
		// getWidth() / 2 -
		// next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label.getWidth()
		// / 2,
		// getHeight() / 2 -
		// next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label.getHeight()
		// / 2);
		layeredPane.add(next_ally_bomb_horizontal_speed_full_force_only_red_content_as_label, 2);

		//

		remaining_ally_bombs_icon_as_icon = new ImageIcon("Images/remaining_ally_bombs_icon.png");
		remaining_ally_bombs_icon_as_label = new JLabel(remaining_ally_bombs_icon_as_icon);
		remaining_ally_bombs_icon_as_label.setSize(50, remaining_ally_bombs_icon_as_icon.getIconHeight());
		remaining_ally_bombs_icon_as_label
				.setLocation(
						next_ally_bomb_horizontal_speed_as_label.getX()
								+ next_ally_bomb_horizontal_speed_as_label.getWidth() + 10,
						getHeight() / 2 - remaining_ally_bombs_icon_as_label.getHeight() / 2);
		layeredPane.add(remaining_ally_bombs_icon_as_label, 1);

		remaining_ally_bombs_label = new JLabel("X");
		remaining_ally_bombs_label.setSize(10, (int) (getHeight() * 0.8));
		remaining_ally_bombs_label.setLocation(
				remaining_ally_bombs_icon_as_label.getX() + remaining_ally_bombs_icon_as_label.getWidth() + 5,
				getHeight() / 2 - remaining_ally_bombs_label.getHeight() / 2);
		remaining_ally_bombs_label.setForeground(Color.yellow);
		remaining_ally_bombs_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		layeredPane.add(remaining_ally_bombs_label, 1);

		score_label = new JLabel("SCORE");
		score_label.setSize(150, (int) (getHeight() * 0.8));
		score_label.setLocation(remaining_ally_bombs_label.getX() + remaining_ally_bombs_label.getWidth() + 50,
				getHeight() / 2 - score_label.getHeight() / 2);
		score_label.setForeground(Color.yellow);
		score_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		layeredPane.add(score_label, 1);
		// setBounds(0, 0, this.getSize().width, this.getSize().height);

		update_next_ally_bomb_horizontal_speed_label();

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();

			ScenarioLevelDataModel current_scenario_level_data_model = game.getCurrent_scenario_level_data_model();

			if (current_scenario_level_data_model != null) {
				current_scenario_level_label
						.setText("LEVEL " + current_scenario_level_data_model.getScenario_level_number());
			}
		}
	}

	@Override
	public void on_game_paused(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_number_of_remaining_lives_changed(Game game, int remaining_lives) {
		update_number_of_remaining_lives();
	}

	private void update_number_of_remaining_lives() {
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			int remaining_lives = game.getRemaining_lives();

			Component object_at_left = current_scenario_level_label;

			while (character_sailor_icons_one_per_remaining_life_as_label.size() < remaining_lives) {

				if (!character_sailor_icons_one_per_remaining_life_as_label.isEmpty()) {
					object_at_left = character_sailor_icons_one_per_remaining_life_as_label
							.get(character_sailor_icons_one_per_remaining_life_as_label.size() - 1);
				}
				int right_of_object_at_left = (int) object_at_left.getBounds().getMaxX();
				JLabel character_sailor_icon_for_one_life_as_label = new JLabel(character_sailor_icon_scalled);
				character_sailor_icons_one_per_remaining_life_as_label.add(character_sailor_icon_for_one_life_as_label);
				character_sailor_icon_for_one_life_as_label.setSize(30, (int) (getHeight() * 0.8));
				character_sailor_icon_for_one_life_as_label.setLocation(right_of_object_at_left + 10,
						getHeight() / 2 - character_sailor_icon_for_one_life_as_label.getHeight() / 2);
				layeredPane.add(character_sailor_icon_for_one_life_as_label);
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

	@Override
	public void on_game_resumed(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_listen_to_game(Game game) {
		update_current_scenario_level(game);
		update_number_of_remaining_lives();
	}

	@Override
	public void on_game_cancelled(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_new_scenario_level(Game game, ScenarioLevelDataModel scenario_level_data_model) {
		update_current_scenario_level(game);
	}

	@Override
	public void on_new_scenario_level_wave(Game game, ScenarioLevelWaveDataModel scenario_level_wave) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_next_ally_bomb_horizontal_speed_changed(Game game, int next_ally_bomb_horizontal_speed) {
		update_next_ally_bomb_horizontal_speed_label();
	}

}
