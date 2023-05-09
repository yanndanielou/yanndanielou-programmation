package hmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;

public class MainViewMenuBarManager implements ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(MainViewMenuBarManager.class);

	private TetrisMainViewFrame parent_main_view = null;
	private JMenuBar menuBar;

	public MainViewMenuBarManager(TetrisMainViewFrame parent) {
		parent_main_view = parent;
	}

	public void createMenu() {
		// Where the GUI is created:

		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

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
				if (GameManager.hasGameInProgress()) {
					Object[] options = { "Yes", "No" };

					int n = JOptionPane.showOptionDialog(parent_main_view, "Abort current game?", "Game in progress!",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					LOGGER.info("Abort current game popup answer: " + n);
					if (n == JOptionPane.YES_OPTION) {
						LOGGER.info("Abort current game");
						GameManager.getInstance().abort_current_game();
					} else if (n == JOptionPane.NO_OPTION) {
						LOGGER.info("Do not abort current game. Resume");
						return;
					} else {
						LOGGER.info("Default answer (popup closed?)");
						return;
					}
				}

			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Pause & Resume", KeyEvent.VK_P);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (GameManager.hasGameInProgress()) {
					GameManager.getInstance().pause_or_resume_current_game();
				} else {
					LOGGER.info("No game in progress, cannot pause or resume");
				}
			}
		});
		menu.add(menuItem);

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

		// Build the first menu.
		menu = new JMenu("A Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		getMenuBar().add(menu);
		menu.addActionListener(this);

		// a group of JMenuItems
		menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("actionPerformed" + e);
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Both text and icon", new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		menuItem.addActionListener(this);

		menu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		rbMenuItem.addActionListener(this);
		menuItem.addActionListener(this);

		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		rbMenuItem.addActionListener(this);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		// a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

		// a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);
		submenu.addActionListener(this);

		menuItem = new JMenuItem("An item in the submenu");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Another item");
		menuItem.addActionListener(this);

		submenu.add(menuItem);
		menu.add(submenu);

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

		// Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		getMenuBar().add(menu);

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
