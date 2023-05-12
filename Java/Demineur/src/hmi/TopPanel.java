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

	public TopPanel(DemineurMainViewFrame demineurMainViewFrame, int width, int height) {

		setLayout(null);
		setSize(width, height);

		remaining_unflagged_mines_label = new JLabel();
		remaining_unflagged_mines_label.setText("Remaining unfallged mines");
		remaining_unflagged_mines_label.setSize(20, HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT);
		remaining_unflagged_mines_label.setLocation(HMIConstants.EXTERNAL_FRAME_WIDTH,
				HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(remaining_unflagged_mines_label);

		smiley_button = new JButton();
		Image img = new ImageIcon("Images/smiley_normal.PNG").getImage();
		Image icon_scalled_as_image = img.getScaledInstance((int) (HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT * 0.9),
				(int) (HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT * 0.9), Image.SCALE_SMOOTH);

		smiley_button.setIcon(new ImageIcon(icon_scalled_as_image));
		smiley_button.setSize(HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT, HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT);
		smiley_button.setLocation(getWidth() / 2 - smiley_button.getWidth() / 2, HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(smiley_button);
		smiley_button.addActionListener(e -> {
			NewGameWhileGameIsInProgressPopup newGameWhileGameIsInProgressPopup = new NewGameWhileGameIsInProgressPopup(this);
			newGameWhileGameIsInProgressPopup.display_option_pane();
		});

		game_duration_label = new JLabel();
		game_duration_label.setText("Game Duration");
		game_duration_label.setSize(20, HMIConstants.TOP_PANEL_ELEMENTS_HEIGHT);
		game_duration_label.setLocation(getWidth() - game_duration_label.getWidth() - HMIConstants.EXTERNAL_FRAME_WIDTH,
				HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(game_duration_label);

	}

	@Override
	public void on_listen_to_game_status(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_cancelled(Game game) {
		removeAll();
	}

	@Override
	public void on_game_lost(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_won(Game game) {
		// TODO Auto-generated method stub

	}

}
