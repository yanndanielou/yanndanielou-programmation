package hmi;

import javax.swing.JPanel;

import game.Game;
import game.GameStatusListener;

public class TopPanel extends JPanel implements GameStatusListener {

	private static final long serialVersionUID = -4722225029326344692L;

	private TowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame;

	public TopPanel(TowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame, int width, int height) {

		this.DesktopTowerDefenseMainViewFrame = DesktopTowerDefenseMainViewFrame;
		setLayout(null);
	}

	@Override
	public void onListenToGameStatus(Game game) {
	}

	@Override
	public void onGameCancelled(Game game) {
		removeAll();
		DesktopTowerDefenseMainViewFrame.removeTopPanel();
	}

	@Override
	public void onGameLost(Game game) {

	}

	@Override
	public void onGameWon(Game game) {
	}

	@Override
	public void onGameStarted(Game game) {
		// TODO Auto-generated method stub
		
	}

}
