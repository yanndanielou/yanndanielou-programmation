package gameoflife.hmi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.constants.HMIConstants;
import gameoflife.game.Game;
import gameoflife.hmi.interfaces.GameOfLifeMainViewGeneric;
import gameoflife.hmi.menubar.MainViewMenuBarManager;
import gameoflife.hmi.panel.BottomPanel;
import gameoflife.hmi.panel.FullFrameContentPanel;
import gameoflife.hmi.panel.GameBoardPanel;
import gameoflife.hmi.panel.TopPanel;

public class GameOfLifeMainViewFrame extends JFrame implements GameOfLifeMainViewGeneric {

	private static final long serialVersionUID = 1443136088686746460L;

	private static final Logger LOGGER = LogManager.getLogger(GameOfLifeMainViewFrame.class);

	private MainViewMenuBarManager mainViewMenuBarManager;

	private TopPanel topPanel;
	private GameBoardPanel gameBoardPanel;
	private BottomPanel bottomPanel;

	//private JScrollPane gameFieldScrollPane;

	private FullFrameContentPanel fullFrameContent;
	private HmiPresenter hmiPresenter;

	protected Dimension frameExtraDimensionComparedToInputTopLevelPanel;

	public GameOfLifeMainViewFrame() {
		super("DesktopGameOfLife");
		mainViewMenuBarManager = new MainViewMenuBarManager(this);
	}

	private void setApplicationIcon() {

		BufferedImage applicationBufferedImage = null;
		String applicationImagePath = "ApplicationIcon.png";

		InputStream resourceInputStream = getClass().getResourceAsStream(applicationImagePath);
		LOGGER.info(() -> "resourceInputStream:" + resourceInputStream);

		try {
			applicationBufferedImage = ImageIO.read(resourceInputStream);
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
		setApplicationIcon();

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainViewMenuBarManager.createMenu();

		setMinimumSize(HMIConstants.MINIMUM_WINDOW_DIMENSION);
		pack();
		setLocationRelativeTo(null);
	}

	public MainViewMenuBarManager getMainViewMenuBarManager() {
		return mainViewMenuBarManager;
	}

	private void newGame(Game game) {
		this.setLayout(new BorderLayout());

		topPanel = new TopPanel(this, game);

		gameBoardPanel = new GameBoardPanel(this, game);

		bottomPanel = new BottomPanel(this, game);

		fullFrameContent = new FullFrameContentPanel(this, topPanel, gameBoardPanel, bottomPanel);
		add(fullFrameContent);

		hmiPresenter = new HmiPresenter(this, topPanel, gameBoardPanel, bottomPanel, fullFrameContent);

		this.addKeyListener(new KeyBoardInputs(this, hmiPresenter));

		pack();

		// Center to screen
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void removeGameFieldPanel() {
		remove(gameBoardPanel);
	}

	public void removeTopPanel() {
		remove(topPanel);
	}

	@Override
	public void registerToGame(Game game) {
		newGame(game);
		game.addGameStatusListener(gameBoardPanel);
		game.addGameStatusListener(topPanel);
	}

	public void removeBottomPanel() {
		// TODO Auto-generated method stub

	}

	public GameBoardPanel getGameBoardPanel() {
		return gameBoardPanel;
	}

	public HmiPresenter getHmiPresenter() {
		return hmiPresenter;
	}
}

/*		
Dimension initialFrameSize = this.getSize();



frameExtraDimensionComparedToInputTopLevelPanel = new Dimension(
		(int) (initialFrameSize.getWidth() - HMIConstants.MINIMUM_WINDOW_DIMENSION.getWidth()),
		(int) (initialFrameSize.getHeight() - HMIConstants.MINIMUM_WINDOW_DIMENSION.getHeight()));

this.addComponentListener(new ComponentAdapter() {
	@Override
	public void componentResized(ComponentEvent evt) {
		Dimension newFrameSize = evt.getComponent().getSize();

		Dimension newFrameContentSize = new Dimension(
				(int) (newFrameSize.getWidth() - frameExtraDimensionComparedToInputTopLevelPanel.getWidth()),
				(int) (newFrameSize.getHeight() - frameExtraDimensionComparedToInputTopLevelPanel.getHeight()));

		gameFieldScrollPane.setBounds(0, 0, (int) newFrameContentSize.getWidth(),
				(int) newFrameContentSize.getHeight());

		//level1LayeredPane.setPreferredSize(newFrameContentSize);
		/* @formatter:off
		gameFieldScrollPane.repaint();
		level1LayeredPane.repaint();
		gameFieldPanel.repaint();
		 @formatter:on*/
/*
 * } });
 */