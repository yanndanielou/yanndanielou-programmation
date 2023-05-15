package hmi;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;
import game.Game;
import game.GameStatusListener;

public class TopPanel extends JPanel implements GameStatusListener {

	private static final long serialVersionUID = -4722225029326344692L;
	private static final Logger LOGGER = LogManager.getLogger(TopPanel.class);

	private JLabel remaining_unflagged_mines_label;
	private JLabel game_duration_label;
	private JButton smiley_button;

	private ImageIcon smiley_normal_icon;
	private ImageIcon smiley_game_lost;
	private ImageIcon smiley_game_won;
	private ImageIcon smiley_click_in_progress;

	private DemineurMainViewFrame demineurMainViewFrame;

	public TopPanel(DemineurMainViewFrame demineurMainViewFrame, int width, int height) {

		this.demineurMainViewFrame = demineurMainViewFrame;

		setLayout(null);
		setSize(width, height);

		remaining_unflagged_mines_label = new JLabel();
		remaining_unflagged_mines_label.setText("Remaining unfallged mines");
		remaining_unflagged_mines_label.setSize(20, HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT);
		remaining_unflagged_mines_label.setLocation(HMIConstants.EXTERNAL_FRAME_WIDTH,
				HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(remaining_unflagged_mines_label);

		smiley_button = new JButton();
		smiley_button.setSize(HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT, HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT);
		smiley_button.setLocation(getWidth() / 2 - smiley_button.getWidth() / 2, HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(smiley_button);
		smiley_button.addActionListener(e -> {
			NewGameWhileGameIsInProgressPopup newGameWhileGameIsInProgressPopup = new NewGameWhileGameIsInProgressPopup(
					this);
			newGameWhileGameIsInProgressPopup.display_option_pane();
		});

		// requires smiley_button to be created for its size
		smiley_normal_icon = get_scalled_icon("Images/smiley_normal.PNG");
		smiley_game_lost = get_scalled_icon("Images/smiley_game_lost.PNG");
		smiley_game_won = get_scalled_icon("Images/smiley_game_won.PNG");
		smiley_click_in_progress = get_scalled_icon("Images/smiley_click_in_progress.PNG");

		game_duration_label = new JLabel();
		game_duration_label.setText("Game Duration");
		game_duration_label.setSize(20, HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT);
		game_duration_label.setLocation(getWidth() - game_duration_label.getWidth() - HMIConstants.EXTERNAL_FRAME_WIDTH,
				HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(game_duration_label);

	}

	private ImageIcon get_scalled_icon(String image_path) {
		return HMIUtils.get_scalled_icon(image_path, (int) (smiley_button.getWidth() * 0.9),
				(int) (smiley_button.getHeight() * 0.9));
	}

	@Override
	public void on_listen_to_game_status(Game game) {
		smiley_button.setIcon(smiley_normal_icon);
	}

	@Override
	public void on_game_cancelled(Game game) {
		removeAll();
		demineurMainViewFrame.removeTopPanel();
	}

	@Override
	public void on_game_lost(Game game) {
		smiley_button.setIcon(smiley_game_lost);

	}

	@Override
	public void on_game_won(Game game) {
		smiley_button.setIcon(smiley_game_won);

	}

}
