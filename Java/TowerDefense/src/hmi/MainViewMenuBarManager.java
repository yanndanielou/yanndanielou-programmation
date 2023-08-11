package hmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.GameObjectsDataModel;
import builders.TowerDataModel;
import cheat_codes.CheatCodeManager;
import core.GameManager;
import game.Game;
import game_board.GameBoard;
import game_board.GameBoardAttackersEntryArea;
import game_board.GameBoardAttackersExitArea;

public class MainViewMenuBarManager implements ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(MainViewMenuBarManager.class);

	private TowerDefenseMainViewFrame parent_main_view = null;
	private JMenuBar menuBar;

	public MainViewMenuBarManager(TowerDefenseMainViewFrame parent) {
		parent_main_view = parent;
		menuBar = new JMenuBar();
	}

	private JMenu createGameMenuColumn() {

		JMenu menu;
		// JMenu submenu;
		JMenuItem menuItem;
		// JCheckBoxMenuItem cbMenuItem;

		// Build the first menu.
		menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(menu);
		menu.addActionListener(this);

		menuItem = new JMenuItem("New Game", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewGameWhileGameIsInProgressPopup(parent_main_view).displayOptionPane();
			}
		});
		menu.add(menuItem);

		// a group of check box menu items
		menu.addSeparator();

		menuItem = new JMenuItem("Exit", KeyEvent.VK_P);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_DOWN_MASK));
		// menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really
		// do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Kill application");
				parent_main_view.dispose();
			}
		});
		menu.add(menuItem);

		return menu;
	}

	private JMenu createTestsMenuColumn() {

		JMenu menu;
		// JMenu submenu;
		JMenuItem menuItem;
		// JCheckBoxMenuItem cbMenuItem;

		// Build the first menu.
		menu = new JMenu("Tests");
		menu.setMnemonic(KeyEvent.VK_T);
		menuBar.add(menu);
		menu.addActionListener(this);

		menuItem = new JMenuItem("New Tower", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (GameManager.hasGameInProgress()) {
					GameManager gameManager = GameManager.getInstance();
					Game game = gameManager.getGame();
					GameBoard gameBoard = game.getGameBoard();
					GameObjectsDataModel game_objects_data_model = game.getGameObjectsDataModel();
					TowerDataModel simple_tower_data_model = game_objects_data_model.getSimpleTowerDataModel();

					gameManager.createSimpleTower(
							gameBoard.getTotalWidth() / 2 - simple_tower_data_model.getWidth() / 2,
							gameBoard.getTotalHeight() / 2 - simple_tower_data_model.getHeight() / 2, 1);
				}
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("New normal attacker", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (GameManager.hasGameInProgress()) {
					GameManager gameManager = GameManager.getInstance();
					Game game = gameManager.getGame();
					GameBoard gameBoard = game.getGameBoard();
					GameBoardAttackersEntryArea gameBoardAttackersEntryArea = gameBoard
							.getGameBoardAttackersEntryAreas().get(0);

					GameBoardAttackersExitArea oneRandomExitArea = gameBoard.getGameBoardAttackersExitAreas().get(0);

					gameManager.createNormalAttacker(gameBoardAttackersEntryArea, oneRandomExitArea, 1);
				}
			}
		});
		menu.add(menuItem);

		return menu;

	}

	private JMenu createCheatsMenuColumn() {
		JMenu menu;
		JMenuItem menuItem;

		// Build second menu in the menu bar.
		menu = new JMenu("Cheats");
		menu.setMnemonic(KeyEvent.VK_C);
		menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		getMenuBar().add(menu);

		menuItem = new JMenuItem("Cheat code", KeyEvent.VK_C);
		menuItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheatCodeDialog cheatCodeDialog = new CheatCodeDialog(parent_main_view);
				cheatCodeDialog.pack();
				cheatCodeDialog.setLocationRelativeTo(parent_main_view);
				cheatCodeDialog.setVisible(true);
				LOGGER.info("actionPerformed" + e);
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Forbid enemies to move");
		menuItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheatCodeManager.getInstance().forbid_enemies_to_move();
			}
		});
		menu.add(menuItem);

		return menu;
	}

	public void createMenu() {
		// Create the menu bar.
		menuBar.add(createGameMenuColumn());
		menuBar.add(createTestsMenuColumn());
		menuBar.add(createCheatsMenuColumn());

		parent_main_view.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LOGGER.info("actionPerformed" + e);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
}
