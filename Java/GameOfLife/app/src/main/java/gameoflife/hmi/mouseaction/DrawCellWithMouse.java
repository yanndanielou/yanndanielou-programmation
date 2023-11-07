package gameoflife.hmi.mouseaction;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.game.PauseReason;
import gameoflife.gameboard.Cell;
import gameoflife.hmi.enums.DrawAction;
import gameoflife.hmi.panel.CellPanel;

public class DrawCellWithMouse implements MouseListener {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(DrawCellWithMouse.class);

	private CellPanel cellPanel;
	private DrawAction drawActionInProgress;

	public DrawCellWithMouse(CellPanel cellPanel, DrawAction drawActionInProgress) {
		this.cellPanel = cellPanel;
		this.drawActionInProgress = drawActionInProgress;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		actionCellPanel();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		LOGGER.info(() -> "mousePressed " + e);
		cellPanel.getGameBoardPanel().getGameBoard().getGame().addPauseReason(PauseReason.CELL_DRAWING_IN_PROGRESS);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		LOGGER.info(() -> "mouseReleased " + e);
		cellPanel.getGameBoardPanel().getGameBoard().getGame().removePauseReason(PauseReason.CELL_DRAWING_IN_PROGRESS);
	}
	
	private void actionCellPanel() {
		Cell cell = cellPanel.getCell();

		switch (drawActionInProgress) {
		case SET_ALIVE:
			cell.setAlive();
			break;
		case SET_DEAD:
			cell.setDead();
			break;
		case TOGGLE_STATE:
			cell.toggleState();
			break;
		default:
			break;

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		boolean mouseButton1IsPressedWithMask = (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0;

		if (drawActionInProgress != null && mouseButton1IsPressedWithMask) {
			actionCellPanel();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
