package gameoflife.hmi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.constants.HMIConstants;
import gameoflife.game.Game;

public class GameOfLifeMainViewFrame extends JFrame implements GameOfLifeMainViewGeneric {

	private static final long serialVersionUID = 1443136088686746460L;

	private static final Logger LOGGER = LogManager.getLogger(GameOfLifeMainViewFrame.class);

	private MainViewMenuBarManager mainViewMenuBarManager;

	private GameBoardPanel gameFieldPanel;
	private TopPanel topPanel;
	private JScrollPane gameFieldScrollPane;

	JPanel mainViewContentPane;

	public GameOfLifeMainViewFrame() {
		super("DesktopGameOfLife");
		mainViewMenuBarManager = new MainViewMenuBarManager(this);
	}

	@SuppressWarnings("unused")
	private void setApplicationIcon() {

		BufferedImage applicationBufferedImage = null;
		File applicationImageFile = null;
		String applicationImagePath = "Images/DesktopGameOfLifeApplicationIcon.png";

		applicationImageFile = new File(applicationImagePath);
		try {
			applicationBufferedImage = ImageIO.read(applicationImageFile);
			setIconImage(applicationBufferedImage);
		} catch (IOException e1) {
			LOGGER.info(() -> "Could not define application icon " + applicationImagePath);
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method is invoked from
	 * the event dispatch thread.
	 */
	public void createAndShowGUI() {

		setLocationRelativeTo(null);
		setVisible(true);

		mainViewMenuBarManager.createMenu();
		this.addKeyListener(new KeyBoardInputs(this));

		this.setMinimumSize(HMIConstants.MINIMUM_WINDOW_DIMENSION);

		setLocationRelativeTo(null);
	}

	public MainViewMenuBarManager getMainViewMenuBarManager() {
		return mainViewMenuBarManager;
	}

	private void newGame(Game game) {

		// Built first to know dimensions
		gameFieldPanel = new GameBoardPanel(this);
		gameFieldPanel.initializeGamefield(game.getGameBoard());

		mainViewContentPane = new JPanel();

		gameFieldScrollPane = new JScrollPane(gameFieldPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		gameFieldScrollPane.setSize(getSize());

		gameFieldScrollPane.getViewport().setPreferredSize(gameFieldPanel.getPreferredSize());

		mainViewContentPane.setLayout(new BorderLayout());
		mainViewContentPane.add(gameFieldScrollPane);
		mainViewContentPane.setSize(getSize());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainViewContentPane);
		// pack();
		setVisible(true);

		// gameFieldScrollPane.setViewportView(gameFieldPanel);
		//

		// add(gameFieldPanel);

		// gameFieldPanel.setLocation(HMIConstants.EXTERNAL_FRAME_WIDTH,
		// HMIConstants.EXTERNAL_FRAME_WIDTH);

		/*
		 * setSize(new Dimension( gameFieldPanel.getWidth() + 2 *
		 * HMIConstants.EXTERNAL_FRAME_WIDTH +
		 * HMIConstants.NOT_UNDERSTOOD_MISSING_FRAME_WIDTH, gameFieldPanel.getY() +
		 * gameFieldPanel.getHeight() + mainViewMenuBarManager.getMenuBar().getHeight()
		 * + HMIConstants.EXTERNAL_FRAME_WIDTH +
		 * HMIConstants.NOT_UNDERSTOOD_MISSING_FRAME_HEIGHT));
		 */
	}

	public void removeTopPanel() {
		remove(topPanel);
	}

	public void removeGameFieldPanel() {
		remove(gameFieldPanel);
	}

	@Override
	public void registerToGame(Game game) {
		newGame(game);
		// game.addGameStatusListener(topPanel);
		game.addGameStatusListener(gameFieldPanel);
	}

}