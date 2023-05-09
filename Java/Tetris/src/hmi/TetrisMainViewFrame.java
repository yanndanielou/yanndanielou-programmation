package hmi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TetrisMainViewFrame extends JFrame {

	private static final long serialVersionUID = 1443136088686746460L;

	private static final Logger LOGGER = LogManager.getLogger(TetrisMainViewFrame.class);

	/*
	 * private SkyPanel skyPanel = null; private AllyBoatPanel allyBoatPanel = null;
	 * private UnderWaterPanel underWaterPanel = null; private OceanBedPanel
	 * oceanBedPanel = null;
	 */
	private MainViewMenuBarManager mainViewMenuBarManager;

	private JPanel panel_content = new JPanel();

	private GameFieldPanel gameFieldPanel;

	public TetrisMainViewFrame() {
		super("Sink submarines");
		setResizable(false);
		mainViewMenuBarManager = new MainViewMenuBarManager(this);
	}

	public void initialize_from_game_board_data_model() {
		// Create and set up the window.

		gameFieldPanel = new GameFieldPanel(this);

		gameFieldPanel.initialize_display();
		gameFieldPanel.setLocation(0, 0);
		panel_content.setLayout(null);
		panel_content.add(gameFieldPanel);

		panel_content.setSize(gameFieldPanel.getSize());
		panel_content.setLocation(0, 0);

		this.setContentPane(panel_content);

		this.setSize(gameFieldPanel.getWidth() + 20,
				gameFieldPanel.getHeight() + mainViewMenuBarManager.getMenuBar().getHeight() + 40);
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
			setIconImage(application_buffered_image);
		} catch (IOException e1) {
			LOGGER.info("Could not define application icon " + application_image_path);			
		}
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

		this.pack();

		// Display the window.
		this.setVisible(true);
		this.setResizable(false);

	}

	public MainViewMenuBarManager getMainViewMenuBarManager() {
		return mainViewMenuBarManager;
	}

}