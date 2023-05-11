package hmi;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;
import game.Game;
import game.GameStatusListener;

public class DemineurMainViewFrame extends JFrame implements GameStatusListener {

	private static final long serialVersionUID = 1443136088686746460L;

	private static final Logger LOGGER = LogManager.getLogger(DemineurMainViewFrame.class);

	private MainViewMenuBarManager mainViewMenuBarManager;

	private GameFieldPanel gameFieldPanel;
	private TopPanel topPanel;

	public DemineurMainViewFrame() {
		super("Demineur");
		mainViewMenuBarManager = new MainViewMenuBarManager(this);
	}

	private void setApplicationIcon() {

		BufferedImage application_buffered_image = null;
		File application_image_file = null;
		String application_image_path = "Images/game_icon.png";

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

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainViewMenuBarManager.createMenu();

		setApplicationIcon();

		this.addKeyListener(new KeyBoardInputs(this));

		pack();

		this.setVisible(true);
	}

	public MainViewMenuBarManager getMainViewMenuBarManager() {
		return mainViewMenuBarManager;
	}

	@Override
	public void on_listen_to_game_status(Game game) {

		setLayout(null);

		// Built first to know dimensions
		gameFieldPanel = new GameFieldPanel(this);
		gameFieldPanel.initialize_gamefield(game.getGameField());

		topPanel = new TopPanel(this, gameFieldPanel.getWidth(), HMIConstants.TOP_PANEL_HEIGHT);
		add(topPanel);
		topPanel.setLocation(0, HMIConstants.EXTERNAL_FRAME_WIDTH);
		// panel_content.add(topPanel);

		add(gameFieldPanel);
		gameFieldPanel.setLocation(0, topPanel.getY() + topPanel.getHeight() + HMIConstants.EXTERNAL_FRAME_WIDTH);

		setMinimumSize(new Dimension(gameFieldPanel.getWidth(), gameFieldPanel.getY() + gameFieldPanel.getHeight()
				+ mainViewMenuBarManager.getMenuBar().getHeight() + HMIConstants.EXTERNAL_FRAME_WIDTH + HMIConstants.NOT_UNDERSTOOD_MISSING_FRAME_HEIGHT));

		pack();
	}

	@Override
	public void on_game_cancelled(Game game) {
		gameFieldPanel.on_game_cancelled(game);
	}

	@Override
	public void on_game_lost(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_won(Game game) {
		// TODO Auto-generated method stub

	}

}