package towerdefense.gameboard;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.GenericIntegerGameBoardPoint;
import main.common.exceptions.BadLogicException;
import towerdefense.belligerents.Attacker;
import towerdefense.belligerents.Tower;
import towerdefense.belligerents.listeners.AttackerListener;
import towerdefense.belligerents.listeners.TowerListener;
import towerdefense.constants.Constants;
import towerdefense.game.Game;
import towerdefense.game.GameBoardPointListener;

public class GameBoardPoint extends GenericIntegerGameBoardPoint implements TowerListener, AttackerListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353270231660749618L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPoint.class);

	private Game game;

	private ArrayList<Tower> towersPresent = new ArrayList<>();
	private ArrayList<Attacker> attackersPresent = new ArrayList<>();
	private ArrayList<GameBoardWallArea> wallsPresent = new ArrayList<>();
	private ArrayList<GameBoardAttackersEntryArea> gameBoardAttackersEntryAreasPresent = new ArrayList<>();
	private ArrayList<GameBoardAttackersExitArea> gameBoardAttackersExitAreasPresent = new ArrayList<>();
	private ArrayList<GameBoardNonPlayableArea> gameBoardNonPlayableAreasPresent = new ArrayList<>();
	private ArrayList<GameBoardInitiallyConstructibleMacroArea> initiallyConstructibleMacroAreasPresent = new ArrayList<>();
	private ArrayList<GameBoardPredefinedConstructionLocation> predefinedConstructionLocationsPresent = new ArrayList<>();

	private ArrayList<GameBoardPointListener> gameBoardPointListeners = new ArrayList<>();

	public GameBoardPoint(Game game, int x, int y) {
		super(x, y);
		this.game = game;
	}

	public void addGameBoardPointListener(GameBoardPointListener gameBoardPointListener) {
		gameBoardPointListeners.add(gameBoardPointListener);
	}

	public void addWall(GameBoardWallArea gameBoardWall) {
		wallsPresent.add(gameBoardWall);
	}

	public boolean isWall() {
		return !wallsPresent.isEmpty();
	}

	public boolean isOccupiedByTower() {
		return !towersPresent.isEmpty();
	}

	public boolean isOccupiedByAttacker() {
		return !attackersPresent.isEmpty();
	}

	public boolean isNonPlayableArea() {
		return !gameBoardNonPlayableAreasPresent.isEmpty();
	}

	public void addGameBoardAttackersEntryArea(GameBoardAttackersEntryArea gameBoardAttackersEntryArea) {
		gameBoardAttackersEntryAreasPresent.add(gameBoardAttackersEntryArea);
	}

	public void addGameBoardAttackersExitArea(GameBoardAttackersExitArea gameBoardAttackersExitArea) {
		gameBoardAttackersExitAreasPresent.add(gameBoardAttackersExitArea);
	}

	public Game getGame() {
		return game;
	}

	private void addTower(Tower tower) {
		if (towersPresent.size() >= Constants.MAXIMUM_NUMBER_OF_TOWERS_ALLOWED_PER_LOCATION) {
			throw new BadLogicException("Cannot add tower " + tower + " to point:" + this);
		}
		towersPresent.add(tower);
	}

	@Override
	public void onListenToTower(Tower tower) {
		addTower(tower);
	}

	@Override
	public void onTowerRemoval(Tower tower) {

		if (towersPresent.size() >= Constants.MAXIMUM_NUMBER_OF_TOWERS_ALLOWED_PER_LOCATION) {
		}

		boolean removed = towersPresent.remove(tower);
		if (!removed) {
			throw new BadLogicException(
					"Cannot remove tower " + tower + " to point:" + this + " because was not present");

		}
		tower.addListener(this);
	}

	@Override
	public void onAttackerEndOfDestructionAndClean(Attacker attacker) {
		// Auto-generated method stub

	}

	private void addAttacker(Attacker attacker) {
		attackersPresent.add(attacker);
	}

	@Override
	public void onListenToAttacker(Attacker attacker) {
		addAttacker(attacker);
	}

	@Override
	public void onAttackerMoved(Attacker attacker) {
		// Auto-generated method stub

	}

	@Override
	public void onAttackerBeginningOfDestruction(Attacker attacker) {
		// Auto-generated method stub

	}

	public void addGameBoardInitiallyConstructibleMacroArea(
			GameBoardInitiallyConstructibleMacroArea gameBoardConstructibleArea) {
		initiallyConstructibleMacroAreasPresent.add(gameBoardConstructibleArea);
	}

	public void addNonPlayableArea(GameBoardNonPlayableArea nonPlayableArea) {
		gameBoardNonPlayableAreasPresent.add(nonPlayableArea);
	}

	public void addGameBoardPredefinedConstructionLocation(
			GameBoardPredefinedConstructionLocation predefinedConstructionLocation) {
		predefinedConstructionLocationsPresent.add(predefinedConstructionLocation);
	}

	@Override
	public void onAttackerEscape(Attacker attacker) {
		// Auto-generated method stub

	}

}
