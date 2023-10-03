package gameoflife.hmi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import gameoflife.gameboard.Cell;

public class CellMouseMotionListener implements MouseMotionListener, MouseListener {
	private Cell cell;
	private JPanel cellPanel;
	private GameBoardPanel gameBoardPanel;

	public CellMouseMotionListener(GameBoardPanel gameBoardPanel, Cell cell, JPanel cellPanel) {
		this.cell = cell;
		this.cellPanel = cellPanel;
		this.gameBoardPanel = gameBoardPanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// document why this method is empty

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

		HmiPresenter hmiPresenter = gameBoardPanel.getHmiPresenter();
		if (hmiPresenter != null) {

			DrawAction drawActionInProgress = hmiPresenter.getDrawActionInProgress();
			if (drawActionInProgress != null) {

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
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
