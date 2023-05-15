package hmi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalButtonUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;
import core.GameManager;
import game.Game;
import game.GameStatusListener;
import game.SquareListener;
import game_board.GameField;
import game_board.Square;
import game_board.SquaresColumn;

public class GameFieldPanel extends JPanel implements GameStatusListener, SquareListener {

	private static final Logger LOGGER = LogManager.getLogger(GameFieldPanel.class);
	private static final long serialVersionUID = -1541008040602802454L;

	private HashMap<Square, JButton> square_to_button_map = new HashMap<>();

//	private JButton all_squares[][];

	private ImageIcon square_initial_state_icon = new ImageIcon("Images/square_initial_state.png");

	private ImageIcon square_mine_exploded_icon;
	private ImageIcon mine_discovered;
	private ImageIcon square_flagged;
	private ImageIcon square_flagged_by_mistake;
	private ImageIcon square_without_mine_and_neighbourg_mine_discovered_with_all_bordered_16_pixels;

	private Color default_button_background_color = null;
	private Color default_button_foreground_color = null;
	private Font default_button_font = null;

	public GameFieldPanel(DemineurMainViewFrame demineurMainViewFrame) {
		initialize_icons();
	}

	private void initialize_icons() {

		square_initial_state_icon = get_scalled_icon("Images/square_initial_state.png");

		square_mine_exploded_icon = get_scalled_icon("Images/mine_exploded.PNG");
		mine_discovered = get_scalled_icon("Images/mine_discovered.png");
		square_flagged = get_scalled_icon("Images/flag.PNG");
		square_flagged_by_mistake = get_scalled_icon("Images/square_flagged_by_mistake.PNG");
		square_without_mine_and_neighbourg_mine_discovered_with_all_bordered_16_pixels = get_scalled_icon(
				"Images/square_without_mine_and_neighbourg_mine_discovered_with_all_bordered_16_pixels.PNG");

	}

	private ImageIcon get_scalled_icon(String image_path) {

		Image img = new ImageIcon(image_path).getImage();
		Image scalled_image = img.getScaledInstance((int) (HMIConstants.ELEMENTARY_SQUARE_HEIGHT * 0.9),
				(int) (HMIConstants.ELEMENTARY_SQUARE_WIDTH * 0.9), Image.SCALE_SMOOTH);

		ImageIcon imageIcon = new ImageIcon(scalled_image);
		return imageIcon;

	}

	public void initialize_gamefield(GameField gameField) {
		setLayout(null);

		setSize(HMIConstants.ELEMENTARY_SQUARE_WIDTH * gameField.getWidth(),
				HMIConstants.ELEMENTARY_SQUARE_HEIGHT * gameField.getHeight());

		// all_squares = new JButton[gameField.getHeight()][gameField.getWidth()];

		ArrayList<SquaresColumn> squaresColumns = gameField.getSquaresColumns();

		for (SquaresColumn squaresColumn : squaresColumns) {

			ArrayList<Square> squares = squaresColumn.getSquares();
			for (Square square : squares) {

				int line_number = squares.indexOf(square);
				int column_number = squaresColumn.getIndex();

				JButton jButton = new JButton();
				// all_squares[column][line] = jButton;

				square_to_button_map.put(square, jButton);

				jButton.setSize(HMIConstants.ELEMENTARY_SQUARE_WIDTH, HMIConstants.ELEMENTARY_SQUARE_HEIGHT);

				jButton.setToolTipText("Line " + line_number + " column " + column_number);
				jButton.setLocation(line_number * HMIConstants.ELEMENTARY_SQUARE_WIDTH,
						column_number * HMIConstants.ELEMENTARY_SQUARE_HEIGHT);

				square.addSquareListener(this);

				redraw_square_status(square);
				add(jButton);

				jButton.addActionListener(e -> {
					GameManager.getInstance().open_square(square, true);
				});
				jButton.addMouseListener(new SquareJButtonMouseListener(square));
			}

		}

	}

	private void redraw_square_status(Square square) {
		JButton jButton = square_to_button_map.get(square);

		if (square.isDiscovered() && square.isFlagged() && !square.isContains_mine()) {
			setJButtonIcon(jButton, square_flagged_by_mistake);
			jButton.setEnabled(false);
		} else if (square.isFlagged()) {
			setJButtonIcon(jButton, square_flagged);
			jButton.setEnabled(false);
		} else if (square.isQuestion_marked()) {
			jButton.setEnabled(false);
			setJButtonText(jButton, "?");
		} else if (square.isExploded()) {
			setJButtonIcon(jButton, square_mine_exploded_icon);
			jButton.setEnabled(false);
		} else if (!square.isDiscovered()) {
			setJButtonIcon(jButton, square_initial_state_icon);
			jButton.setEnabled(true);
		} else if (square.isDiscovered() && square.isContains_mine()) {
			setJButtonIcon(jButton, mine_discovered);
			jButton.setEnabled(false);
		} else if (square.getNumber_of_neighbour_mines() == 0) {
			jButton.setEnabled(false);
			setJButtonIcon(jButton, square_without_mine_and_neighbourg_mine_discovered_with_all_bordered_16_pixels);
		} else {
			jButton.setEnabled(false);
			setJButtonText(jButton, "" + (square.getNumber_of_neighbour_mines()));
		}

		// jButton.setPressedIcon(new ImageIcon("Images/square_being_clicked.png"));
		// jButton.setRolloverIcon(new ImageIcon("Images/square_being_clicked.png"));
	}

	private void setJButtonText(JButton jButton, String text) {
		jButton.setIcon(null);
		jButton.setDisabledIcon(null);

		jButton.setText(text);

		jButton.setBackground(Color.WHITE);
		jButton.setForeground(Color.RED);
		jButton.setMargin(new Insets(0, 0, 0, 0));

		jButton.setUI(new MetalButtonUI() {

			protected Color getDisabledTextColor() {
				return Color.RED;
			}
		});

	}

	private void setJButtonIcon(JButton jButton, ImageIcon imageIcon) {

		if (default_button_background_color == null) {
			default_button_background_color = jButton.getBackground();
		}
		if (default_button_foreground_color == null) {
			default_button_foreground_color = jButton.getForeground();
		}
		if (default_button_font == null) {
			default_button_font = jButton.getFont();
		}

		// jButton.setBackground(default_button_background_color);
		// jButton.setForeground(default_button_foreground_color);
		// jButton.setFont(default_button_font);

		jButton.setText(null);
		jButton.setIcon(imageIcon);
		jButton.setDisabledIcon(imageIcon);

	}

	@Override
	public void on_listen_to_game_status(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_cancelled(Game game) {
		removeAll();
		// all_squares = null;
	}

	@Override
	public void on_game_lost(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_won(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_listen_to_square(Square square) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_square_status_changed(Square square) {
		redraw_square_status(square);
	}

}
