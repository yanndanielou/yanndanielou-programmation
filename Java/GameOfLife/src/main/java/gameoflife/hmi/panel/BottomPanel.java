package gameoflife.hmi.panel;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.hmi.utils.HMIUtils;
import gameoflife.constants.HMIConstants;
import gameoflife.game.Game;
import gameoflife.game.GameStatusListener;
import gameoflife.hmi.GameOfLifeMainViewFrame;

public class BottomPanel extends BasePanel implements GameStatusListener {

	private static final Logger LOGGER = LogManager.getLogger(BottomPanel.class);

	private static final long serialVersionUID = -4722225029326344692L;

	private JButton playButton;
	private JButton pauseButton;

	private JSpinner playSpeedSpinner;
	private JButton setSpeed1ForPlaySpeedButton;
	private JButton stepForwardButton;

	@SuppressWarnings("unused")
	private GameOfLifeMainViewFrame gameOfLifeMainViewFrame;

	public BottomPanel(GameOfLifeMainViewFrame gameOfLifeMainViewFrame, Game game) {
		this.gameOfLifeMainViewFrame = gameOfLifeMainViewFrame;

		setLayout(null);
		setBackground(HMIConstants.TOP_PANEL_BACKGROUND_COLOR);

		setPreferredSize(new Dimension(gameOfLifeMainViewFrame.getWidth(), HMIConstants.BOTTOM_PANNEL_HEIGHT));

		setSpeed1ForPlaySpeedButton = HMIUtils
				.createJButtonFromImage("src/main/resources/images/SetSpeed1ForPlaySpeedButtonIcon.png");
		setSpeed1ForPlaySpeedButton.setLocation((int) HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth(), 0);
		setSpeed1ForPlaySpeedButton.addActionListener(e -> {
			LOGGER.info(() -> "Set speed 1 for play button actionned");
		});
		add(setSpeed1ForPlaySpeedButton);

		playSpeedSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		playSpeedSpinner.setLocation((int) (setSpeed1ForPlaySpeedButton.getBounds().getMaxX()
				+ HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth()), 0);
		playSpeedSpinner.setSize(50, setSpeed1ForPlaySpeedButton.getHeight());
		playSpeedSpinner.addChangeListener(e -> {
			LOGGER.info(() -> "Play speed changed to : " + playSpeedSpinner.getValue());
			game.setAutoPlaySpeedPerSecond(getAutoPlaySpeedPerSecond());
		});
		add(playSpeedSpinner);

		stepForwardButton = HMIUtils.createJButtonFromImage("src/main/resources/images/StepForwardButtonIcon.png");
		stepForwardButton.setLocation((int) (playSpeedSpinner.getBounds().getMaxX()
				+ HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth()), 0);
		stepForwardButton.addActionListener(e -> {
			LOGGER.info(() -> "Step forward button actionned");
			game.playOneStep();
		});
		add(stepForwardButton);

		playButton = HMIUtils.createJButtonFromImage("src/main/resources/images/PlayButtonIcon.png");
		playButton.setLocation((int) (stepForwardButton.getBounds().getMaxX()
				+ HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth()), 0);
		playButton.addActionListener(e -> {
			LOGGER.info(() -> "Play button actionned");
			game.autoPlay(getAutoPlaySpeedPerSecond());
		});
		add(playButton);

		pauseButton = HMIUtils.createJButtonFromImage("src/main/resources/images/PauseButtonIcon.png");
		pauseButton.setLocation(
				(int) (playButton.getBounds().getMaxX() + HMIConstants.SPACE_BETWEEN_COMMANDS_DIMENSION.getWidth()), 0);
		pauseButton.addActionListener(e -> {
			LOGGER.info(() -> "Pause button actionned");
			game.pause();
		});
		add(pauseButton);
	}

	private int getAutoPlaySpeedPerSecond() {
		Object playSpeedValueAsObject = playSpeedSpinner.getValue();
		return (int) playSpeedValueAsObject;
	}

	@Override
	public void onGameCancelled(Game game) {
		gameOfLifeMainViewFrame.removeBottomPanel();
	}

}
