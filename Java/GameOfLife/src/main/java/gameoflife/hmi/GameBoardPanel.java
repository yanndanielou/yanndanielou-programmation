package gameoflife.hmi;

import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.game.Game;
import gameoflife.game.GameBoardPointListener;
import gameoflife.game.GameStatusListener;
import gameoflife.gameboard.GameBoard;
import gameoflife.gameboard.GameBoardPoint;

public class GameBoardPanel extends JLayeredPane
		implements GameStatusListener, GameBoardPointListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPanel.class);

	private static final long serialVersionUID = -1541008040602802454L;

	private JLabel emptyGameBoardBackgroundAsLabel;

	private HashMap<GameBoardPoint, JLabel> gameObjectToLabelMap = new HashMap<>();

	private GameBoard gameBoard;

	private enum LAYERS_ORDERED_FROM_TOP_TO_BACK {
		BELLIGERENTS, UNVISIBLE_UNITARY_PARTIAL_CONSTRUCTION_SQUARE, BACKGROUND_IMAGE, UNVISIBLE;
	}

	private GameOfLifeMainViewFrame towerDefenseMainViewFrame;

	public GameBoardPanel(GameOfLifeMainViewFrame towerDefenseMainViewFrame) {
		this.towerDefenseMainViewFrame = towerDefenseMainViewFrame;
	}

	public void initializeGamefield(GameBoard gameBoard) {
		this.gameBoard = gameBoard;

		setLayout(null);
		setSize(gameBoard.getTotalWidth(), gameBoard.getTotalHeight());

		add(emptyGameBoardBackgroundAsLabel, LAYERS_ORDERED_FROM_TOP_TO_BACK.BACKGROUND_IMAGE.ordinal());


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
