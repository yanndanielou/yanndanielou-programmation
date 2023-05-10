package hmi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;
import game.Game;
import game.GameStatusListener;
import game_board.GameField;

public class TopPanel extends JPanel {

	private static final long serialVersionUID = -4722225029326344692L;
	private static final Logger LOGGER = LogManager.getLogger(TopPanel.class);

	private JLabel remaining_unflagged_mines_label;
	private JLabel game_duration_label;
	private JButton smiley_button;

	public TopPanel(DemineurMainViewFrame demineurMainViewFrame) {
		
		setLayout(null);
		
		remaining_unflagged_mines_label = new JLabel();
		remaining_unflagged_mines_label.setSize(100, 20);
		remaining_unflagged_mines_label.setLocation(HMIConstants.EXTERNAL_FRAME_WIDTH, HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(remaining_unflagged_mines_label);
		
		smiley_button = new JButton();
		smiley_button.setSize(100, 20);
		smiley_button.setLocation(getWidth()/2 - smiley_button.getWidth()/2, HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(smiley_button);

		
		game_duration_label = new JLabel();
		game_duration_label.setSize(100, 20);
		game_duration_label.setLocation(getWidth() - game_duration_label.getWidth() - HMIConstants.EXTERNAL_FRAME_WIDTH, HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(game_duration_label);

		Dimension preferredSize = getPreferredSize();
		int pause = 1;
	}

	public void initialize_display() {
		setLayout(null);
	}

}
