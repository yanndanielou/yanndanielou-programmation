package gameoflife.hmi;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gameoflife.constants.HMIConstants;

public class TopPanel extends JPanel {

	private static final long serialVersionUID = -4722225029326344692L;

	private GameOfLifeMainViewFrame desktopGameOfLifeMainViewFrame;

	private JLabel scoreStaticLabel;
	private JLabel currentScoreTextLabel;


	public TopPanel(GameOfLifeMainViewFrame desktopGameOfLifeMainViewFrame, int width, int height) {
		this.desktopGameOfLifeMainViewFrame = desktopGameOfLifeMainViewFrame;

		setSize(width, height);

		setLayout(null);
		setBackground(HMIConstants.TOP_PANEL_BACKGROUND_COLOR);


	}


}
