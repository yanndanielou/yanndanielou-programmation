package hmi;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import game_board.Square;

public class SquareJButtonMouseListener extends MouseAdapter {
	private static final Logger LOGGER = LogManager.getLogger(GameFieldPanel.class);

	private Square square;

	public SquareJButtonMouseListener(Square square) {
		this.square = square;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (square.isHighlighting_neighbours_during_click_in_progress()) {
			square.unhighlight_unrevealed_neighbours_after_click();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1 && square.isDiscovered()) {
			square.highlight_unrevealed_neighbours_because_click_in_progress();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		LOGGER.info("Mouse clicked. Click count:" + e.getClickCount() + " button:" + e.getButton());
		if (SwingUtilities.isRightMouseButton(e)) {
			if (!square.isDiscovered()) {
				GameManager.getInstance().toggle_right_click_square(square);
			}
		} else if (e.getClickCount() == 2) {
			if (square.isDiscovered() && !square.isContains_mine()) {
				boolean reveal_neighbours_if_as_many_neighbor_flags_as_neighbour_mines = GameManager.getInstance()
						.reveal_neighbours_if_as_many_neighbor_flags_as_neighbour_mines(square);
				if (!reveal_neighbours_if_as_many_neighbor_flags_as_neighbour_mines) {

				}
			}
		}

	}

}
