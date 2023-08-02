package hmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainViewMenuBarManager implements ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(MainViewMenuBarManager.class);

	private DesktopTowerDefenseMainViewFrame parent_main_view = null;
	private JMenuBar menuBar;

	public MainViewMenuBarManager(DesktopTowerDefenseMainViewFrame parent) {
		parent_main_view = parent;
	}

	public void createMenu() {
		// Where the GUI is created:

		JMenu menu;
		// JMenu submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem radioButtonMenuItem;
		// JCheckBoxMenuItem cbMenuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();

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
				new NewGameWhileGameIsInProgressPopup(parent_main_view).display_option_pane();
				/*
				 * if (GameManager.hasGameInProgress()) { Object[] options = { "Yes", "No" };
				 * 
				 * int n = JOptionPane.showOptionDialog(parent_main_view, "Abort current game?",
				 * "Game in progress!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				 * null, options, options[0]); LOGGER.info("Abort current game popup answer: " +
				 * n); if (n == JOptionPane.YES_OPTION) { LOGGER.info("Abo.rt current game");
				 * GameManager.getInstance().abort_current_game(); } else if (n ==
				 * JOptionPane.NO_OPTION) { LOGGER.info("Do not abort current game. Resume");
				 * return; } else { LOGGER.info("Default answer (popup closed?)"); return; } }
				 * GameManager.getInstance().new_game();
				 */
			}
		});
		menu.add(menuItem);

		// a group of radio button menu items
		menu.addSeparator();

		ButtonGroup group = new ButtonGroup();

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
