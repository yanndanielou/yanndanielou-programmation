package towerdefense.hmi;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.common.hmi.utils.HMIUtils;
import towerdefense.constants.HMIConstants;
import towerdefense.game.Game;
import towerdefense.game.GameDuration;
import towerdefense.game.GameDurationListener;
import towerdefense.game.GameStatusListener;
import towerdefense.game.Player;
import towerdefense.game.PlayerListener;

public class TopPanel extends JPanel implements GameStatusListener, PlayerListener, GameDurationListener {

	private static final long serialVersionUID = -4722225029326344692L;

	private TowerDefenseMainViewFrame desktopTowerDefenseMainViewFrame;

	private JLabel scoreStaticLabel;
	private JLabel currentScoreTextLabel;

	private JLabel nextAttackersWaveCountdownTimerSymbolAsLabel;
	private JLabel nextAttackersWaveCountdownTimerTextLabel;
	private JLabel remainingLivesSymbolAsLabel;
	private JLabel remainingLivesTextLabel;
	private JLabel goldSymbolAsLabel;
	private JLabel currentGoldTextLabel;

	public TopPanel(TowerDefenseMainViewFrame desktopTowerDefenseMainViewFrame, int width, int height) {
		this.desktopTowerDefenseMainViewFrame = desktopTowerDefenseMainViewFrame;

		setSize(width, height);

		setLayout(null);
		setBackground(HMIConstants.TOP_PANEL_BACKGROUND_COLOR);

		scoreStaticLabel = new JLabel("Score:");
		Font scoreStaticLabelFont = new Font(Font.SANS_SERIF, Font.BOLD, 25);
		scoreStaticLabel.setFont(scoreStaticLabelFont);
		scoreStaticLabel.setSize(scoreStaticLabel.getPreferredSize());
		scoreStaticLabel.setLocation(100, getHeight() / 2 - scoreStaticLabel.getHeight() / 2);
		scoreStaticLabel.setForeground(HMIConstants.SCORE_IN_TOP_PANEL_COLOR);
		add(scoreStaticLabel);

		currentScoreTextLabel = new JLabel("0");
		currentScoreTextLabel.setFont(scoreStaticLabelFont);
		currentScoreTextLabel.setSize(scoreStaticLabel.getSize());
		currentScoreTextLabel.setLocation(
				scoreStaticLabel.getX() + scoreStaticLabel.getWidth()
						+ HMIConstants.TOP_PANEL_HORIZONTAL_SPACES_BETWEEN_SYMBOLS_AND_TEXT_VALUE,
				scoreStaticLabel.getY());
		currentScoreTextLabel.setForeground(HMIConstants.SCORE_IN_TOP_PANEL_COLOR);
		add(currentScoreTextLabel);

		nextAttackersWaveCountdownTimerSymbolAsLabel = HMIUtils
				.createJLabelFromImage("Images/next_attackers_wave_countdown_timer_symbol_in_top_panel.png");
		add(nextAttackersWaveCountdownTimerSymbolAsLabel);
		nextAttackersWaveCountdownTimerSymbolAsLabel.setLocation(400,
				getHeight() / 2 - nextAttackersWaveCountdownTimerSymbolAsLabel.getHeight() / 2);

		nextAttackersWaveCountdownTimerTextLabel = new JLabel("30");
		nextAttackersWaveCountdownTimerTextLabel.setFont(scoreStaticLabelFont);
		nextAttackersWaveCountdownTimerTextLabel.setSize(scoreStaticLabel.getSize());
		nextAttackersWaveCountdownTimerTextLabel.setLocation(
				nextAttackersWaveCountdownTimerSymbolAsLabel.getX()
						+ nextAttackersWaveCountdownTimerSymbolAsLabel.getWidth()
						+ HMIConstants.TOP_PANEL_HORIZONTAL_SPACES_BETWEEN_SYMBOLS_AND_TEXT_VALUE,
				scoreStaticLabel.getY());
		nextAttackersWaveCountdownTimerTextLabel
				.setForeground(HMIConstants.NEXT_ATTACKERS_WAVE_COUNTDOWN_IN_TOP_PANEL_COLOR);
		add(nextAttackersWaveCountdownTimerTextLabel);

		remainingLivesSymbolAsLabel = HMIUtils.createJLabelFromImage("Images/remaining_lives_symbol_in_top_panel.png");
		add(remainingLivesSymbolAsLabel);
		remainingLivesSymbolAsLabel.setLocation(600, getHeight() / 2 - remainingLivesSymbolAsLabel.getHeight() / 2);

		remainingLivesTextLabel = new JLabel("20");
		remainingLivesTextLabel.setFont(scoreStaticLabelFont);
		remainingLivesTextLabel.setSize(scoreStaticLabel.getSize());
		remainingLivesTextLabel.setLocation(
				remainingLivesSymbolAsLabel.getX() + remainingLivesSymbolAsLabel.getWidth()
						+ HMIConstants.TOP_PANEL_HORIZONTAL_SPACES_BETWEEN_SYMBOLS_AND_TEXT_VALUE,
				scoreStaticLabel.getY());
		remainingLivesTextLabel.setForeground(HMIConstants.REMAINING_LIVES_IN_TOP_PANEL_COLOR);
		add(remainingLivesTextLabel);

		goldSymbolAsLabel = HMIUtils.createJLabelFromImage("Images/gold_symbol_in_top_panel.png");
		add(goldSymbolAsLabel);
		goldSymbolAsLabel.setLocation(800, getHeight() / 2 - goldSymbolAsLabel.getHeight() / 2);

		currentGoldTextLabel = new JLabel("99");
		currentGoldTextLabel.setFont(scoreStaticLabelFont);
		currentGoldTextLabel.setSize(scoreStaticLabel.getSize());
		currentGoldTextLabel.setLocation(
				goldSymbolAsLabel.getX() + goldSymbolAsLabel.getWidth()
						+ HMIConstants.TOP_PANEL_HORIZONTAL_SPACES_BETWEEN_SYMBOLS_AND_TEXT_VALUE,
				scoreStaticLabel.getY());
		currentGoldTextLabel.setForeground(HMIConstants.GOLD_IN_TOP_PANEL_COLOR);
		add(currentGoldTextLabel);

	}

	@Override
	public void onListenToGameStatus(Game game) {
		currentGoldTextLabel.setText("" + game.getPlayer().getGold());
		remainingLivesTextLabel.setText("" + game.getPlayer().getRemainingNumberOfLives());
	}

	@Override
	public void onGameCancelled(Game game) {
		removeAll();
		desktopTowerDefenseMainViewFrame.removeTopPanel();
	}

	@Override
	public void onGameLost(Game game) {
		// Auto-generated method stub
	}

	@Override
	public void onGameWon(Game game) {
		// Auto-generated method stub
	}

	@Override
	public void onGameStarted(Game game) {
		// Auto-generated method stub

	}

	@Override
	public void onGoldChange(Player player, int gold) {
		currentGoldTextLabel.setText("" + gold);
	}

	@Override
	public void onRemaningLivesChange(Player player, int remainingLives) {
		remainingLivesTextLabel.setText("" + remainingLives);
	}

	@Override
	public void onSecondsDurationChanged(GameDuration gameDuration, int numberOfSecondsSinceGameStart) {
		// TODO Auto-generated method stub
		
	}

}
