package towerdefense.game;

import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import test.common.timer.TimeConstants;
import towerdefense.time.GamePausableOneShotDelayedTask;

public class GameDuration implements GameStatusListener {
	private static final Logger LOGGER = LogManager.getLogger(GameDuration.class);

	private Date gameStartDate = new Date();
	private int numberOfSecondsSinceGameStart;

	private ArrayList<GameDurationListener> gameDurationListeners = new ArrayList<>();

	@Override
	public void onListenToGameStatus(Game game) {

		new GamePausableOneShotDelayedTask(game, TimeConstants.ONE_SECOND) {

			@Override
			public void run() {
				numberOfSecondsSinceGameStart++;
				gameDurationListeners.forEach((gameDurationListener) -> gameDurationListener
						.onSecondsDurationChanged(GameDuration.this, numberOfSecondsSinceGameStart));

			}
		};

	}

	public void addGameStatusListener(GameDurationListener listener) {
		gameDurationListeners.add(listener);
	}

	@Override
	public void onGameStarted(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameCancelled(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameLost(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameWon(Game game) {
		// TODO Auto-generated method stub

	}
}
