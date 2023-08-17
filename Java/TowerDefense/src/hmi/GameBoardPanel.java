package hmi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.GameObject;
import belligerents.Tower;
import belligerents.listeners.AttackerListener;
import belligerents.listeners.TowerListener;
import game.Game;
import game.GameBoardPointListener;
import game.GameStatusListener;
import gameboard.GameBoard;
import gameboard.GameBoardPredefinedConstructionLocation;

public class GameBoardPanel extends JLayeredPane
		implements GameStatusListener, GameBoardPointListener, TowerListener, AttackerListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPanel.class);

	private static final long serialVersionUID = -1541008040602802454L;

	private JLabel emptyGameBoardBackgroundAsLabel;

	@Deprecated
	private List<JLabel> constructibleLocationsAsLabels = new ArrayList<>();

	private HashMap<GameObject, JLabel> gameObjectToLabelMap = new HashMap<>();
	private HashMap<GameBoardPredefinedConstructionLocation, ConstructionLocationPanel> constructionLocationToLabelMap = new HashMap<>();

	private GameBoard gameBoard;

	private enum LAYERS_ORDERED_FROM_TOP_TO_BACK {
		BELLIGERENTS, UNVISIBLE_UNITARY_PARTIAL_CONSTRUCTION_SQUARE, BACKGROUND_IMAGE, UNVISIBLE;
	}

	private TowerDefenseMainViewFrame towerDefenseMainViewFrame;

	public GameBoardPanel(TowerDefenseMainViewFrame towerDefenseMainViewFrame) {
		this.towerDefenseMainViewFrame = towerDefenseMainViewFrame;
	}

	public void initializeGamefield(GameBoard gameBoard) {
		this.gameBoard = gameBoard;

		setLayout(null);
		setSize(gameBoard.getTotalWidth(), gameBoard.getTotalHeight());

		emptyGameBoardBackgroundAsLabel = HMIUtils
				.createJLabelFromImage(gameBoard.getGameBoardDataModel().getGameBoardFullBackgroundImagePath());
		emptyGameBoardBackgroundAsLabel.setLocation(0, 0);

		add(emptyGameBoardBackgroundAsLabel, LAYERS_ORDERED_FROM_TOP_TO_BACK.BACKGROUND_IMAGE.ordinal());

		for (GameBoardPredefinedConstructionLocation predefinedConstructionLocation : gameBoard
				.getPredefinedConstructionLocations()) {
			ConstructionLocationPanel constructionLocationPanel = new ConstructionLocationPanel(this,
					predefinedConstructionLocation);
			add(constructionLocationPanel, LAYERS_ORDERED_FROM_TOP_TO_BACK.BELLIGERENTS.ordinal());
			constructionLocationToLabelMap.put(predefinedConstructionLocation, constructionLocationPanel);
		}
		// repaint();

		/*
		 * addMouseMotionListener(new MouseMotionListener() {
		 * 
		 * @Override public void mouseMoved(MouseEvent mouseEvent) { int mouseX =
		 * mouseEvent.getX(); int mouseY = mouseEvent.getY();
		 * 
		 * // TODO Auto-generated method stub LOGGER.info("mouseMoved x:" + mouseX +
		 * ", y:" + mouseY + " : " + mouseEvent); }
		 * 
		 * @Override public void mouseDragged(MouseEvent mouseEvent) {
		 * LOGGER.info("mouseDragged" + mouseEvent);
		 * 
		 * } });
		 */
	}

	@Override
	public void onListenToGameStatus(Game game) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
	}

	@Override
	public void onListenToTower(Tower tower) {
		displayNewObjectAsLabel(tower, LAYERS_ORDERED_FROM_TOP_TO_BACK.BELLIGERENTS);
	}

	@Override
	public void onAttackerEndOfDestructionAndClean(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	private void displayNewObjectAsLabel(GameObject gameObject, LAYERS_ORDERED_FROM_TOP_TO_BACK layer) {
		ImageIcon getGraphicalRepresentationAsIcon = gameObject.getGraphicalRepresentationAsIcon();
		JLabel objectAsLabel = new JLabel(getGraphicalRepresentationAsIcon);
		objectAsLabel.setLocation(gameObject.getSurroundingRectangleAbsoluteOnCompleteBoard().getX(),
				gameObject.getSurroundingRectangleAbsoluteOnCompleteBoard().getY());
		objectAsLabel.setSize(gameObject.getSurroundingRectangleAbsoluteOnCompleteBoard().getWidth(),
				gameObject.getSurroundingRectangleAbsoluteOnCompleteBoard().getHeight());
		gameObjectToLabelMap.put(gameObject, objectAsLabel);
		add(objectAsLabel, layer.ordinal());
	}

	@Override
	public void onListenToAttacker(Attacker attacker) {
		displayNewObjectAsLabel(attacker, LAYERS_ORDERED_FROM_TOP_TO_BACK.BELLIGERENTS);
	}

	@Override
	public void onAttackerMoved(Attacker attacker) {
		JLabel jLabel = gameObjectToLabelMap.get(attacker);
		jLabel.setLocation(attacker.getExtremeLeftPointX(), attacker.getHighestPointY());
	}

	@Override
	public void onAttackerBeginningOfDestruction(Attacker attacker) {
	}

	@Override
	public void onTowerRemoval(Tower tower) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameStarted(Game game) {
		// TODO Auto-generated method stub

	}

	public TowerDefenseMainViewFrame getTowerDefenseMainViewFrame() {
		return towerDefenseMainViewFrame;
	}

	public HmiPresenter getHmiPresenter() {
		return towerDefenseMainViewFrame.getHmiPresenter();
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	@Override
	public void onAttackerEscape(Attacker attacker) {
		JLabel attackerRepresentationLabel = gameObjectToLabelMap.get(attacker);
		attackerRepresentationLabel.setVisible(false);
		remove(attackerRepresentationLabel);
		gameObjectToLabelMap.remove(attacker);
	}
}
