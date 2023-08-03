package hmi;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import belligerents.Tower;
import belligerents.listeners.TowerListener;
import game.Game;
import game.GameBoardPointListener;
import game.GameStatusListener;
import game_board.GameBoard;

public class GameFieldPanel extends JLayeredPane implements GameStatusListener, GameBoardPointListener, TowerListener {

	private static final long serialVersionUID = -1541008040602802454L;

	private ImageIcon empty_game_board_full_as_icon = new ImageIcon("Images/Empty_game_board_full.png");
	private JLabel empty_game_board_full_as_label;

	private ImageIcon simple_tower_icon = new ImageIcon("Images/Simple_tower.png");

	private HashMap<Tower, JLabel> tower_to_label_map = new HashMap<>();

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

		add(empty_game_board_full_as_label, 1);

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

	private void update_tower_location(Tower tower) {

	}

	@Override
	public void on_listen_to_tower(Tower tower) {
		ImageIcon get_graphical_representation_as_icon = tower.get_graphical_representation_as_icon();
		JLabel tower_as_label = new JLabel(get_graphical_representation_as_icon);
		tower_as_label.setLocation((int) tower.getSurrounding_rectangle_absolute_on_complete_board().getX(),
				(int) tower.getSurrounding_rectangle_absolute_on_complete_board().getY());
		tower_as_label.setSize((int) tower.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
				(int) tower.getSurrounding_rectangle_absolute_on_complete_board().getHeight());
		tower_to_label_map.put(tower, tower_as_label);
		// add(tower_as_label);
		add(tower_as_label, 0);
		repaint();
		
	}

	@Override
	public void on_tower_moved(Tower tower) {
		// TODO Auto-generated method stub

	}

}
