package gameoflife.hmi.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gameoflife.constants.HMIConstants;
import gameoflife.hmi.GameOfLifeMainViewFrame;
import gameoflife.hmi.HmiPresenter;
import gameoflife.hmi.KeyBoardInputs;
import gameoflife.hmi.mouseaction.FullFrameContentPanelMouseAdapter;

public class FullFrameContentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4499026061683356916L;
	
	private HmiPresenter hmiPresenter;

	public FullFrameContentPanel(GameOfLifeMainViewFrame desktopGameOfLifeMainViewFrame, TopPanel topPanel,
			GameBoardPanel gameBoardPanel, BottomPanel bottomPanel) {
	
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
	
		this.addKeyListener(new KeyBoardInputs(this, hmiPresenter));
		scrollPane.addKeyListener(new KeyBoardInputs(this, hmiPresenter));

	}

	@Override
	public Dimension getPreferredSize() {
		return HMIConstants.MINIMUM_WINDOW_DIMENSION;
	}


	public void setHmiPresenter(HmiPresenter hmiPresenter) {
		this.hmiPresenter = hmiPresenter;
	}

}
