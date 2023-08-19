package main.game;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameDuration implements GameStatusListener {
	private static final Logger LOGGER = LogManager.getLogger(GameDuration.class);

	private Date gameStartDate = new Date();
	private int numberOfSecondsSinceGameStart;
	@Override
	public void onListenToGameStatus(Game game) {
		// TODO Auto-generated method stub
		
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
