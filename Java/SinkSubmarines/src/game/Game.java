package game;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import moving_objects.weapon.Weapon;
import time.TimeManager;
import time.TimeManagerListener;

public class Game implements TimeManagerListener {
	private static final Logger LOGGER = LogManager.getLogger(TimeManagerListener.class);

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
	private ArrayList<GameStatusListener> game_status_listeners = new ArrayList<>();

	private int next_ally_bomb_horizontal_speed_relative_percentage = 0;
	private NextAllyBombHorizontalSpeedIncreaseDirection next_ally_bomb_horizontal_speed_increase_direction;

	private int number_of_remaining_lives;
	private int score;

	private boolean paused = false;

	public Game(GenericObjectsDataModel genericObjectsDataModel, int number_of_lives) {
		gameboard = new GameBoard();
		ally_boat = new AllyBoat(genericObjectsDataModel.getAlly_boat_data_model(),
				genericObjectsDataModel.getAlly_simple_bomb_data_model(), this);
		setNumber_remaining_lives(number_of_lives);
		TimeManager.getInstance().add_listener(this);

		add_game_status_listener(TimeManager.getInstance());
	}

	public void add_game_listener(GameListener listener) {
		game_listeners.add(listener);
		listener.on_listen_to_game(this);
	}

	public void add_game_status_listener(GameStatusListener listener) {
		game_status_listeners.add(listener);
		listener.on_listen_to_game_status(this);
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
		game_objects.addAll(get_all_submarines_bombs());
		return game_objects;
	}

	public ArrayList<Belligerent> get_all_submarines() {
		ArrayList<Belligerent> submarines = new ArrayList<>();
		submarines.addAll(simple_submarines);
		submarines.addAll(yellow_submarines);
		return submarines;
	}

	public ArrayList<Weapon> get_all_submarines_bombs() {
		ArrayList<Weapon> submarines_bombs = new ArrayList<>();
		submarines_bombs.addAll(simple_submarine_bombs);
		submarines_bombs.addAll(floating_submarine_bombs);
		return submarines_bombs;
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
		game_status_listeners.forEach((game_status_listener) -> game_status_listener.on_game_resumed(this));
	}

	public void notify_game_cancelled() {
		game_status_listeners.forEach((game_status_listener) -> game_status_listener.on_game_cancelled(this));
	}

	public int getNumber_Remaining_lives() {
		return number_of_remaining_lives;
	}

	public void setNumber_remaining_lives(int remaining_lives) {
		if (this.number_of_remaining_lives != remaining_lives) {
			this.number_of_remaining_lives = remaining_lives;
			notify_number_remaining_lives_changed();
		}
	}

	private void notify_game_paused() {
		for (GameStatusListener gameListener : game_status_listeners) {
			gameListener.on_game_paused(this);
		}
	}

	private void notify_number_remaining_lives_changed() {
		for (GameListener gameListener : game_listeners) {
			gameListener.on_number_of_remaining_lives_changed(this, number_of_remaining_lives);
		}
	}

	private void notify_new_scenario_level_loaded(Game game, ScenarioLevelDataModel scenario_level_data_model) {
		game_listeners.forEach((game_listener) -> game_listener.on_new_scenario_level(this, scenario_level_data_model));

	}

	private void notify_new_scenario_level_wave(Game game, ScenarioLevelWaveDataModel scenario_level_wave) {
		game_listeners.forEach((game_listener) -> game_listener.on_new_scenario_level_wave(this, scenario_level_wave));

	}

	private void notify_next_ally_bomb_horizontal_speed_changed() {
		game_listeners.forEach((game_listener) -> game_listener.on_next_ally_bomb_horizontal_speed_changed(this,
				next_ally_bomb_horizontal_speed_relative_percentage));
	}

	public ScenarioLevelWaveDataModel getCurrent_scenario_Level_wave_data_model() {
		return current_scenario_Level_wave_data_model;
	}

	public void setCurrent_scenario_Level_wave_data_model(
			ScenarioLevelWaveDataModel current_scenario_Level_wave_data_model) {
		this.current_scenario_Level_wave_data_model = current_scenario_Level_wave_data_model;
		notify_new_scenario_level_wave(this, current_scenario_Level_wave_data_model);
	}

	public ScenarioLevelDataModel getCurrent_scenario_level_data_model() {
		return current_scenario_level_data_model;
	}

	public void setCurrent_scenario_level_data_model(ScenarioLevelDataModel current_scenario_level_data_model) {
		this.current_scenario_level_data_model = current_scenario_level_data_model;

		ally_boat.setMax_number_of_living_bombs(current_scenario_level_data_model.getMax_number_of_ally_bombs());
		notify_new_scenario_level_loaded(this, current_scenario_level_data_model);
	}

	public int getNext_ally_bomb_horizontal_speed_relative_percentage() {
		return next_ally_bomb_horizontal_speed_relative_percentage;
	}

	public boolean compute_next_ally_bomb_horizontal_speed_relative_percentage() {
		int previous_value_next_ally_bomb_horizontal_speed_relative_percentage = next_ally_bomb_horizontal_speed_relative_percentage;
		if (next_ally_bomb_horizontal_speed_increase_direction != null) {
			switch (next_ally_bomb_horizontal_speed_increase_direction) {
			case DECREASE:
				if (next_ally_bomb_horizontal_speed_relative_percentage > 0) {
					next_ally_bomb_horizontal_speed_relative_percentage--;
				} else {
					next_ally_bomb_horizontal_speed_increase_direction = NextAllyBombHorizontalSpeedIncreaseDirection.INCREASE;
				}
				break;
			case INCREASE:
				if (next_ally_bomb_horizontal_speed_relative_percentage < 100) {
					next_ally_bomb_horizontal_speed_relative_percentage++;
				} else {
					next_ally_bomb_horizontal_speed_increase_direction = NextAllyBombHorizontalSpeedIncreaseDirection.DECREASE;
				}
				break;
			default:
				break;
			}
		}
		if (previous_value_next_ally_bomb_horizontal_speed_relative_percentage != next_ally_bomb_horizontal_speed_relative_percentage) {
			notify_next_ally_bomb_horizontal_speed_changed();
			return true;
		}
		return false;
	}

	public void set_next_ally_bomb_horizontal_speed(boolean value) {
		if (value) {
			if (next_ally_bomb_horizontal_speed_increase_direction == null) {
				next_ally_bomb_horizontal_speed_increase_direction = NextAllyBombHorizontalSpeedIncreaseDirection.INCREASE;
			}
		} else {
			next_ally_bomb_horizontal_speed_increase_direction = null;
			next_ally_bomb_horizontal_speed_relative_percentage = 0;
			notify_next_ally_bomb_horizontal_speed_changed();
		}
	}

	@Override
	public void on_10ms_tick() {
		compute_next_ally_bomb_horizontal_speed_relative_percentage();
	}

	@Override
	public void on_50ms_tick() {
	}

	@Override
	public void on_100ms_tick() {
	}

	@Override
	public void on_second_tick() {
	}

	@Override
	public void on_pause() {
		notify_game_paused();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		game_listeners.forEach((game_listener) -> game_listener.on_score_changed(this, score));
	}

	private enum NextAllyBombHorizontalSpeedIncreaseDirection {
		DECREASE, INCREASE;
	}

	public void addScore(int score_addition) {
		setScore(score + score_addition);
	}

	public void game_over() {
		LOGGER.info("Game is over!");
		game_status_listeners.forEach((game_status_listener) -> game_status_listener.on_game_over(this));
	}
}
