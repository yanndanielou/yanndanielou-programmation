package gameboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.Tower;
import belligerents.listeners.AttackerListener;
import belligerents.listeners.TowerListener;
import common.BadLogicException;
import constants.Constants;
import game.Game;
import game.GameBoardPointListener;
import geometry.IntegerPoint;

public class GameBoardPoint extends IntegerPoint implements TowerListener, AttackerListener {

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

	private EnumMap<NeighbourGameBoardPointDirection, GameBoardPoint> neighbourPerDirection = new EnumMap<>(
			NeighbourGameBoardPointDirection.class);

	public GameBoardPoint(Game game, int line, int column) {
		super(column, line);
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
	
	public boolean isOccupiedByAttacker(){
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

	public int getRow() {
		return getYAsInt();
	}

	public int getColumn() {
		return getXAsInt();
	}

	public void setNeighbour(NeighbourGameBoardPointDirection direction, GameBoardPoint neighbour) {
		neighbourPerDirection.put(direction, neighbour);
	}

	public String getShortDescription() {
		return "GameBoardPoint :" + "[" + getXAsInt() + "," + getYAsInt() + "]";
	}

	public Collection<GameBoardPoint> getNeighbours() {
		return neighbourPerDirection.values();
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
