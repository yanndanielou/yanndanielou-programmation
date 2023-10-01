package gameoflife.hmi;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JSlider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.constants.HMIConstants;
import main.common.hmi.utils.HMIUtils;

public class MainViewPanel extends JLayeredPane {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MainViewPanel.class);

	private static final long serialVersionUID = -1541008040602802454L;

	private JButton panButton;

	private JButton drawButton;

	private JButton playButton;
	private JButton pauseButton;

	private JSlider zoomLevelSlider;

	private GameBoardPanel gameBoardPanel;

	private enum LAYERS_ORDERED_FROM_TOP_TO_BACK {
		BUTTONS, CELLS;
	}

	public MainViewPanel(GameOfLifeMainViewFrame towerDefenseMainViewFrame, GameBoardPanel gameBoardPanel) {
		setLayout(null);
		setPreferredSize(gameBoardPanel.getPreferredSize());
		setVisible(true);

		gameBoardPanel.setLocation(0,0);
		gameBoardPanel.setVisible(true);
		add(gameBoardPanel, LAYERS_ORDERED_FROM_TOP_TO_BACK.BUTTONS);

		panButton = HMIUtils.createJButtonFromImage("src/main/resources/images/PanButtonIcon.png");
		panButton.setLocation((int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(),
				(int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getHeight());
		add(panButton, LAYERS_ORDERED_FROM_TOP_TO_BACK.BUTTONS);

	}

}
