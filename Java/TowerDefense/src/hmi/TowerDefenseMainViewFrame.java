package hmi;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.Tower;
import constants.HMIConstants;
import game.Game;

public class TowerDefenseMainViewFrame extends JFrame implements TowerDefenseMainViewGeneric {

	private static final long serialVersionUID = 1443136088686746460L;

	private static final Logger LOGGER = LogManager.getLogger(TowerDefenseMainViewFrame.class);

	private MainViewMenuBarManager mainViewMenuBarManager;

	private GameBoardPanel gameFieldPanel;
	private SideCommandPanel sideCommandPanel;
	private TopPanel topPanel;

	private HmiPresenter hmiPresenter = null;

	public TowerDefenseMainViewFrame() {
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
		gameFieldPanel = new GameBoardPanel(this);
		gameFieldPanel.initializeGamefield(game.getGameBoard());
		add(gameFieldPanel);

		sideCommandPanel = new SideCommandPanel(this);
		sideCommandPanel.initializeGamefield(game.getGameBoard());
		add(sideCommandPanel);

		topPanel = new TopPanel(this, gameFieldPanel.getWidth() + sideCommandPanel.getWidth(),
				HMIConstants.TOP_PANEL_HEIGHT);
		topPanel.setLocation(HMIConstants.EXTERNAL_FRAME_WIDTH, HMIConstants.EXTERNAL_FRAME_WIDTH);
		add(topPanel);

		gameFieldPanel.setLocation(HMIConstants.EXTERNAL_FRAME_WIDTH,
				topPanel.getY() + topPanel.getHeight());

		sideCommandPanel.setLocation(gameFieldPanel.getX() + gameFieldPanel.getWidth(), gameFieldPanel.getY());

		hmiPresenter = new HmiPresenter(this, topPanel, gameFieldPanel, sideCommandPanel);

		setSize(new Dimension(
				gameFieldPanel.getWidth() + sideCommandPanel.getWidth() + 2 * HMIConstants.EXTERNAL_FRAME_WIDTH
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
	public void registerToGame(Game game) {
		new_game(game);
		game.addGameStatusListener(topPanel);
		game.addGameStatusListener(gameFieldPanel);
		game.addGameStatusListener(sideCommandPanel);

	}

	@Override
	public void register_to_tower(Tower tower) {
		tower.addListener(gameFieldPanel);
	}

	@Override
	public void register_to_attacker(Attacker attacker) {
		attacker.addListener(gameFieldPanel);
	}

	public SideCommandPanel getSideCommandPanel() {
		return sideCommandPanel;
	}

	public HmiPresenter getHmiPresenter() {
		return hmiPresenter;
	}
}