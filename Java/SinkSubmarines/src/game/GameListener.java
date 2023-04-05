package game;

import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelWaveDataModel;

public interface GameListener {

	public void on_game_paused(Game game);

	public void on_number_of_remaining_lives_changed(Game game, int remaining_lives);

	public void on_game_resumed(Game game);

	public void on_listen_to_game(Game game);

	public void on_game_cancelled(Game game);

	public void on_new_scenario_level(Game game, ScenarioLevelDataModel scenario_level_data_model);

	public void on_new_scenario_level_wave(Game game, ScenarioLevelWaveDataModel scenario_level_wave);

	public void on_next_ally_bomb_horizontal_speed_changed(Game game, int next_ally_bomb_horizontal_speed);

}
