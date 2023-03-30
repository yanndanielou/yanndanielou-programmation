package game;

import java.util.ArrayList;

import builders.gameboard.GameBoardDataModel;
import builders.genericobjects.GenericObjectsDataModel;
import game_board.GameBoard;
import moving_objects.GameObject;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.Belligerent;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.SimpleAllyBomb;

public class Game {

	private ArrayList<Level> levels = new ArrayList<Level>(); // Create an ArrayList object
	private GameBoard gameboard = null;
	private AllyBoat ally_boat = null;
	private ArrayList<SimpleSubMarine> simple_submarines = new ArrayList<>();
	private ArrayList<YellowSubMarine> yellow_submarines = new ArrayList<>();
	private ArrayList<SimpleAllyBomb> simple_ally_bombs = new ArrayList<>();

	public Game(GameBoardDataModel gameBoardDataModel, GenericObjectsDataModel genericObjectsDataModel) {
		// this.genericObjectsDataModel = genericObjectsDataModel;
		// this.gameBoardDataModel = gameBoardDataModel;
		gameboard = new GameBoard(gameBoardDataModel);
		ally_boat = new AllyBoat(genericObjectsDataModel.getAlly_boat_data_model(), gameBoardDataModel);
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}

	public GameBoard getGameboard() {
		return gameboard;
	}

	public AllyBoat getAlly_boat() {
		return ally_boat;
	}

	public ArrayList<GameObject> getGame_objects() {
		ArrayList<GameObject> game_objects = new ArrayList<>();
		game_objects.add(ally_boat);
		game_objects.addAll(simple_ally_bombs);
		game_objects.addAll(get_all_submarines());
		return game_objects;
	}

	public ArrayList<Belligerent> get_all_submarines() {
		ArrayList<Belligerent> submarines = new ArrayList<>();
		submarines.addAll(simple_submarines);
		submarines.addAll(yellow_submarines);
		return submarines;
	}

	public ArrayList<SimpleSubMarine> getSimple_submarines() {
		return simple_submarines;
	}

	public ArrayList<YellowSubMarine> getYellow_submarines() {
		return yellow_submarines;
	}

	public void addSimpleSubMarine(SimpleSubMarine submarine) {
		simple_submarines.add(submarine);
	}

	public void addYellowSubMarine(YellowSubMarine submarine) {
		yellow_submarines.add(submarine);
	}

	public void addSimpleAllyBomb(SimpleAllyBomb simpleAllyBomb) {
		simple_ally_bombs.add(simpleAllyBomb);
	}

	public ArrayList<SimpleAllyBomb> getSimple_ally_bombs() {
		return simple_ally_bombs;
	}

	/*
	 * public SimpleSubMarine
	 * create_simple_submarine(ScenarioLevelEnnemyCreationDataModel
	 * scenarioLevelEnnemyCreationDataModel) { SimpleSubMarine submarine = new
	 * SimpleSubMarine(scenarioLevelEnnemyCreationDataModel,
	 * genericObjectsDataModel.getSimple_submarine_data_model(),
	 * gameBoardDataModel); return submarine; }
	 */
}
