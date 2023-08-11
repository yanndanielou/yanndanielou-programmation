package hmi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import belligerents.Attacker;
import belligerents.Belligerent;
import belligerents.GameObject;
import belligerents.Tower;
import belligerents.listeners.AttackerListener;
import belligerents.listeners.TowerListener;
import core.GameManager;
import game.Game;
import game.GameBoardPointListener;
import game.GameStatusListener;
import game_board.GameBoard;
import game_board.GameBoardPredefinedConstructionLocation;

public class GameBoardPanel extends JLayeredPane
		implements GameStatusListener, GameBoardPointListener, TowerListener, AttackerListener {

	private static final Logger LOGGER = LogManager.getLogger(GameBoardPanel.class);

	private static final long serialVersionUID = -1541008040602802454L;

	private ImageIcon emptyGameBoardBackgroundAsIcon;
	private JLabel emptyGameBoardBackgroundAsLabel;

	@Deprecated
	private List<JLabel> constructibleLocationsAsLabels = new ArrayList<>();

	private HashMap<GameObject, JLabel> gameObjectToLabelMap = new HashMap<>();
	private HashMap<GameBoardPredefinedConstructionLocation, ConstructionLocationPanel> constructionLocationToLabelMap = new HashMap<>();

	private enum LAYERS_ORDERED_FROM_TOP_TO_BACK {
		BELLIGERENTS, UNVISIBLE_UNITARY_PARTIAL_CONSTRUCTION_SQUARE, BACKGROUND_IMAGE, UNVISIBLE;
	}

	private DesktopTowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame;;

	public GameBoardPanel(DesktopTowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame) {
		this.DesktopTowerDefenseMainViewFrame = DesktopTowerDefenseMainViewFrame;
	}

	public void initialize_gamefield(GameBoard gameBoard) {
		setLayout(null);
		setSize(gameBoard.getTotalWidth(), gameBoard.getTotalHeight());

		emptyGameBoardBackgroundAsIcon = new ImageIcon(
				gameBoard.getGameBoardDataModel().getGameBoardFullBackgroundImagePath());
		emptyGameBoardBackgroundAsLabel = new JLabel(emptyGameBoardBackgroundAsIcon);

		emptyGameBoardBackgroundAsLabel.setSize(gameBoard.getTotalWidth(), gameBoard.getTotalHeight());
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
		DesktopTowerDefenseMainViewFrame.removeGameFieldPanel();
	}

	@Override
	public void onGameLost(Game game) {
	}

	@Override
	public void on_game_won(Game game) {
		// TODO Auto-generated method stub
	}

	@Override
	public void on_listen_to_tower(Tower tower) {
		display_new_object_as_label(tower, LAYERS_ORDERED_FROM_TOP_TO_BACK.BELLIGERENTS);
	}

	@Override
	public void on_attacker_end_of_destruction_and_clean(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	private void display_new_object_as_label(GameObject gameObject, LAYERS_ORDERED_FROM_TOP_TO_BACK layer) {
		ImageIcon get_graphical_representation_as_icon = gameObject.get_graphical_representation_as_icon();
		JLabel objectAsLabel = new JLabel(get_graphical_representation_as_icon);
		objectAsLabel.setLocation((int) gameObject.getSurrounding_rectangle_absolute_on_complete_board().getX(),
				(int) gameObject.getSurrounding_rectangle_absolute_on_complete_board().getY());
		objectAsLabel.setSize((int) gameObject.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
				(int) gameObject.getSurrounding_rectangle_absolute_on_complete_board().getHeight());
		gameObjectToLabelMap.put(gameObject, objectAsLabel);
		add(objectAsLabel, layer.ordinal());
	}

	@Override
	public void onListenToAttacker(Attacker attacker) {
		display_new_object_as_label(attacker, LAYERS_ORDERED_FROM_TOP_TO_BACK.BELLIGERENTS);
	}

	@Override
	public void on_attacker_moved(Attacker attacker) {
		JLabel jLabel = gameObjectToLabelMap.get(attacker);
		jLabel.setLocation(attacker.get_extreme_left_point_x(), attacker.getHighestPointY());
	}

	@Override
	public void on_attacker_beginning_of_destruction(Attacker attacker) {
	}

	@Override
	public void on_tower_removal(Tower tower) {
		// TODO Auto-generated method stub

	}

}
