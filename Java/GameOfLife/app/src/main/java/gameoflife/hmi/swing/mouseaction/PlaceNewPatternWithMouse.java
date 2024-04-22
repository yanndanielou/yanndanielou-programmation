package gameoflife.hmi.swing.mouseaction;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.game.Game;
import gameoflife.game.PauseReason;
import gameoflife.gameboard.Cell;
import gameoflife.hmi.swing.panel.CellPanel;
import gameoflife.patterns.Pattern;

public class PlaceNewPatternWithMouse implements MouseListener {
	private static final Logger LOGGER = LogManager.getLogger(PlaceNewPatternWithMouse.class);

	private CellPanel cellPanel;
	private Pattern patternToDraw;

	public PlaceNewPatternWithMouse(CellPanel cellPanel, Pattern patternToDraw) {
		this.cellPanel = cellPanel;
		this.patternToDraw = patternToDraw;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		placePatternFromTopLeft();
	}

	private void placePatternFromTopLeft() {
		Game game = cellPanel.getGameBoardPanel().getGameBoard().getGame();
		Cell cell = cellPanel.getCell();
		LOGGER.info(() -> "Place new pattern with top left position:" + cell);
		game.applyPattern(patternToDraw, cell.getXAsInt(), cell.getYAsInt());
		cellPanel.getHmiPresenter().placePatternOnGameBoard(null);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		LOGGER.info(() -> "mousePressed " + e);
		cellPanel.getGameBoardPanel().getGameBoard().getGame().addPauseReason(PauseReason.PLACE_PATTERN_IN_PROGRESS);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		LOGGER.info(() -> "mouseReleased " + e);
		cellPanel.getGameBoardPanel().getGameBoard().getGame().removePauseReason(PauseReason.PLACE_PATTERN_IN_PROGRESS);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
