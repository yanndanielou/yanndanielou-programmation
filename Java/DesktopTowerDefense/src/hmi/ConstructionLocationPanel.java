package hmi;

import java.awt.Color;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game_board.GameBoardPredefinedConstructionLocation;

public class ConstructionLocationPanel extends JPanel {

	private GameBoardPredefinedConstructionLocation gameBoardPredefinedConstructionLocation;
	private GameBoardPanel gameBoardPanel;

	private JLabel representationAsLabel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1504209832169214216L;

	Random random = new Random();

	public ConstructionLocationPanel(GameBoardPanel gameBoardPanel,
			GameBoardPredefinedConstructionLocation gameBoardPredefinedConstructionLocation) {
		this.gameBoardPredefinedConstructionLocation = gameBoardPredefinedConstructionLocation;
		this.gameBoardPanel = gameBoardPanel;
		this.setLayout(null);
		// this.setBackground(Color.BLACK);
		this.setBackground(new Color(random.nextInt(0, 255), random.nextInt(0, 255), random.nextInt(0, 255)));
		this.setLocation(gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getX(),
				gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getY());
		this.setSize(gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getWidth(),
				gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getHeight());

		/*
		 * setVisible(true);
		 * 
		 * representationAsLabel = new JLabel("pl");
		 * representationAsLabel.setLocation(getLocation());
		 * representationAsLabel.setSize(getSize()); add(representationAsLabel);
		 */
	}

}
