package gameoflife.hmi;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.constants.HMIConstants;
import main.common.hmi.utils.HMIUtils;

public class MainViewTopPanel extends JLayeredPane {

	private static final Logger LOGGER = LogManager.getLogger(MainViewTopPanel.class);

	private JButton panButton;

	private JButton drawButton;

	private JButton playButton;
	private JButton pauseButton;

	private JSlider zoomLevelSlider;

	private JButton showGridButton;

	GameOfLifeMainViewFrame gameOfLifeMainViewFrame;

	private enum LAYERS_ORDERED_FROM_TOP_TO_BACK {
		BUTTONS, CELLS;
	}

	public MainViewTopPanel(GameOfLifeMainViewFrame gameOfLifeMainViewFrame, JScrollPane gameFieldScrollPane) {

		this.gameOfLifeMainViewFrame = gameOfLifeMainViewFrame;

		setPreferredSize(HMIConstants.MINIMUM_WINDOW_DIMENSION);

		add(gameFieldScrollPane, LAYERS_ORDERED_FROM_TOP_TO_BACK.CELLS, 0);

		panButton = HMIUtils.createJButtonFromImage("src/main/resources/images/PanButtonIcon.png");
		panButton.setLocation((int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(),
				(int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getHeight());
		panButton.addActionListener(e -> {
			LOGGER.info(() -> "Pan button actionned");
		});
		add(panButton, LAYERS_ORDERED_FROM_TOP_TO_BACK.BUTTONS, 0);

		drawButton = HMIUtils.createJButtonFromImage("src/main/resources/images/DrawButtonIcon.png");
		drawButton.setLocation(
				(int) panButton.getBounds().getMaxX() + (int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(),
				(int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getHeight());
		drawButton.addActionListener(e -> {
			LOGGER.info(() -> "Draw button actionned");
		});
		add(drawButton, LAYERS_ORDERED_FROM_TOP_TO_BACK.BUTTONS, 0);

		zoomLevelSlider = new JSlider(SwingConstants.HORIZONTAL, 5, 200, HMIConstants.CELL_HEIGHT_IN_PIXELS);
		zoomLevelSlider.setLocation(
				(int) drawButton.getBounds().getMaxX() + (int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(),
				(int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getHeight());
		zoomLevelSlider.setMajorTickSpacing(10);
		zoomLevelSlider.setMinorTickSpacing(1);
		zoomLevelSlider.setPaintTicks(true);
		zoomLevelSlider.setPaintLabels(true);
		zoomLevelSlider.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font = new Font("Serif", Font.ITALIC, 15);
		zoomLevelSlider.setFont(font);
		zoomLevelSlider.setSize(new Dimension(100, drawButton.getHeight()));
		add(zoomLevelSlider, LAYERS_ORDERED_FROM_TOP_TO_BACK.BUTTONS, 0);
		zoomLevelSlider.addChangeListener(e -> {
		});

		showGridButton = HMIUtils.createJButtonFromImage("src/main/resources/images/GridButtonIcon.png");
		showGridButton.setLocation(
				(int) zoomLevelSlider.getBounds().getMaxX()
						+ (int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(),
				(int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getHeight());
		showGridButton.addActionListener(e -> {
			LOGGER.info(() -> "Show grid button actionned");
			gameOfLifeMainViewFrame.getGameBoardPanel().displayGrid();
		});
		add(showGridButton, LAYERS_ORDERED_FROM_TOP_TO_BACK.BUTTONS, 0);

	}

}
