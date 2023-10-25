package gameoflife.hmi;

import gameoflife.constants.HMIConstants;
import gameoflife.hmi.enums.DrawAction;
import gameoflife.hmi.panel.BottomPanel;
import gameoflife.hmi.panel.FullFrameContentPanel;
import gameoflife.hmi.panel.GameBoardPanel;
import gameoflife.hmi.panel.TopPanel;

public class HmiPresenter {

	private GameOfLifeMainViewFrame gameOfLifeMainViewFrame;
	private TopPanel topPanel;
	private GameBoardPanel gameBoardPanel;
	private BottomPanel bottomPanel;
	private FullFrameContentPanel frameContentPanel;
	private boolean panInProgress;

	private int cellSizeInPixels;

	private DrawAction drawActionInProgress = null;

	public HmiPresenter(GameOfLifeMainViewFrame gameOfLifeMainViewFrame, TopPanel topPanel,
			GameBoardPanel gameBoardPanel, BottomPanel bottomPanel, FullFrameContentPanel frameContentPanel) {

		this.gameOfLifeMainViewFrame = gameOfLifeMainViewFrame;
		this.topPanel = topPanel;
		this.gameBoardPanel = gameBoardPanel;
		this.bottomPanel = bottomPanel;
		this.frameContentPanel = frameContentPanel;

		topPanel.setHmiPresenter(this);
		gameBoardPanel.setHmiPresenter(this);
		bottomPanel.setHmiPresenter(this);
		frameContentPanel.setHmiPresenter(this);

		setCellSizeInPixel(HMIConstants.INITIAL_CELL_SIZE_IN_PIXELS);
		hideGrid();
		setPanInProgress(false);
	}

	public void setCellSizeInPixel(int cellSizeInPixels) {
		this.cellSizeInPixels = cellSizeInPixels;
		gameBoardPanel.setCellSizeInPixel(cellSizeInPixels);
		topPanel.setCellSizeInPixel(cellSizeInPixels);
	}

	public void displayGrid() {
		gameBoardPanel.displayGrid();
		topPanel.onGridDisplayed();
	}

	public void hideGrid() {
		gameBoardPanel.hideGrid();
		topPanel.onGridHidden();
	}

	public int getCellSizeInPixels() {
		return cellSizeInPixels;
	}

	public void setDrawActionInProgress(DrawAction drawActionInProgress) {
		this.drawActionInProgress = drawActionInProgress;
		gameBoardPanel.setDrawActionInProgress(drawActionInProgress);
	}

	public DrawAction getDrawActionInProgress() {
		return drawActionInProgress;
	}

	public void togglePanInProgress() {
		setPanInProgress(!panInProgress);
	}

	protected void setPanInProgress(boolean panInProgress) {
		this.panInProgress = panInProgress;
		gameBoardPanel.setPanInProgress(panInProgress);

		if (panInProgress) {
			setDrawActionInProgress(null);
		}

	}

	public boolean isPanInProgress() {
		return panInProgress;
	}

}
