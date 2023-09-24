package gameoflife.hmi;

import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.constants.HMIConstants;
import gameoflife.game.Game;
import gameoflife.game.GameBoardPointListener;
import gameoflife.game.GameStatusListener;
import gameoflife.gameboard.GameBoard;
import gameoflife.gameboard.Cell;

public class GameBoardPanel extends JPanel implements GameStatusListener, GameBoardPointListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPanel.class);

	private static final long serialVersionUID = -1541008040602802454L;

	private JLabel emptyGameBoardBackgroundAsLabel;

	private HashMap<Cell, JLabel> gameObjectToLabelMap = new HashMap<>();

	private GameBoard gameBoard;

	private GameOfLifeMainViewFrame towerDefenseMainViewFrame;

	public GameBoardPanel(GameOfLifeMainViewFrame towerDefenseMainViewFrame) {
		this.towerDefenseMainViewFrame = towerDefenseMainViewFrame;
	}

	public void initializeGamefield(GameBoard gameBoard) {
		this.gameBoard = gameBoard;

		setLayout(null);
		setSize(gameBoard.getTotalWidth() * HMIConstants.CELL_WIDTH_IN_PIXELS, gameBoard.getTotalHeight() * HMIConstants.CELL_HEIGHT_IN_PIXELS);
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

}
