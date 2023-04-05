package game;

import java.util.ArrayList;

import builders.gameboard.GameBoardDataModel;
import builders.genericobjects.GenericObjectsDataModel;
import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelWaveDataModel;
import game_board.GameBoard;
import moving_objects.GameObject;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.Belligerent;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;

public class Game {

	private ArrayList<Level> levels = new ArrayList<Level>(); // Create an ArrayList object
	private GameBoard gameboard = null;
	private AllyBoat ally_boat = null;
	private ArrayList<SimpleSubMarine> simple_submarines = new ArrayList<>();
	private ArrayList<YellowSubMarine> yellow_submarines = new ArrayList<>();
	private ArrayList<SimpleAllyBomb> simple_ally_bombs = new ArrayList<>();
	private ArrayList<SimpleSubmarineBomb> simple_submarine_bombs = new ArrayList<>();
	private ArrayList<FloatingSubmarineBomb> floating_submarine_bombs = new ArrayList<>();

	private ScenarioLevelDataModel current_scenario_level_data_model = null;
	private ScenarioLevelWaveDataModel current_scenario_Level_wave_data_model = null;

	private ArrayList<GameListener> game_listeners = new ArrayList<>();

	private int remaining_lives;

	private boolean paused = false;

	public Game(GameBoardDataModel gameBoardDataModel, GenericObjectsDataModel genericObjectsDataModel,
			int number_of_lives) {
		// this.genericObjectsDataModel = genericObjectsDataModel;
		// this.gameBoardDataModel = gameBoardDataModel;
		gameboard = new GameBoard(gameBoardDataModel);
		ally_boat = new AllyBoat(genericObjectsDataModel.getAlly_boat_data_model(), gameBoardDataModel,
				genericObjectsDataModel.getAlly_simple_bomb_data_model(), this);
		this.setRemaining_lives(number_of_lives);
	}

	public void add_game_listener(GameListener listener) {
		game_listeners.add(listener);
		listener.on_listen_to_game(this);
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
		game_objects.addAll(simple_submarine_bombs);
		game_objects.addAll(floating_submarine_bombs);
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

	public void addSimpleSubmarineBomb(SimpleSubmarineBomb sumbmarineBomb) {
		simple_submarine_bombs.add(sumbmarineBomb);
	}

	public ArrayList<SimpleSubmarineBomb> getSimple_submarine_bombs() {
		return simple_submarine_bombs;
	}

	public void addFloatingSubmarineBomb(FloatingSubmarineBomb sumbmarineBomb) {
		floating_submarine_bombs.add(sumbmarineBomb);
	}

	public ArrayList<FloatingSubmarineBomb> getFloating_submarine_bombs() {
		return floating_submarine_bombs;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		if (this.paused != paused) {
			this.paused = paused;
			if (paused) {
				notify_game_paused();
			} else {
				notify_game_resumed();
			}
		}
	}

	private void notify_game_resumed() {
		game_listeners.forEach((game_listener) -> game_listener.on_game_resumed(this));
	}

	public void notify_game_cancelled() {
		game_listeners.forEach((game_listener) -> game_listener.on_game_cancelled(this));
	}

	public int getRemaining_lives() {
		return remaining_lives;
	}

	public void setRemaining_lives(int remaining_lives) {
		if (this.remaining_lives != remaining_lives) {
			this.remaining_lives = remaining_lives;
			notify_remaining_lives_changed();
		}
	}

	private void notify_game_paused() {
		for (GameListener gameListener : game_listeners) {
			gameListener.on_game_paused(this);
		}
	}

	private void notify_remaining_lives_changed() {
		for (GameListener gameListener : game_listeners) {
			gameListener.on_number_of_remaining_lives_changed(this);
		}
	}

	private void notify_new_scenario_level_loaded(Game game, ScenarioLevelDataModel scenario_level_data_model) {
		game_listeners.forEach((game_listener) -> game_listener.on_new_scenario_level(this, scenario_level_data_model));
		
	}
	private void notify_new_scenario_level_wave(Game game, ScenarioLevelWaveDataModel scenario_level_wave) {
		game_listeners.forEach((game_listener) -> game_listener.on_new_scenario_level_wave(this, scenario_level_wave));
		
	}

	public ScenarioLevelWaveDataModel getCurrent_scenario_Level_wave_data_model() {
		return current_scenario_Level_wave_data_model;
	}

	public void setCurrent_scenario_Level_wave_data_model(ScenarioLevelWaveDataModel current_scenario_Level_wave_data_model) {
		this.current_scenario_Level_wave_data_model = current_scenario_Level_wave_data_model;
		notify_new_scenario_level_wave(this, current_scenario_Level_wave_data_model);
	}

	public ScenarioLevelDataModel getCurrent_scenario_level_data_model() {
		return current_scenario_level_data_model;
	}

	public void setCurrent_scenario_level_data_model(ScenarioLevelDataModel current_scenario_level_data_model) {
		this.current_scenario_level_data_model = current_scenario_level_data_model;
		notify_new_scenario_level_loaded(this, current_scenario_level_data_model);
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
