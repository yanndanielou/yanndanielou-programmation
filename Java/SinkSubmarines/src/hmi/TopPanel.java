package hmi;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;

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
import game.Game;
import game.GameListener;

public class TopPanel extends JPanel implements GameListener {

	private JLabel current_scenario_level_label;
	private ImageIcon character_sailor_icon;
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
		current_scenario_level_label.setLocation(10, 10);
		current_scenario_level_label.setForeground(Color.yellow);
		current_scenario_level_label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		add(current_scenario_level_label);

		character_sailor_icon = new ImageIcon("Images/character_baby_sailor.png");

		score_label = new JLabel("SCORE");
		score_label.setSize(150, (int) (getHeight() * 0.8));
		score_label.setLocation(400, 10);
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
			current_scenario_level_label
			.setText("LEVEL:");
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
