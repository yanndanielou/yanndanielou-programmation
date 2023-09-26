package towerdefense.hmi;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.common.hmi.utils.HMIUtils;
import towerdefense.belligerents.Attacker;
import towerdefense.belligerents.GameObject;
import towerdefense.belligerents.Tower;
import towerdefense.belligerents.listeners.AttackerListener;
import towerdefense.belligerents.listeners.TowerListener;
import towerdefense.game.Game;
import towerdefense.game.GameBoardPointListener;
import towerdefense.game.GameStatusListener;
import towerdefense.gameboard.GameBoard;
import towerdefense.gameboard.GameBoardPredefinedConstructionLocation;

public class GameBoardPanel extends JLayeredPane
		implements GameStatusListener, GameBoardPointListener, TowerListener, AttackerListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPanel.class);

	private static final long serialVersionUID = -1541008040602802454L;

	private JLabel emptyGameBoardBackgroundAsLabel;

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
	public void onListenToTower(Tower tower) {
		displayNewObjectAsLabel(tower, LAYERS_ORDERED_FROM_TOP_TO_BACK.BELLIGERENTS);
	}

	@Override
	public void onAttackerEndOfDestructionAndClean(Attacker attacker) {
		// Auto-generated method stub

	}

	private void displayNewObjectAsLabel(GameObject gameObject, LAYERS_ORDERED_FROM_TOP_TO_BACK layer) {
		ImageIcon getGraphicalRepresentationAsIcon = gameObject.getGraphicalRepresentationAsIcon();
		JLabel objectAsLabel = new JLabel(getGraphicalRepresentationAsIcon);
		objectAsLabel.setLocation(gameObject.getExtremeRightPointX(), gameObject.getHighestPointY());
		objectAsLabel.setSize(gameObject.getWidth(), gameObject.getHeight());
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
		jLabel.setLocation(attacker.getExtremeLeftPointXWithIntegerPrecision(), attacker.getHighestPointY());
	}

	@Override
	public void onAttackerBeginningOfDestruction(Attacker attacker) {
	}

	@Override
	public void onTowerRemoval(Tower tower) {
		// Auto-generated method stub

	}

	@Override
	public void onGameStarted(Game game) {
		// Auto-generated method stub

	}

	public TowerDefenseMainViewFrame getTowerDefenseMainViewFrame() {
		return towerDefenseMainViewFrame;
	}

	public HmiEditionMode getHmiPresenter() {
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
