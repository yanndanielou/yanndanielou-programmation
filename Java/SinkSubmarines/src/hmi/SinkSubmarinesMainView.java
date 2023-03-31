package hmi;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import builders.gameboard.GameBoardDataModel;

public class SinkSubmarinesMainView extends JFrame {

	private static final long serialVersionUID = 1443136088686746460L;

	//private static final Logger LOGGER = LogManager.getLogger(SinkSubmarinesMainView.class);

	private TopPanel topPanel = null;
	private SkyPanel skyPanel = null;
	private AllyBoatPanel allyBoatPanel = null;
	private UnderWaterPanel underWaterPanel = null;
	private OceanBedPanel oceanBedPanel = null;
	private MainViewMenuBarManager mainViewMenuBarManager;

	public SinkSubmarinesMainView() {
		super("Sink submarines");
		setResizable(false);
		mainViewMenuBarManager = new MainViewMenuBarManager(this);
	}

	public void initialize_from_game_board_data_model(GameBoardDataModel gameBoardDataModel) {
		// Create and set up the window.
		Container pane = this.getContentPane();
		pane.setLayout(null);

		topPanel = new TopPanel(pane, gameBoardDataModel.getWidth(), gameBoardDataModel, null);

		skyPanel = new SkyPanel(pane, gameBoardDataModel.getWidth(), gameBoardDataModel, topPanel);

		allyBoatPanel = new AllyBoatPanel(pane, gameBoardDataModel.getWidth(), gameBoardDataModel, skyPanel);

		underWaterPanel = new UnderWaterPanel(pane, gameBoardDataModel.getWidth(), gameBoardDataModel, allyBoatPanel);

		oceanBedPanel = new OceanBedPanel(pane, gameBoardDataModel.getWidth(), gameBoardDataModel, underWaterPanel);

		int windows_total_height = topPanel.getHeight() + skyPanel.getHeight()
				+ allyBoatPanel.getHeight() + underWaterPanel.getHeight() + oceanBedPanel.getHeight();
		this.setSize(gameBoardDataModel.getWidth() + 20, windows_total_height+20);

		this.addKeyListener(new KeyBoardInputs(this));

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

	public TopPanel getTopPanel() {
		return topPanel;
	}

	public SkyPanel getSkyPanel() {
		return skyPanel;
	}

	public AllyBoatPanel getAllyBoatPanel() {
		return allyBoatPanel;
	}

	public UnderWaterPanel getUnderWaterPanel() {
		return underWaterPanel;
	}

	public MainViewMenuBarManager getMainViewMenuBarManager() {
		return mainViewMenuBarManager;
	}

	public OceanBedPanel getOceanBedPanel() {
		return oceanBedPanel;
	}

}