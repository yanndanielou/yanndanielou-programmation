package gameoflife.hmi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.constants.HMIConstants;
import gameoflife.game.Game;
import main.common.hmi.utils.HMIUtils;

public class GameOfLifeMainViewFrame extends JFrame implements GameOfLifeMainViewGeneric {

	private static final long serialVersionUID = 1443136088686746460L;

	private static final Logger LOGGER = LogManager.getLogger(GameOfLifeMainViewFrame.class);

	private MainViewMenuBarManager mainViewMenuBarManager;

	private GameBoardPanel gameFieldPanel;
	private TopPanel topPanel;

	// private MainViewPanel mainViewPanel;

	private JScrollPane gameFieldScrollPane;

	JPanel topLevelPanelForJFrame;
	JLayeredPane level1LayeredPane;

	private JButton panButton;

	protected Dimension frameExtraDimensionComparedToInputTopLevelPanel;

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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainViewMenuBarManager.createMenu();
		this.addKeyListener(new KeyBoardInputs(this));

		setLocationRelativeTo(null);
	}

	public MainViewMenuBarManager getMainViewMenuBarManager() {
		return mainViewMenuBarManager;
	}

	private void newGame(Game game) {

		gameFieldPanel = new GameBoardPanel(this);
		gameFieldPanel.initializeGamefield(game.getGameBoard());

		gameFieldPanel.setPreferredSize(gameFieldPanel.getSize());
		gameFieldPanel.setBounds(0, 0, (int) gameFieldPanel.getSize().getWidth(),
				(int) gameFieldPanel.getSize().getHeight());

		// mainViewPanel = new MainViewPanel(this, gameFieldPanel);

		topLevelPanelForJFrame = new JPanel();

		gameFieldScrollPane = new JScrollPane(gameFieldPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		// gameFieldScrollPane.setPreferredSize(HMIConstants.MINIMUM_WINDOW_DIMENSION);
		// gameFieldScrollPane.setLocation(0, 0);
		gameFieldScrollPane.setBounds(0, 0, (int) HMIConstants.MINIMUM_WINDOW_DIMENSION.getWidth(),
				(int) HMIConstants.MINIMUM_WINDOW_DIMENSION.getHeight());

		panButton = HMIUtils.createJButtonFromImage("src/main/resources/images/PanButtonIcon.png");
		panButton.setLocation((int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(),
				(int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getHeight());

		// mainViewContentPane.add(panButton, 0);

		level1LayeredPane = new JLayeredPane();
		level1LayeredPane.setPreferredSize(HMIConstants.MINIMUM_WINDOW_DIMENSION);

		level1LayeredPane.add(gameFieldScrollPane, 50, 0);
		level1LayeredPane.add(panButton, 51, 0);

		/*
		 * topLevelPanelForJFrame.setLayout(null);
		 * topLevelPanelForJFrame.setSize(getSize());
		 */
		topLevelPanelForJFrame.add(level1LayeredPane, BorderLayout.CENTER);
		// topLevelPanelForJFrame.add(level1LayeredPane);

		setContentPane(topLevelPanelForJFrame);

		setMinimumSize(HMIConstants.MINIMUM_WINDOW_DIMENSION);
		pack();

		Dimension initialFrameSize = this.getSize();
		frameExtraDimensionComparedToInputTopLevelPanel = new Dimension(
				(int) (initialFrameSize.getWidth() - HMIConstants.MINIMUM_WINDOW_DIMENSION.getWidth()),
				(int) (initialFrameSize.getHeight() - HMIConstants.MINIMUM_WINDOW_DIMENSION.getHeight()));

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent evt) {
				Dimension newFrameSize = evt.getComponent().getSize();
				
				Dimension newFrameContentSize = new Dimension((int) (newFrameSize.getWidth() - frameExtraDimensionComparedToInputTopLevelPanel.getWidth()),
						(int) (newFrameSize.getHeight()- frameExtraDimensionComparedToInputTopLevelPanel.getHeight()));

				gameFieldScrollPane.setBounds(0, 0,
						(int) newFrameContentSize.getWidth(),
						(int) newFrameContentSize.getHeight());

				level1LayeredPane.setPreferredSize(newFrameContentSize);

				// level1LayeredPane.setBounds(0, 0, (int) size.getWidth(), (int)
				// size.getHeight());
				// repaint();
			}
		});

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