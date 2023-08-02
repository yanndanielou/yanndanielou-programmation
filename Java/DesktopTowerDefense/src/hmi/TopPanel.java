package hmi;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	public void on_listen_to_game_status(Game game) {
	}

	@Override
	public void on_game_cancelled(Game game) {
		removeAll();
		DesktopTowerDefenseMainViewFrame.removeTopPanel();
	}

	@Override
	public void on_game_lost(Game game) {

	}

	@Override
	public void on_game_won(Game game) {
	}

}
