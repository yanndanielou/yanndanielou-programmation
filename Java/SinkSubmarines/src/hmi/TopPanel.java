package hmi;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import builders.gameboard.GameBoardAreaDataModel;
import builders.gameboard.GameBoardDataModel;
import builders.scenariolevel.ScenarioLevelDataModel;
import core.GameManager;
import core.ScenarioLevelExecutor;
import game.Game;
import game.GameListener;

public class TopPanel extends JPanel implements  GameListener {

	private JLabel current_scenario_level = new JLabel();
	private Icon character_sailor_icon;
	private JLabel score;

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

		current_scenario_level.setSize(100, (int) (getHeight() * 0.8));
		current_scenario_level.setLocation(10, 10);
		add(current_scenario_level);

		// setBounds(0, 0, this.getSize().width, this.getSize().height);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (GameManager.hasGameInProgress()) {
			Game game = GameManager.getInstance().getGame();
			ScenarioLevelExecutor scenarioLevelExecutor = ScenarioLevelExecutor.getInstance();

			ScenarioLevelDataModel current_scenario_level_data_model = scenarioLevelExecutor
					.getCurrent_scenario_level_data_model();

			if (current_scenario_level_data_model != null) {
				current_scenario_level.setText("LEVEL " + current_scenario_level_data_model.getScenario_level_number());
			}
		}
	}

	@Override
	public void on_game_paused(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_number_of_remaining_lives_changed(Game game) {
		// TODO Auto-generated method stub
		
	}

}
