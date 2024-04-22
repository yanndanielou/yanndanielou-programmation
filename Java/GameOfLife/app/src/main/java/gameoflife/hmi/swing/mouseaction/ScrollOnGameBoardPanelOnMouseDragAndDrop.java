package gameoflife.hmi.swing.mouseaction;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.hmi.swing.panel.CellPanel;

public class ScrollOnGameBoardPanelOnMouseDragAndDrop extends MouseAdapter {
	private static final Logger LOGGER = LogManager.getLogger(ScrollOnGameBoardPanelOnMouseDragAndDrop.class);

	private CellPanel cellPanel;
	private Point origin;

	public ScrollOnGameBoardPanelOnMouseDragAndDrop(CellPanel cellPanel, boolean panInProgress) {
		this.cellPanel = cellPanel;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		origin = new Point(e.getPoint());
		LOGGER.info(() -> "Mouse pressed on " + origin);

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		LOGGER.info(() -> "mouseDragged " + e);
		int eventX = e.getX();
		int eventY = e.getY();
		//cellPanel.scrollRectToVisible(null);
		// cellPanel.scrollRectToVisible(null);

		if (origin != null) {
			JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class,
					cellPanel.getGameBoardPanel());
			if (viewPort != null) {
				int deltaX = origin.x - e.getX();
				int deltaY = origin.y - e.getY();

				Rectangle view = viewPort.getViewRect();
				LOGGER.info(() -> "View initial rectangle: " + view);
				LOGGER.info(() -> "deltaX:" + deltaX + " , deltaY:" + deltaY);
				view.x += deltaX;
				view.y += deltaY;

				//view.x += eventX;
				//view.y += eventY;

				cellPanel.scrollRectToVisible(view);
				
				origin = null;

			}
		}
	}

}
