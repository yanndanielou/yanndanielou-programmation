package gameoflife.hmi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gameoflife.constants.HMIConstants;
import gameoflife.hmi.panel.BottomPanel;
import gameoflife.hmi.panel.GameBoardPanel;
import gameoflife.hmi.panel.TopPanel;

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
		JScrollPane scrollPane = new JScrollPane(gameBoardPanel);
		add(scrollPane);

		JPanel bottomPanelParentPane = new JPanel();
		bottomPanelParentPane.setLayout(new BoxLayout(bottomPanelParentPane, BoxLayout.Y_AXIS));
		bottomPanelParentPane.add(bottomPanel);
		add(bottomPanelParentPane, BorderLayout.SOUTH);
		
		FullFrameContentPanelMouseAdapter frameContentPanelMouseAdapter = new FullFrameContentPanelMouseAdapter(this);
		

		scrollPane.addMouseListener(frameContentPanelMouseAdapter);
		scrollPane.addMouseMotionListener(frameContentPanelMouseAdapter);
	
	}

	@Override
	public Dimension getPreferredSize() {
		return HMIConstants.MINIMUM_WINDOW_DIMENSION;
	}


	public void setHmiPresenter(HmiPresenter hmiPresenter) {
		this.hmiPresenter = hmiPresenter;
	}

}
