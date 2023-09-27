package gameoflife.hmi;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JComponent;
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

	private HashMap<Cell, JPanel> gameObjectToLabelMap = new HashMap<>();

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
			JPanel displayedObject = new JPanel();

			gameObjectToLabelMap.put(cell, displayedObject);

			displayedObject.setSize(HMIConstants.CELL_WIDTH_IN_PIXELS, HMIConstants.CELL_HEIGHT_IN_PIXELS);

			int cellX = cell.getXAsInt();
			int cellY = cell.getYAsInt();
			displayedObject.setToolTipText("X: " + cellX + " Y: " + cellY);
			displayedObject.setLocation(cellX * HMIConstants.CELL_WIDTH_IN_PIXELS,
					cellY * HMIConstants.CELL_HEIGHT_IN_PIXELS);

			cell.addGameBoardPointListener(this);

			redrawCell(cell);
			add(displayedObject);
		}

	}

	private void redrawCell(Cell cell) {
		JComponent component = gameObjectToLabelMap.get(cell);

		if (cell.isAlive()) {
			component.setBackground(Color.BLACK);
		} else {
			component.setBackground(Color.WHITE);
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
