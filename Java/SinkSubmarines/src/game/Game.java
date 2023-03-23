package game;

import java.util.ArrayList;

import builders.gameboard.GameBoardDataModel;
import builders.gameboard.GameBoardDataModelBuilder;
import builders.genericobjects.GenericObjectsDataModel;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import game_board.GameBoard;
import moving_objects.AllyBoat;
import moving_objects.GameObject;
import moving_objects.SimpleSubMarine;

public class Game {

	private ArrayList<Level> levels = new ArrayList<Level>(); // Create an ArrayList object
	private GameBoard gameboard = null;
	private AllyBoat ally_boat = null;
	private ArrayList<GameObject> game_objects = new ArrayList<>();
	private ArrayList<SimpleSubMarine> simple_submarines = new ArrayList<>();


	public Game(GameBoardDataModel gameBoardDataModel, GenericObjectsDataModel genericObjectsDataModel) {
	//	this.genericObjectsDataModel = genericObjectsDataModel;
	//	this.gameBoardDataModel = gameBoardDataModel;
		gameboard = new GameBoard(gameBoardDataModel);
		ally_boat = new AllyBoat(genericObjectsDataModel.getAlly_boat_data_model(), gameBoardDataModel);
		game_objects.add(ally_boat);
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
		return game_objects;
	}

	/*
	public SimpleSubMarine create_simple_submarine(ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel) {
		SimpleSubMarine submarine = new SimpleSubMarine(scenarioLevelEnnemyCreationDataModel,
				genericObjectsDataModel.getSimple_submarine_data_model(), gameBoardDataModel);
		return submarine;
	}
*/
}
