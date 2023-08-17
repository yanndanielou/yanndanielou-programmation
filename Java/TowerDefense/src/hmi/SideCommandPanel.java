package hmi;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import gameboard.GameBoard;

public class SideCommandPanel extends JLayeredPane implements GameStatusListener, TowerListener, AttackerListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7463188292663402748L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(SideCommandPanel.class);

	private ImageIcon sideCommandPanelBackgroundAsIcon;
	private JLabel sideCommandPanelBackgroundAsLabel;
	private TowerDefenseMainViewFrame towerDefenseMainViewFrame;
	private GameBoard gameBoard;

	private JLabel mainMenuButtonAsLabel;
	private JLabel startGameButtonAsLabel;
	private JButton createSimpleTowerButton;
	private JButton changeVolumeButton;

	private enum LAYERS_ORDERED_FROM_TOP_TO_BACK {
		TOWERS_CONSTRUCTION_SELECTION, GAME_BUTTONS, SELECTED_BELLIGERENT_DETAILS, BACKGROUND_IMAGE, UNVISIBLE;
	}

	public SideCommandPanel(TowerDefenseMainViewFrame towerDefenseMainViewFrame) {
		this.towerDefenseMainViewFrame = towerDefenseMainViewFrame;
	}

	public void initializeGamefield(GameBoard gameBoard) {
		setLayout(null);

		this.gameBoard = gameBoard;

		sideCommandPanelBackgroundAsIcon = new ImageIcon(
				gameBoard.getGameBoardDataModel().getCommandPanelBackgroundImagePath());
		sideCommandPanelBackgroundAsLabel = new JLabel(sideCommandPanelBackgroundAsIcon);

		add(sideCommandPanelBackgroundAsLabel, LAYERS_ORDERED_FROM_TOP_TO_BACK.BACKGROUND_IMAGE.ordinal());

		setSize(sideCommandPanelBackgroundAsIcon.getIconWidth(), sideCommandPanelBackgroundAsIcon.getIconHeight());
		sideCommandPanelBackgroundAsLabel.setLocation(0, 0);
		sideCommandPanelBackgroundAsLabel.setSize(getSize());

		ImageIcon mainMenuButtonAsIcon = new ImageIcon("Images/MainMenuButtonInSideCommandPanel.PNG");
		mainMenuButtonAsLabel = new JLabel(mainMenuButtonAsIcon);
		mainMenuButtonAsLabel.setSize(mainMenuButtonAsIcon.getIconWidth(), mainMenuButtonAsIcon.getIconHeight());
		mainMenuButtonAsLabel.setLocation(220, 2);
		add(mainMenuButtonAsLabel, LAYERS_ORDERED_FROM_TOP_TO_BACK.TOWERS_CONSTRUCTION_SELECTION.ordinal());

		ImageIcon startGameButtonAsIcon = new ImageIcon("Images/Start_game_button_in_side_command_panel.PNG");
		startGameButtonAsLabel = new JLabel(startGameButtonAsIcon);
		startGameButtonAsLabel.setSize(startGameButtonAsIcon.getIconWidth(), startGameButtonAsIcon.getIconHeight());
		startGameButtonAsLabel.setLocation(42, 50);
		add(startGameButtonAsLabel, LAYERS_ORDERED_FROM_TOP_TO_BACK.TOWERS_CONSTRUCTION_SELECTION.ordinal());
		startGameButtonAsLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gameBoard.getGame().start();
			}
		});

		createSimpleTowerButton = HMIUtils
				.createJButtonFromImage("Images/simple_tower_construction_buttons_in_side_command_panel.PNG");
		createSimpleTowerButton.setLocation(42, 130);
		add(createSimpleTowerButton, LAYERS_ORDERED_FROM_TOP_TO_BACK.TOWERS_CONSTRUCTION_SELECTION.ordinal());
		createSimpleTowerButton.addActionListener((event) -> {
			getHmiPresenter().selectTowerForConstruction(
					gameBoard.getGame().getGameObjectsDataModel().getSimpleTowerDataModel());
		});

		changeVolumeButton = HMIUtils.createJButtonFromImage("Images/Volume_muted_button_in_side_command_panel.PNG");
		changeVolumeButton.setLocation(startGameButtonAsLabel.getX(), mainMenuButtonAsLabel.getY());
		add(changeVolumeButton, LAYERS_ORDERED_FROM_TOP_TO_BACK.GAME_BUTTONS.ordinal());

	}

	@Override
	public void onAttackerEndOfDestructionAndClean(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onListenToAttacker(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAttackerMoved(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAttackerBeginningOfDestruction(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onListenToTower(Tower tower) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTowerRemoval(Tower tower) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onListenToGameStatus(Game game) {

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
	public void onGameWon(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameStarted(Game game) {
		startGameButtonAsLabel.setVisible(false);
	}

	public HmiPresenter getHmiPresenter() {
		return towerDefenseMainViewFrame.getHmiPresenter();
	}

	@Override
	public void onAttackerEscape(Attacker attacker) {
		// TODO Auto-generated method stub

	}
}
