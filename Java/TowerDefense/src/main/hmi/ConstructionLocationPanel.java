package main.hmi;

import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.gameboard.GameBoardPredefinedConstructionLocation;

public class ConstructionLocationPanel extends JPanel {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(ConstructionLocationPanel.class);

	private GameBoardPredefinedConstructionLocation gameBoardPredefinedConstructionLocation;
	private GameBoardPanel gameBoardPanel;

	private JPanel mouseOverSelectionForConstructionWhenEmptyPanel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1504209832169214216L;
	private Color randomSelectedBackgroundColorForDebug;
	private Random random = new Random();

	public ConstructionLocationPanel(GameBoardPanel gameBoardPanel,
			GameBoardPredefinedConstructionLocation gameBoardPredefinedConstructionLocation) {
		this.gameBoardPredefinedConstructionLocation = gameBoardPredefinedConstructionLocation;
		this.gameBoardPanel = gameBoardPanel;
		this.setLayout(null);
		setOpaque(false);
		randomSelectedBackgroundColorForDebug = new Color(random.nextInt(0, 255), random.nextInt(0, 255),
				random.nextInt(0, 255));
		this.setLocation(gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getX(),
				gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getY());
		this.setSize(gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getWidth(),
				gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getHeight());

		mouseOverSelectionForConstructionWhenEmptyPanel = new JPanel();
		mouseOverSelectionForConstructionWhenEmptyPanel.setLayout(null);
		mouseOverSelectionForConstructionWhenEmptyPanel.setSize(getWidth() / 2, getHeight() / 2);
		mouseOverSelectionForConstructionWhenEmptyPanel.setOpaque(false);

		add(mouseOverSelectionForConstructionWhenEmptyPanel);
		mouseOverSelectionForConstructionWhenEmptyPanel
				.addMouseListener(new ConstructionLocationPanelMouseOverListenerForConstruction(this,
						mouseOverSelectionForConstructionWhenEmptyPanel));
	}

	public Color getRandomSelectedBackgroundColorForDebug() {
		return randomSelectedBackgroundColorForDebug;
	}

	public GameBoardPanel getGameBoardPanel() {
		return gameBoardPanel;
	}

	public GameBoardPredefinedConstructionLocation getGameBoardPredefinedConstructionLocation() {
		return gameBoardPredefinedConstructionLocation;
	}

	public HmiPresenter getHmiPresenter() {
		return gameBoardPanel.getHmiPresenter();
	}
}
