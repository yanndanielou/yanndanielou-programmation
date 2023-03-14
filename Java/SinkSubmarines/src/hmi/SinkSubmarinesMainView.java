package hmi;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.GameBoardDataModel;

public class SinkSubmarinesMainView extends JFrame {

	private static final long serialVersionUID = 1443136088686746460L;

	private static final Logger LOGGER = LogManager.getLogger(SinkSubmarinesMainView.class);

	private TopPanel topPanel = null;
	private SkyPanel skyPanel = null;
	private AllyBoatPanel allyBoatPanel = null;
	private UnderWaterPanel underWaterPanel = null;
	private MainViewMenuBarManager mainViewMenuBarManager;

	public SinkSubmarinesMainView() {
		super("Title of application");
		setResizable(false);
		mainViewMenuBarManager = new MainViewMenuBarManager(this);
	}

	public void initialize_from_game_board_data_model(GameBoardDataModel gameBoardDataModel) {
		// Create and set up the window.
		Container pane = this.getContentPane();
		pane.setLayout(null);
		Insets insets = pane.getInsets();

		topPanel = new TopPanel(pane, gameBoardDataModel.getWidth(), gameBoardDataModel, null);

		skyPanel = new SkyPanel(pane, gameBoardDataModel.getWidth(), gameBoardDataModel, topPanel);

		allyBoatPanel = new AllyBoatPanel(pane, gameBoardDataModel.getWidth(), gameBoardDataModel, skyPanel);

		underWaterPanel = new UnderWaterPanel(pane, gameBoardDataModel.getWidth(), gameBoardDataModel, allyBoatPanel);

		this.setSize(gameBoardDataModel.getWidth(), 1000);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method is invoked from
	 * the event dispatch thread.
	 */
	public void createAndShowGUI() {

		/* Use an appropriate Look and Feel */
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(200, 200);

		mainViewMenuBarManager.createMenu();

		// Display the window.

		this.setVisible(true);
		this.setResizable(false);

	}

}