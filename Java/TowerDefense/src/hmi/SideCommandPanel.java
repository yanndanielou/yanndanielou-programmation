package hmi;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.FileSystemNotFoundException;

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
import builders.TowerDataModel;
import game.Game;
import game.GameStatusListener;
import game_board.GameBoard;

public class SideCommandPanel extends JLayeredPane implements GameStatusListener, TowerListener, AttackerListener {

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

		ImageIcon mainMenuButtonAsIcon = new ImageIcon("Images/Main_menu_button_in_side_command_panel.PNG");
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
				startGameButtonAsLabel.setVisible(false);
			}
		});

		createSimpleTowerButton = createJButtonFromImage(
				"Images/simple_tower_construction_buttons_in_side_command_panel.PNG");
		createSimpleTowerButton.setLocation(42, 130);
		add(createSimpleTowerButton, LAYERS_ORDERED_FROM_TOP_TO_BACK.TOWERS_CONSTRUCTION_SELECTION.ordinal());
		createSimpleTowerButton.addActionListener((event) -> {
			getHmiPresenter().selectTowerForConstruction(
					gameBoard.getGame().getGameObjectsDataModel().getSimpleTowerDataModel());
		});

		changeVolumeButton = createJButtonFromImage("Images/Volume_muted_button_in_side_command_panel.PNG");
		changeVolumeButton.setLocation(startGameButtonAsLabel.getX(), mainMenuButtonAsLabel.getY());
		add(changeVolumeButton, LAYERS_ORDERED_FROM_TOP_TO_BACK.GAME_BUTTONS.ordinal());

	}

	private JButton createJButtonFromImage(String imagePath) {
		ImageIcon buttonIcon = new ImageIcon(imagePath);
		if (buttonIcon.getIconHeight() <= 0) {
			throw new FileSystemNotFoundException("Invalid path:" + imagePath);
		}
		JButton buttonToCreate = new JButton(buttonIcon);
		buttonToCreate.setSize(buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
		buttonToCreate.setBorder(null);
		return buttonToCreate;
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
	public void on_attacker_moved(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAttackerBeginningOfDestruction(Attacker attacker) {
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
		// TODO Auto-generated method stub

	}

	public HmiPresenter getHmiPresenter() {
		return towerDefenseMainViewFrame.getHmiPresenter();
	}
}
