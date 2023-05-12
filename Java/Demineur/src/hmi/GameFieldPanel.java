package hmi;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;
import game.Game;
import game.GameStatusListener;
import game_board.GameField;

//FIXME: try JLayeredPane instead
public class GameFieldPanel extends JPanel implements GameStatusListener {

	private static final Logger LOGGER = LogManager.getLogger(GameFieldPanel.class);
	private static final long serialVersionUID = -1541008040602802454L;

	JButton all_squares[][];

	private GridLayout layout = null;

	public GameFieldPanel(DemineurMainViewFrame demineurMainViewFrame) {
		// TODO Auto-generated constructor stub
	}

	public void initialize_display() {

	}

	public void initialize_gamefield(GameField gameField) {
		setLayout(null);

		setSize(HMIConstants.ELEMENTARY_SQUARE_WIDTH * gameField.getWidth(),
				HMIConstants.ELEMENTARY_SQUARE_HEIGHT * gameField.getHeight());

		all_squares = new JButton[gameField.getHeight()][gameField.getWidth()];

		for (int column = 0; column < all_squares.length; column++) {
			for (int line = 0; line < all_squares[column].length; line++) {
				JButton jButton = new JButton();
				all_squares[column][line] = jButton;
				jButton.setSize(HMIConstants.ELEMENTARY_SQUARE_WIDTH, HMIConstants.ELEMENTARY_SQUARE_HEIGHT);
				jButton.setBackground(Color.LIGHT_GRAY);
				jButton.setIcon(new ImageIcon("Images/square_initial_state.png"));
				// jButton.setPressedIcon(new ImageIcon("Images/square_being_clicked.png"));
				// jButton.setRolloverIcon(new ImageIcon("Images/square_being_clicked.png"));
				jButton.setToolTipText("Line " + line + " column " + column);
				jButton.setLocation(line * HMIConstants.ELEMENTARY_SQUARE_WIDTH,
						column * HMIConstants.ELEMENTARY_SQUARE_HEIGHT);
				add(jButton);
			}
		}

	}

	@Override
	public void on_listen_to_game_status(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_cancelled(Game game) {
		removeAll();
		all_squares = null;
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
