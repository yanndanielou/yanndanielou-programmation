package hmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.GameDifficultyDataModel;
import core.GameManager;
import core.UserChoicesManager;
import game.GameDifficultyChosen;

public class MainViewMenuBarManager implements ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(MainViewMenuBarManager.class);

	private DemineurMainViewFrame parent_main_view = null;
	private JMenuBar menuBar;

	public MainViewMenuBarManager(DemineurMainViewFrame parent) {
		parent_main_view = parent;
	}

	public void createMenu() {
		// Where the GUI is created:

		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem radioButtonMenuItem;
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
						LOGGER.info("Abo.rt current game");
						GameManager.getInstance().abort_current_game();
					} else if (n == JOptionPane.NO_OPTION) {
						LOGGER.info("Do not abort current game. Resume");
						return;
					} else {
						LOGGER.info("Default answer (popup closed?)");
						return;
					}
				}
				GameManager.getInstance().new_game();
			}
		});
		menu.add(menuItem);

		// a group of radio button menu items
		menu.addSeparator();

		ButtonGroup group = new ButtonGroup();

		UserChoicesManager user_choice_manager = UserChoicesManager.getInstance();
		for (GameDifficultyDataModel gameDifficultyDataModel : user_choice_manager.getGame_difficulty_data_models()) {

			radioButtonMenuItem = new JRadioButtonMenuItem(gameDifficultyDataModel.getName());
			radioButtonMenuItem.setSelected(user_choice_manager.getGameDifficultyChosen() == gameDifficultyDataModel);
			// rbMenuItem.setMnemonic(KeyEvent.VK_R);
			radioButtonMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					user_choice_manager.selectGameDifficulty(gameDifficultyDataModel);
					LOGGER.info("rbMenuItem.addActionListener");
				}
			});

			group.add(radioButtonMenuItem);
			menu.add(radioButtonMenuItem);
		}

		radioButtonMenuItem = new JRadioButtonMenuItem("Custom..");
		radioButtonMenuItem.setSelected(
				user_choice_manager.getGameDifficultyChosen() == user_choice_manager.getCustom_game_difficulty());
		// rbMenuItem.setMnemonic(KeyEvent.VK_R);
		radioButtonMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("rbMenuItem.addActionListener");
			}
		});

		group.add(radioButtonMenuItem);
		menu.add(radioButtonMenuItem);

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
