package gameoflife.hmi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

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

	private HmiPresenter hmiPresenter;
	/*
	 * private enum LAYERS_ORDERED_FROM_TOP_TO_BACK { BUTTONS, CELLS; }
	 */

	public GameBoardPanel(GameOfLifeMainViewFrame towerDefenseMainViewFrame, Game game) {
		this.towerDefenseMainViewFrame = towerDefenseMainViewFrame;
		initializeGamefield(game.getGameBoard());
	}

	public void initializeGamefield(GameBoard gameBoard) {
		LOGGER.info("initializeGamefield : begin");
		this.gameBoard = gameBoard;

		setLayout(null);

		for (Cell cell : gameBoard.getAllGameBoardPointsAsOrderedList().stream().map(Cell.class::cast).toList()) {
			JPanel displayedObject = new JPanel();

			gameObjectToLabelMap.put(cell, displayedObject);

			int cellX = cell.getXAsInt();
			int cellY = cell.getYAsInt();
			displayedObject.setToolTipText("X: " + cellX + " Y: " + cellY);
			displayedObject.setLayout(new BorderLayout());
			displayedObject.add(new JLabel(displayedObject.getToolTipText()), BorderLayout.CENTER);
			// displayedObject.set(RandomColorGenerator.getRandomColor());

			//displayedObject.addMouseMotionListener(new CellMouseMotionListener(this, cell, displayedObject));
			displayedObject.addMouseListener(new CellMouseMotionListener(this, cell, displayedObject));

			cell.addGameBoardPointListener(this);

			redrawCell(cell);
			// add(displayedObject, LAYERS_ORDERED_FROM_TOP_TO_BACK.CELLS);
			add(displayedObject);
		}
		LOGGER.info("initializeGamefield : end");
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

	public void displayGrid() {
		for (JPanel cell : gameObjectToLabelMap.values()) {
			Border border = null;
			border = new MatteBorder(1, 1, 1, 1, Color.BLACK);
			cell.setBorder(border);
		}
	}

	public void hideGrid() {
		for (JPanel cell : gameObjectToLabelMap.values()) {
			cell.setBorder(null);
		}
	}

	public void setHmiPresenter(HmiPresenter hmiPresenter) {
		this.hmiPresenter = hmiPresenter;
	}

	public void setCellSizeInPixel(int cellSizeInPixels) {

		for (Entry<Cell, JPanel> entrySet : gameObjectToLabelMap.entrySet()) {
			Cell cell = entrySet.getKey();
			JPanel cellPanel = entrySet.getValue();

			cellPanel.setSize(cellSizeInPixels, cellSizeInPixels);

			int cellX = cell.getXAsInt();
			int cellY = cell.getYAsInt();

			cellPanel.setLocation(cellX * cellSizeInPixels, cellY * cellSizeInPixels);
		}
		Dimension dimension = new Dimension(gameBoard.getTotalWidth() * HMIConstants.INITIAL_CELL_SIZE_IN_PIXELS,
				gameBoard.getTotalHeight() * cellSizeInPixels);
		setPreferredSize(dimension);
	}

	public void setDrawActionInProgress(DrawAction drawActionInProgress) {

	}

	public HmiPresenter getHmiPresenter() {
		return hmiPresenter;
	}
}
