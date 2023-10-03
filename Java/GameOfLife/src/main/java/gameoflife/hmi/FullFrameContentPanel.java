package gameoflife.hmi;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gameoflife.constants.HMIConstants;

public class FullFrameContentPanel extends JPanel {

	private TopPanel topPanel;
	private GameBoardPanel gameBoardPanel;
	private BottomPanel bottomPanel;
	private GameOfLifeMainViewFrame desktopGameOfLifeMainViewFrame;

	private HmiPresenter hmiPresenter;

	public FullFrameContentPanel(GameOfLifeMainViewFrame desktopGameOfLifeMainViewFrame, TopPanel topPanel,
			GameBoardPanel gameBoardPanel, BottomPanel bottomPanel) {
		this.desktopGameOfLifeMainViewFrame = desktopGameOfLifeMainViewFrame;
		this.topPanel = topPanel;
		this.gameBoardPanel = gameBoardPanel;
		this.bottomPanel = bottomPanel;

		setLayout(new BorderLayout());

		JPanel topPanelParentPane = new JPanel();
		topPanelParentPane.setLayout(new BoxLayout(topPanelParentPane, BoxLayout.Y_AXIS));
		topPanelParentPane.add(topPanel);

		add(topPanelParentPane, BorderLayout.NORTH);
		add(new JScrollPane(gameBoardPanel));

		JPanel bottomPanelParentPane = new JPanel();
		bottomPanelParentPane.setLayout(new BoxLayout(bottomPanelParentPane, BoxLayout.Y_AXIS));
		bottomPanelParentPane.add(bottomPanel);
		add(bottomPanelParentPane, BorderLayout.SOUTH);
	}

	@Override
	public Dimension getPreferredSize() {
		return HMIConstants.MINIMUM_WINDOW_DIMENSION;
	}


	public void setHmiPresenter(HmiPresenter hmiPresenter) {
		this.hmiPresenter = hmiPresenter;
	}

}
