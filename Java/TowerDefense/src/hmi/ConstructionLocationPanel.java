package hmi;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game_board.GameBoardPredefinedConstructionLocation;

public class ConstructionLocationPanel extends JPanel {

	private static final Logger LOGGER = LogManager.getLogger(GameBoardPanel.class);
	private String name;

	private GameBoardPredefinedConstructionLocation gameBoardPredefinedConstructionLocation;
	private GameBoardPanel gameBoardPanel;

	private JLabel representationAsLabel;
	private JPanel mouseOverSelectionForConstructionWhenEmptyPanel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1504209832169214216L;
	private Color backgroundColor;
	Random random = new Random();

	public ConstructionLocationPanel(GameBoardPanel gameBoardPanel,
			GameBoardPredefinedConstructionLocation gameBoardPredefinedConstructionLocation) {
		this.gameBoardPredefinedConstructionLocation = gameBoardPredefinedConstructionLocation;
		this.gameBoardPanel = gameBoardPanel;
		this.setLayout(null);
		setOpaque(false);
		backgroundColor = new Color(random.nextInt(0, 255), random.nextInt(0, 255), random.nextInt(0, 255));
		this.setLocation(gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getX(),
				gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getY());
		this.setSize(gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getWidth(),
				gameBoardPredefinedConstructionLocation.getRectangleDefinedArea().getHeight());

		name = "" + random.nextInt();

		mouseOverSelectionForConstructionWhenEmptyPanel = new JPanel();
		mouseOverSelectionForConstructionWhenEmptyPanel.setLayout(null);
		mouseOverSelectionForConstructionWhenEmptyPanel.setSize(getWidth() / 2, getHeight() / 2);
		mouseOverSelectionForConstructionWhenEmptyPanel.setOpaque(false);

		add(mouseOverSelectionForConstructionWhenEmptyPanel);
		mouseOverSelectionForConstructionWhenEmptyPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(null);
				mouseOverSelectionForConstructionWhenEmptyPanel.setBackground(null);
				setOpaque(false);
				mouseOverSelectionForConstructionWhenEmptyPanel.setOpaque(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(backgroundColor);
				mouseOverSelectionForConstructionWhenEmptyPanel.setBackground(backgroundColor);
				setOpaque(true);
				mouseOverSelectionForConstructionWhenEmptyPanel.setOpaque(true);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

}
