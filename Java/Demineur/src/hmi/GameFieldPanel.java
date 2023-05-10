package hmi;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;
import game.Game;
import game.GameStatusListener;
import game_board.GameField;

//FIXME: try JLayeredPane instead
public class GameFieldPanel extends JPanel implements GameStatusListener {

	JButton all_squares[][];

	private GridLayout layout = null;

	public GameFieldPanel(DemineurMainViewFrame demineurMainViewFrame) {
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOGGER = LogManager.getLogger(GameFieldPanel.class);

	public void initialize_display() {

	}

	public void initialize_gamefield(GameField gameField) {
		layout = new GridLayout(gameField.getHeight(), gameField.getWidth(), 0, 0);
		setLayout(layout);

		removeAll();

		setSize(HMIConstants.ELEMENTARY_SQUARE_WIDTH * gameField.getWidth(),
				HMIConstants.ELEMENTARY_SQUARE_HEIGHT * gameField.getHeight());

		all_squares = new JButton[gameField.getHeight()][gameField.getWidth()];

		for (int line = 0; line < all_squares.length; line++) {
			for (int column = 0; column < all_squares[line].length; column++) {
				JButton jButton = new JButton();
				all_squares[line][column] = jButton;
				jButton.setSize(HMIConstants.ELEMENTARY_SQUARE_WIDTH, HMIConstants.ELEMENTARY_SQUARE_HEIGHT);
				jButton.setEnabled(false);
				jButton.setBackground(Color.black);
				Border border = jButton.getBorder();
				// jButton.setBorder(border);
				jButton.setToolTipText("Line " + line + " column " + column);
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
