package hmi;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.HMIConstants;
import game.Game;
import game.GameStatusListener;
import game.GameBoardPointListener;
import game_board.GameBoard;
import game_board.GameBoardPoint;
import game_board.SquaresColumn;

public class GameFieldPanel extends JPanel implements GameStatusListener, GameBoardPointListener {

	private static final long serialVersionUID = -1541008040602802454L;

	private HashMap<GameBoardPoint, JButton> square_to_button_map = new HashMap<>();

	private ImageIcon empty_game_board_full_as_icon = new ImageIcon("Images/Empty_game_board_full.png");
	private JLabel empty_game_board_full_as_label;

	private ImageIcon simple_tower_icon = new ImageIcon("Images/Simple_tower.png");

	private enum LAYERS_ORDERED_FROM_TOP_TO_BACK {
		LABELS, UNDER_LABELS, BOMBS, BELLIGERENTS, ROCKS, BACKGROUND_IMAGE, UNVISIBLE;
	}

	private DesktopTowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame;;

	public GameFieldPanel(DesktopTowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame) {
		this.DesktopTowerDefenseMainViewFrame = DesktopTowerDefenseMainViewFrame;
	}

	public void initialize_gamefield(GameBoard gameField) {
		setLayout(null);
		setSize(gameField.getTotalWidth(), gameField.getTotalHeight());

		empty_game_board_full_as_label = new JLabel(empty_game_board_full_as_icon);

		empty_game_board_full_as_label.setSize(gameField.getTotalWidth(), gameField.getTotalHeight());
		empty_game_board_full_as_label.setLocation(0, 0);

		add(empty_game_board_full_as_label, LAYERS_ORDERED_FROM_TOP_TO_BACK.LABELS.ordinal());

	}

	@Override
	public void on_listen_to_game_status(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_cancelled(Game game) {
		removeAll();
		DesktopTowerDefenseMainViewFrame.removeGameFieldPanel();
	}

	@Override
	public void on_game_lost(Game game) {
	}

	@Override
	public void on_game_won(Game game) {
		// TODO Auto-generated method stub

	}

}
