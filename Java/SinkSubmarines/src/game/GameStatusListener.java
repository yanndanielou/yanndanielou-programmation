package game;

import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelWaveDataModel;

public interface GameStatusListener {

	public void on_game_paused(Game game);

	public void on_game_resumed(Game game);

	public void on_listen_to_game_status(Game game);

	public void on_game_cancelled(Game game);

}
