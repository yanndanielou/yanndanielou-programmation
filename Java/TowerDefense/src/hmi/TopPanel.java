package hmi;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.HMIConstants;
import game.Game;
import game.GameStatusListener;

public class TopPanel extends JPanel implements GameStatusListener {

	private static final long serialVersionUID = -4722225029326344692L;

	private TowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame;

	private JLabel scoreStaticLabel;

	private JLabel nextAttackersWaveCountdownTimerSymbolAsLabel;
	private JLabel remainingLivesSymbolAsLabel;
	private JLabel goldSymbolAsLabel;

	public TopPanel(TowerDefenseMainViewFrame DesktopTowerDefenseMainViewFrame, int width, int height) {
		this.DesktopTowerDefenseMainViewFrame = DesktopTowerDefenseMainViewFrame;

		setSize(width, height);

		setLayout(null);
		setBackground(HMIConstants.TOP_PANNEL_BACKGROUND_COLOR);

		scoreStaticLabel = new JLabel("Score:");
		Font scoreStaticLabelFont = new Font(Font.SANS_SERIF, Font.BOLD, 25);
		scoreStaticLabel.setFont(scoreStaticLabelFont);
		scoreStaticLabel.setSize(scoreStaticLabel.getPreferredSize());
		scoreStaticLabel.setLocation(100, getHeight() / 2 - scoreStaticLabel.getHeight() / 2);
		scoreStaticLabel.setForeground(HMIConstants.SCORE_IN_TOP_PANNEL_COLOR);
		scoreStaticLabel.setBackground(HMIConstants.SCORE_IN_TOP_PANNEL_COLOR);
		add(scoreStaticLabel);

		nextAttackersWaveCountdownTimerSymbolAsLabel = HMIUtils
				.createJLabelFromImage("Images/next_attackers_wave_countdown_timer_symbol_in_top_panel.png");
		add(nextAttackersWaveCountdownTimerSymbolAsLabel);
		nextAttackersWaveCountdownTimerSymbolAsLabel.setLocation(400,
				getHeight() / 2 - nextAttackersWaveCountdownTimerSymbolAsLabel.getHeight() / 2);

		remainingLivesSymbolAsLabel = HMIUtils.createJLabelFromImage("Images/remaining_lives_symbol_in_top_panel.png");
		add(remainingLivesSymbolAsLabel);
		remainingLivesSymbolAsLabel.setLocation(600, getHeight() / 2 - remainingLivesSymbolAsLabel.getHeight() / 2);

		goldSymbolAsLabel = HMIUtils.createJLabelFromImage("Images/gold_symbol_in_top_panel.png");
		add(goldSymbolAsLabel);
		goldSymbolAsLabel.setLocation(800, getHeight() / 2 - goldSymbolAsLabel.getHeight() / 2);

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
