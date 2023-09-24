package gameoflife.hmi;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.constants.HMIConstants;
import gameoflife.game.CellListener;
import gameoflife.game.Game;
import gameoflife.game.GameStatusListener;
import gameoflife.gameboard.Cell;
import gameoflife.gameboard.GameBoard;

public class GameBoardPanel extends JPanel implements GameStatusListener, CellListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPanel.class);

	private static final long serialVersionUID = -1541008040602802454L;

	private HashMap<Cell, JButton> gameObjectToLabelMap = new HashMap<>();

	private GameBoard gameBoard;
	private GameOfLifeMainViewFrame towerDefenseMainViewFrame;

	public GameBoardPanel(GameOfLifeMainViewFrame towerDefenseMainViewFrame) {
		this.towerDefenseMainViewFrame = towerDefenseMainViewFrame;
	}

	public void initializeGamefield(GameBoard gameBoard) {
		this.gameBoard = gameBoard;

		setLayout(null);
		setSize(gameBoard.getTotalWidth() * HMIConstants.CELL_WIDTH_IN_PIXELS,
				gameBoard.getTotalHeight() * HMIConstants.CELL_HEIGHT_IN_PIXELS);

		for (Cell cell : gameBoard.getAllGameBoardPointsAsOrderedList().stream().map(Cell.class::cast).toList()) {
			int lineNumber = cell.getRow();
			int columnNumber = cell.getColumn();

			JButton jButton = new JButton();

			gameObjectToLabelMap.put(cell, jButton);

			jButton.setSize(HMIConstants.CELL_WIDTH_IN_PIXELS, HMIConstants.CELL_HEIGHT_IN_PIXELS);

			jButton.setToolTipText("Line " + lineNumber + " column " + columnNumber);
			jButton.setLocation(lineNumber * HMIConstants.CELL_WIDTH_IN_PIXELS,
					columnNumber * HMIConstants.CELL_HEIGHT_IN_PIXELS);

			cell.addGameBoardPointListener(this);

			redrawCell(cell);
			add(jButton);
		}

	}

	private void redrawCell(Cell cell) {
		JButton jButton = gameObjectToLabelMap.get(cell);

		if (cell.isAlive()) {
			jButton.setForeground(Color.BLACK);
		} else {
			jButton.setForeground(Color.WHITE);
		}

	}

	@Override
	public void onListenToGameStatus(Game game) {
		// Auto-generated method stub

	}

	@Override
	public void onGameCancelled(Game game) {
		removeAll();
		towerDefenseMainViewFrame.removeGameFieldPanel();
	}

	@Override
	public void onGameLost(Game game) {
	}

	@Override
	public void onGameWon(Game game) {
		// Auto-generated method stub
	}

	@Override
	public void onGameStarted(Game game) {
		// Auto-generated method stub

	}

	public GameOfLifeMainViewFrame getGameOfLifeMainViewFrame() {
		return towerDefenseMainViewFrame;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	@Override
	public void onCellAliveStatusChanged(boolean alive, Cell cell) {
		redrawCell(cell);
	}

}
