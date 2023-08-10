package hmi;

import javax.swing.JPanel;

import game.Game;
import game.GameStatusListener;

public class TopPanel extends JPanel implements GameStatusListener {

	private static final long serialVersionUID = -4722225029326344692L;

	private DesktopTowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame;

	public TopPanel(DesktopTowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame, int width, int height) {

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
	public void on_game_won(Game game) {
	}

}
