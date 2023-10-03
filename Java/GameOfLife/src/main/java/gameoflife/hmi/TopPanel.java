package gameoflife.hmi;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.constants.HMIConstants;
import main.common.hmi.utils.HMIUtils;

public class TopPanel extends JPanel {
	
	private static final Logger LOGGER = LogManager.getLogger(TopPanel.class);


	private static final long serialVersionUID = -4722225029326344692L;

	private JButton panButton;

	private JButton drawButton;

	private JButton playButton;
	private JButton pauseButton;

	private JSlider zoomLevelSlider;

	private JButton showGridButton;

	
	@SuppressWarnings("unused")
	private GameOfLifeMainViewFrame desktopGameOfLifeMainViewFrame;

	public TopPanel(GameOfLifeMainViewFrame desktopGameOfLifeMainViewFrame) {
		this.desktopGameOfLifeMainViewFrame = desktopGameOfLifeMainViewFrame;

		setLayout(null);
		setBackground(HMIConstants.TOP_PANEL_BACKGROUND_COLOR);
		
		panButton = HMIUtils.createJButtonFromImage("src/main/resources/images/PanButtonIcon.png");
		panButton.setLocation((int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(),
				(int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getHeight());
		panButton.addActionListener(e -> {
			LOGGER.info(() -> "Pan button actionned");
		});
		add(panButton);

		drawButton = HMIUtils.createJButtonFromImage("src/main/resources/images/DrawButtonIcon.png");
		drawButton.setLocation(
				(int) panButton.getBounds().getMaxX() + (int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(),
				(int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getHeight());
		drawButton.addActionListener(e -> {
			LOGGER.info(() -> "Draw button actionned");
		});
		add(drawButton);

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
		add(zoomLevelSlider);
		zoomLevelSlider.addChangeListener(e -> {
		});

		showGridButton = HMIUtils.createJButtonFromImage("src/main/resources/images/GridButtonIcon.png");
		showGridButton.setLocation(
				(int) zoomLevelSlider.getBounds().getMaxX()
						+ (int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(),
				(int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getHeight());
		showGridButton.addActionListener(e -> {
			LOGGER.info(() -> "Show grid button actionned");
			//gameOfLifeMainViewFrame.getGameBoardPanel().displayGrid();
		});
		add(showGridButton);

	}

}
