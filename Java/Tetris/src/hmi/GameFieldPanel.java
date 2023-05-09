package hmi;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;
import game_board.GameField;

//FIXME: try JLayeredPane instead
public class GameFieldPanel extends JPanel {

	JButton all_squares[][];

	private GridLayout layout = null;

	public GameFieldPanel(TetrisMainViewFrame tetrisMainViewFrame) {
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOGGER = LogManager.getLogger(GameFieldPanel.class);

	public void initialize_display() {

	}

	public void initialize_gamefield(GameField gameField) {
		layout = new GridLayout(gameField.getHeight(), gameField.getWidth());
		setLayout(layout);
		
		removeAll();

		setSize(HMIConstants.ELEMENTARY_SQUARE_WIDTH * gameField.getWidth(),
				HMIConstants.ELEMENTARY_SQUARE_WIDTH * gameField.getHeight());

		all_squares = new JButton[gameField.getHeight()][gameField.getWidth()];

		for (int line = 0; line < all_squares.length; line++) {
			for (int column = 0; column < all_squares[line].length; column++) {
				JButton jButton = new JButton();
				all_squares[line][column] = jButton;
				jButton.setSize(HMIConstants.ELEMENTARY_SQUARE_WIDTH, HMIConstants.ELEMENTARY_SQUARE_HEIGHT);
				jButton.setEnabled(false);
				jButton.setBackground(Color.black);
				jButton.setToolTipText("Line " + line + " column " + column);
				add(jButton);
			}
		}

		int pause = 2;
	}

}
