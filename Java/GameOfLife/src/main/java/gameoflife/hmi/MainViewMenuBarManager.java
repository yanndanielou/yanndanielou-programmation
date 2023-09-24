package gameoflife.hmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.core.GameManager;
import gameoflife.gameboard.Cell;
import main.common.random.RandomIntegerGenerator;

public class MainViewMenuBarManager implements ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(MainViewMenuBarManager.class);

	private GameOfLifeMainViewFrame parentMainView = null;
	private JMenuBar menuBar;

	public MainViewMenuBarManager(GameOfLifeMainViewFrame parent) {
		parentMainView = parent;
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
				new NewGameWhileGameIsInProgressPopup(parentMainView).displayOptionPane();
			}
		});
		menu.add(menuItem);

		// a group of check box menu items
		menu.addSeparator();

		menuItem = new JMenuItem("Pause", KeyEvent.VK_P);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GameManager.hasGameInProgress()) {
					GameManager.getInstance().getGame().pause();
				}
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("Resume", KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GameManager.hasGameInProgress()) {
					GameManager.getInstance().getGame().resume();
				}
			}
		});
		menu.add(menuItem);

		// a group of check box menu items
		menu.addSeparator();

		menuItem = new JMenuItem("Start Game", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GameManager.hasGameInProgress()) {
					GameManager.getInstance().getGame().start();
				}
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
				parentMainView.dispose();
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


		menuItem = new JMenuItem("Set one random dead cell alive", KeyEvent.VK_A);
		menuItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (GameManager.hasGameInProgress()) {

					List<Cell> notAliveCells = GameManager.getInstance().getGame().getGameBoard()
							.getAllGameBoardPointsAsOrderedList().stream().map(Cell.class::cast)
							.filter(Predicate.not(Cell::isAlive)).toList();
					if (!notAliveCells.isEmpty()) {
						Cell cell = notAliveCells
								.get(RandomIntegerGenerator.getRandomNumberUsingNextInt(0, notAliveCells.size() - 1));
						cell.setAlive();
					}
				}
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Set one random alive cell to dead", KeyEvent.VK_D);
		menuItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(e -> {

			if (GameManager.hasGameInProgress()) {

				List<Cell> aliveCells = GameManager.getInstance().getGame().getGameBoard()
						.getAllGameBoardPointsAsOrderedList().stream().map(Cell.class::cast).filter(Cell::isAlive)
						.toList();
				if (!aliveCells.isEmpty()) {
					Cell cell = aliveCells
							.get(RandomIntegerGenerator.getRandomNumberUsingNextInt(0, aliveCells.size() - 1));
					cell.setDead();
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
				CheatCodeDialog cheatCodeDialog = new CheatCodeDialog(parentMainView);
				cheatCodeDialog.pack();
				cheatCodeDialog.setLocationRelativeTo(parentMainView);
				cheatCodeDialog.setVisible(true);
				LOGGER.info("actionPerformed" + e);
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

		parentMainView.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LOGGER.info("actionPerformed" + e);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
}
