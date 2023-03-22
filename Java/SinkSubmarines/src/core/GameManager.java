package core;

import java.util.ArrayList;

import builders.gameboard.GameBoardDataModelBuilder;
import builders.genericobjects.GenericObjectsDataModelBuilder;
import game.Game;
import hmi.SinkSubmarinesMainView;
import moving_objects.GameObject;
import time.TimeManager;
import time.TimeManagerListener;

public class GameManager implements TimeManagerListener {

	private static GameManager instance;

	private GenericObjectsDataModelBuilder genericObjectsDataModelBuilder = null;
	private GameBoardDataModelBuilder gameBoardDataModelBuilder = null;
	private SinkSubmarinesMainView sinkSubmarinesMainView = null;
	private Game game = null;

	private GameManager() {
		TimeManager.getInstance().add_listener(this);
	}

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	public void new_game(String game_board_data_model_json_file, String generic_objects_data_model_json_file) {
		gameBoardDataModelBuilder = new GameBoardDataModelBuilder(game_board_data_model_json_file);
		genericObjectsDataModelBuilder = new GenericObjectsDataModelBuilder(generic_objects_data_model_json_file);
		TimeManager.getInstance().start();
		sinkSubmarinesMainView
				.initialize_from_game_board_data_model(gameBoardDataModelBuilder.getGame_board_data_model());
		game = new Game(gameBoardDataModelBuilder.getGame_board_data_model(),
				genericObjectsDataModelBuilder.getGeneric_objects_data_model());
		sinkSubmarinesMainView.getAllyBoatPanel().setAlly_boat(game.getAlly_boat());
		GameObjectsMovementOrchestor.getInstance();

	}

	@Override
	public void on_10ms_tick() {

	}

	@Override
	public void on_100ms_tick() {
	}

	@Override
	public void on_second_tick() {
	}

	public GenericObjectsDataModelBuilder getGenericObjectsDataModelBuilder() {
		return genericObjectsDataModelBuilder;
	}

	public GameBoardDataModelBuilder getGameBoardDataModelBuilder() {
		return gameBoardDataModelBuilder;
	}

	public SinkSubmarinesMainView getSinkSubmarinesMainView() {
		return sinkSubmarinesMainView;
	}

	public Game getGame() {
		return game;
	}

	public void setSinkSubmarinesMainView(SinkSubmarinesMainView sinkSubmarinesMainView) {
		this.sinkSubmarinesMainView = sinkSubmarinesMainView;
	}

	@Override
	public void on_50ms_tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_20ms_tick() {
		// TODO Auto-generated method stub
		
	}
}
