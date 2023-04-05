package hmi;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import builders.gameboard.GameBoardAreaDataModel;
import builders.gameboard.GameBoardDataModel;
import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelWaveDataModel;
import core.GameManager;
import core.ScenarioLevelExecutor;
import game.DifficultyLevel;
import game.Game;
import game.GameListener;

public class TopPanel extends JPanel implements GameListener {

	private JLabel current_scenario_level_label;

	private ImageIcon character_sailor_icon;
	private ArrayList<JLabel> character_sailor_icons_as_label = new ArrayList<>();

	private JLabel next_ally_bomb_horizontal_speed_as_label;

	private ImageIcon remaining_ally_bombs_icon_as_icon;
	private JLabel remaining_ally_bombs_icon_as_label;

	private JLabel remaining_ally_bombs_label;
	private JLabel score_label;

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

		current_scenario_level_label = new JLabel("LEVEL:");
		current_scenario_level_label.setSize(100, (int) (getHeight() * 0.8));
		current_scenario_level_label.setLocation(10, getHeight() / 2 - current_scenario_level_label.getHeight() / 2);
		current_scenario_level_label.setForeground(Color.yellow);
		current_scenario_level_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(current_scenario_level_label);

		character_sailor_icon = new ImageIcon("Images/character_baby_sailor.png");

		ImageIcon next_ally_bomb_horizontal_speed_no_force_icon = new ImageIcon(
				"Images/next_ally_bomb_horizontal_speed_no_force_icon.png");
		next_ally_bomb_horizontal_speed_as_label = new JLabel(next_ally_bomb_horizontal_speed_no_force_icon);
		next_ally_bomb_horizontal_speed_as_label.setSize(next_ally_bomb_horizontal_speed_no_force_icon.getIconWidth(),
				next_ally_bomb_horizontal_speed_no_force_icon.getIconHeight());
		next_ally_bomb_horizontal_speed_as_label.setLocation(
				getWidth() / 2 - next_ally_bomb_horizontal_speed_as_label.getWidth() / 2,
				getHeight() / 2 - next_ally_bomb_horizontal_speed_as_label.getHeight() / 2);
		next_ally_bomb_horizontal_speed_as_label.setBackground(Color.red);
		next_ally_bomb_horizontal_speed_as_label.setForeground(Color.red);
		//next_ally_bomb_horizontal_speed_no_force_icon.paintIcon(next_ally_bomb_horizontal_speed_as_label, getGraphics(), window_width, window_width)
		add(next_ally_bomb_horizontal_speed_as_label);

		remaining_ally_bombs_icon_as_icon = new ImageIcon("Images/remaining_ally_bombs_icon.png");
		remaining_ally_bombs_icon_as_label = new JLabel(remaining_ally_bombs_icon_as_icon);
		remaining_ally_bombs_icon_as_label.setSize(50, remaining_ally_bombs_icon_as_icon.getIconHeight());
		remaining_ally_bombs_icon_as_label.setLocation((int) (getWidth() * 0.6),
				getHeight() / 2 - remaining_ally_bombs_icon_as_label.getHeight() / 2);
		add(remaining_ally_bombs_icon_as_label);

		remaining_ally_bombs_label = new JLabel("X");
		remaining_ally_bombs_label.setSize(10, (int) (getHeight() * 0.8));
		remaining_ally_bombs_label.setLocation(
				remaining_ally_bombs_icon_as_label.getX() + remaining_ally_bombs_icon_as_label.getWidth() + 10,
				getHeight() / 2 - remaining_ally_bombs_label.getHeight() / 2);
		remaining_ally_bombs_label.setForeground(Color.yellow);
		remaining_ally_bombs_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(remaining_ally_bombs_label);

		score_label = new JLabel("SCORE");
		score_label.setSize(150, (int) (getHeight() * 0.8));
		score_label.setLocation(remaining_ally_bombs_label.getX() + remaining_ally_bombs_label.getWidth() + 50,
				getHeight() / 2 - score_label.getHeight() / 2);
		score_label.setForeground(Color.yellow);
		score_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(score_label);
		// setBounds(0, 0, this.getSize().width, this.getSize().height);

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
	public void on_number_of_remaining_lives_changed(Game game) {
		update_current_scenario_level(game);
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

}
