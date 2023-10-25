package gameoflife.hmi.panel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.gameboard.Cell;
import gameoflife.hmi.enums.DrawAction;
import gameoflife.hmi.mouseaction.DrawCellWithMouse;
import gameoflife.hmi.mouseaction.ScrollOnGameBoardPanelOnMouseDragAndDrop;

public class CellPanel extends BasePanel {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(CellPanel.class);

	private static final long serialVersionUID = -4722225029326344692L;

	private GameBoardPanel gameBoardPanel;
	private Cell cell;
	private DrawCellWithMouse drawCellWithMouse;
	private ScrollOnGameBoardPanelOnMouseDragAndDrop scrollOnGameBoardPanelOnMouseDragAndDrop;

	public CellPanel(GameBoardPanel gameBoardPanel, Cell cell) {
		this.cell = cell;
		this.gameBoardPanel = gameBoardPanel;
	}

	public Cell getCell() {
		return cell;
	}

	public void setDrawActionInProgress(DrawAction drawActionInProgress) {
		if (drawCellWithMouse != null) {
			removeMouseListener(drawCellWithMouse);
		}
		if (drawActionInProgress != null) {
			drawCellWithMouse = new DrawCellWithMouse(this, drawActionInProgress);
			addMouseListener(drawCellWithMouse);
		}

	}

	public void setPanInProgress(boolean panInProgress) {

		if (scrollOnGameBoardPanelOnMouseDragAndDrop != null) {
			removeMouseMotionListener(scrollOnGameBoardPanelOnMouseDragAndDrop);
			removeMouseListener(scrollOnGameBoardPanelOnMouseDragAndDrop);
		}
		if (panInProgress) {
			scrollOnGameBoardPanelOnMouseDragAndDrop = new ScrollOnGameBoardPanelOnMouseDragAndDrop(this,
					panInProgress);
			addMouseMotionListener(scrollOnGameBoardPanelOnMouseDragAndDrop);
			addMouseListener(scrollOnGameBoardPanelOnMouseDragAndDrop);
		}
	}

	public GameBoardPanel getGameBoardPanel() {
		return gameBoardPanel;
	}

}
