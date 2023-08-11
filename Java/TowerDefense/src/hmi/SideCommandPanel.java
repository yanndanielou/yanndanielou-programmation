package hmi;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.Tower;
import belligerents.listeners.AttackerListener;
import belligerents.listeners.TowerListener;
import game.Game;
import game.GameStatusListener;
import game_board.GameBoard;

public class SideCommandPanel extends JLayeredPane implements GameStatusListener, TowerListener, AttackerListener {

	private static final Logger LOGGER = LogManager.getLogger(SideCommandPanel.class);

	private ImageIcon sideCommandPanelBackgroundAsIcon;
	private JLabel sideCommandPanelBackgroundAsLabel;
	private TowerDefenseMainViewFrame towerDefenseMainViewFrame;

	private enum LAYERS_ORDERED_FROM_TOP_TO_BACK {
		TOWERS_CONSTRUCTION_SELECTION, SELECTED_BELLIGERENT_DETAILS, BACKGROUND_IMAGE, UNVISIBLE;
	}

	public SideCommandPanel(TowerDefenseMainViewFrame towerDefenseMainViewFrame) {
		this.towerDefenseMainViewFrame = towerDefenseMainViewFrame;
	}

	public void initializeGamefield(GameBoard gameBoard) {
		setLayout(null);

		sideCommandPanelBackgroundAsIcon = new ImageIcon(
				gameBoard.getGameBoardDataModel().getCommandPanelBackgroundImagePath());
		sideCommandPanelBackgroundAsLabel = new JLabel(sideCommandPanelBackgroundAsIcon);

		add(sideCommandPanelBackgroundAsLabel, LAYERS_ORDERED_FROM_TOP_TO_BACK.BACKGROUND_IMAGE.ordinal());

		setSize(sideCommandPanelBackgroundAsIcon.getIconWidth(), sideCommandPanelBackgroundAsIcon.getIconHeight());
		sideCommandPanelBackgroundAsLabel.setLocation(0, 0);
		sideCommandPanelBackgroundAsLabel.setSize(getSize());

	}

	@Override
	public void on_attacker_end_of_destruction_and_clean(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onListenToAttacker(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_attacker_moved(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_attacker_beginning_of_destruction(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_listen_to_tower(Tower tower) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_tower_removal(Tower tower) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onListenToGameStatus(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameCancelled(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameLost(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_won(Game game) {
		// TODO Auto-generated method stub

	}
}
