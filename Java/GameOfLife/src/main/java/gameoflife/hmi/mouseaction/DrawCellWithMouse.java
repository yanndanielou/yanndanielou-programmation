package gameoflife.hmi.mouseaction;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.gameboard.Cell;
import gameoflife.hmi.DrawAction;
import gameoflife.hmi.HmiPresenter;
import gameoflife.hmi.panel.CellPanel;
import gameoflife.hmi.panel.GameBoardPanel;

public class DrawCellWithMouse implements MouseListener {
	private static final Logger LOGGER = LogManager.getLogger(DrawCellWithMouse.class);

	private CellPanel cellPanel;
	private DrawAction drawActionInProgress;

	public DrawCellWithMouse(CellPanel cellPanel, DrawAction drawActionInProgress) {
		this.cellPanel = cellPanel;
		this.drawActionInProgress = drawActionInProgress;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		boolean mouseButton1IsPressedWithMask = (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0;

		if (drawActionInProgress != null && mouseButton1IsPressedWithMask) {
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
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
