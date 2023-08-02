package hmi;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;
import game.Game;

public class DesktopTowerDefenseMainViewFrame extends JFrame implements DesktopTowerDefenseMainViewGeneric {

	private static final long serialVersionUID = 1443136088686746460L;

	private static final Logger LOGGER = LogManager.getLogger(DesktopTowerDefenseMainViewFrame.class);

	private MainViewMenuBarManager mainViewMenuBarManager;

	private GameFieldPanel gameFieldPanel;
	private TopPanel topPanel;

	public DesktopTowerDefenseMainViewFrame() {
		super("DesktopTowerDefense");
		mainViewMenuBarManager = new MainViewMenuBarManager(this);
	}

	@SuppressWarnings("unused")
	private void setApplicationIcon() {

		BufferedImage application_buffered_image = null;
		File application_image_file = null;
		String application_image_path = "Images/DesktopTowerDefense_application_icon.png";

		application_image_file = new File(application_image_path);
		try {
			application_buffered_image = ImageIO.read(application_image_file);
			setIconImage(application_buffered_image);
		} catch (IOException e1) {
			LOGGER.info("Could not define application icon " + application_image_path);
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method is invoked from
	 * the event dispatch thread.
	 */
	public void createAndShowGUI() {

		setLayout(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainViewMenuBarManager.createMenu();

		// setApplicationIcon();

		this.addKeyListener(new KeyBoardInputs(this));

		pack();

		setVisible(true);
		setResizable(false);
	}

	public MainViewMenuBarManager getMainViewMenuBarManager() {
		return mainViewMenuBarManager;
	}

	private void new_game(Game game) {

		// Built first to know dimensions
		gameFieldPanel = new GameFieldPanel(this);
		gameFieldPanel.initialize_gamefield(game.getGameField());

		topPanel = new TopPanel(this, gameFieldPanel.getWidth(), HMIConstants.TOP_PANEL_HEIGHT);
		add(topPanel);
		topPanel.setLocation(HMIConstants.EXTERNAL_FRAME_WIDTH, HMIConstants.EXTERNAL_FRAME_WIDTH);

		add(gameFieldPanel);
		gameFieldPanel.setLocation(HMIConstants.EXTERNAL_FRAME_WIDTH,
				topPanel.getY() + topPanel.getHeight() + HMIConstants.EXTERNAL_FRAME_WIDTH);

		setSize(new Dimension(
				gameFieldPanel.getWidth() + 2 * HMIConstants.EXTERNAL_FRAME_WIDTH
						+ HMIConstants.NOT_UNDERSTOOD_MISSING_FRAME_WIDTH,
				gameFieldPanel.getY() + gameFieldPanel.getHeight() + mainViewMenuBarManager.getMenuBar().getHeight()
						+ HMIConstants.EXTERNAL_FRAME_WIDTH + HMIConstants.NOT_UNDERSTOOD_MISSING_FRAME_HEIGHT));

	}

	public void removeTopPanel() {
		remove(topPanel);
	}

	public void removeGameFieldPanel() {
		remove(gameFieldPanel);
	}

	@Override
	public void register_to_game(Game game) {
		new_game(game);
		game.add_game_status_listener(topPanel);
		game.add_game_status_listener(gameFieldPanel);

	}

}