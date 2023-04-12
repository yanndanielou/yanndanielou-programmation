package hmi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;

public class SinkSubmarinesMainViewFrame extends JFrame {

	private static final long serialVersionUID = 1443136088686746460L;

	// private static final Logger LOGGER =
	// LogManager.getLogger(SinkSubmarinesMainView.class);

	/*
	 * private SkyPanel skyPanel = null; private AllyBoatPanel allyBoatPanel = null;
	 * private UnderWaterPanel underWaterPanel = null; private OceanBedPanel
	 * oceanBedPanel = null;
	 */
	private MainViewMenuBarManager mainViewMenuBarManager;

	private JPanel panel_content = new JPanel();

	private GameBoardPanel gameBoardPanel;

	public SinkSubmarinesMainViewFrame() {
		super("Sink submarines");
		setResizable(false);
		mainViewMenuBarManager = new MainViewMenuBarManager(this);
	}

	public void initialize_from_game_board_data_model() {
		// Create and set up the window.

		gameBoardPanel = new GameBoardPanel(this, "Images/entire_gameboard.png");

		gameBoardPanel.initialize_display();
		gameBoardPanel.setLocation(0, 0);
		panel_content.setLayout(null);
		panel_content.add(gameBoardPanel);

		panel_content.setSize(gameBoardPanel.getSize());
		panel_content.setLocation(0, 0);

		this.setContentPane(panel_content);

		// add(gameBoardPanel);
		// setLayeredPane(gameBoardPanel);

		// gameBoardPanel.setLayer(mainViewMenuBarManager.getMenuBar(), 100);

		/*
		 * topPanel = new TopPanel(pane, gameBoardDataModel.getWidth(),
		 * gameBoardDataModel, null);
		 * 
		 * skyPanel = new SkyPanel(pane, gameBoardDataModel.getWidth(),
		 * gameBoardDataModel, topPanel);
		 * 
		 * allyBoatPanel = new AllyBoatPanel(pane, gameBoardDataModel.getWidth(),
		 * gameBoardDataModel, skyPanel);
		 * 
		 * underWaterPanel = new UnderWaterPanel(pane, gameBoardDataModel.getWidth(),
		 * gameBoardDataModel, allyBoatPanel);
		 * 
		 * oceanBedPanel = new OceanBedPanel(pane, gameBoardDataModel.getWidth(),
		 * gameBoardDataModel, underWaterPanel);
		 */

		this.setSize(gameBoardPanel.getWidth() + 20,
				gameBoardPanel.getHeight() + mainViewMenuBarManager.getMenuBar().getHeight() + 40);
		// this.pack();

		this.addKeyListener(new KeyBoardInputs(this));

	}

	private void setApplicationIcon() {

		BufferedImage application_buffered_image = null;
		File application_image_file = null;
		String application_image_path = "Images/game_icon.png";

		application_image_file = new File(application_image_path);
		try {
			application_buffered_image = ImageIO.read(application_image_file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setIconImage(application_buffered_image);
	}

	/*
	 * private void set_look_and_field() { // Use an appropriate Look and Feel try {
	 * //
	 * UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
	 * ); UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); }
	 * catch (UnsupportedLookAndFeelException ex) { ex.printStackTrace(); } catch
	 * (IllegalAccessException ex) { ex.printStackTrace(); } catch
	 * (InstantiationException ex) { ex.printStackTrace(); } catch
	 * (ClassNotFoundException ex) { ex.printStackTrace(); }
	 * 
	 * // Turn off metal's use of bold fonts UIManager.put("swing.boldMetal",
	 * Boolean.FALSE);
	 * 
	 * }
	 */
	/**
	 * Create the GUI and show it. For thread safety, this method is invoked from
	 * the event dispatch thread.
	 */
	public void createAndShowGUI() {

		// set_look_and_field();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainViewMenuBarManager.createMenu();

		setApplicationIcon();

		// this.setSize(800, 600);
		this.pack();

		// Display the window.

		this.setVisible(true);
		this.setResizable(false);

	}

	public MainViewMenuBarManager getMainViewMenuBarManager() {
		return mainViewMenuBarManager;
	}

	public void setAlly_boat(AllyBoat ally_boat) {
		// allyBoatPanel.setAlly_boat(ally_boat);

	}

	public void register_to_ally_boat(AllyBoat ally_boat) {
		ally_boat.add_movement_listener(gameBoardPanel);
	}

	public void register_to_simple_submarine(SimpleSubMarine submarine) {
		// submarine.add_movement_listener(underWaterPanel);
		submarine.add_movement_listener(gameBoardPanel);
	}

	public void register_to_yellow_submarine(YellowSubMarine submarine) {
		// submarine.add_movement_listener(underWaterPanel);
		submarine.add_movement_listener(gameBoardPanel);
	}

	public void register_to_floating_submarine_bomb(FloatingSubmarineBomb sumbmarineBomb) {
		// sumbmarineBomb.add_movement_listener(allyBoatPanel);
//		sumbmarineBomb.add_movement_listener(underWaterPanel);
		sumbmarineBomb.add_movement_listener(gameBoardPanel);
	}

	public void register_to_simple_ally_bomb(SimpleAllyBomb simpleAllyBomb) {

		// simpleAllyBomb.add_movement_listener(allyBoatPanel);
		// simpleAllyBomb.add_movement_listener(underWaterPanel);
		// simpleAllyBomb.add_movement_listener(oceanBedPanel);
		// simpleAllyBomb.add_movement_listener(topPanel);
		simpleAllyBomb.add_movement_listener(gameBoardPanel);
	}

	public void register_to_simple_submarine_bomb(SimpleSubmarineBomb sumbmarineBomb) {

		// sumbmarineBomb.add_movement_listener(allyBoatPanel);
		// sumbmarineBomb.add_movement_listener(underWaterPanel);
		sumbmarineBomb.add_movement_listener(gameBoardPanel);

	}

	public void register_to_game(Game game) {
		// game.add_game_listener(topPanel);
		game.add_game_listener(gameBoardPanel);
	}

	public GameBoardPanel getGameBoardPanel() {
		return gameBoardPanel;
	}

}