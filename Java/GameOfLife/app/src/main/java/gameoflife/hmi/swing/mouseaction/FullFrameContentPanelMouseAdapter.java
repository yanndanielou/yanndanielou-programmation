package gameoflife.hmi.swing.mouseaction;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.hmi.swing.panel.FullFrameContentPanel;

public class FullFrameContentPanelMouseAdapter extends MouseAdapter {
	private static final Logger LOGGER = LogManager.getLogger(FullFrameContentPanelMouseAdapter.class);

	private Point origin;

	private FullFrameContentPanel fullFrameContentPanel;

	public FullFrameContentPanelMouseAdapter(FullFrameContentPanel fullFrameContentPanel) {
		this.fullFrameContentPanel = fullFrameContentPanel;

	}

	@Override
	public void mousePressed(MouseEvent e) {
		origin = new Point(e.getPoint());
		LOGGER.info(() -> "Mouse pressed (drag) in " + origin);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		LOGGER.info(() -> "mouseReleased " + e);

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (origin != null) {
			JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, fullFrameContentPanel);
			if (viewPort != null) {
				int deltaX = origin.x - e.getX();
				int deltaY = origin.y - e.getY();

				Rectangle view = viewPort.getViewRect();
				view.x += deltaX;
				view.y += deltaY;

				fullFrameContentPanel.scrollRectToVisible(view);
				LOGGER.info(() -> "Mouse dragged to " + e.getX() + " " + e.getY());

			}
		}
	}

}
