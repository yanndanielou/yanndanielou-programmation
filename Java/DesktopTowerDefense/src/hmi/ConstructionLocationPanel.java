package hmi;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

		name = "" + random.nextInt();
		/*
		 * setVisible(true);
		 * 
		 * representationAsLabel = new JLabel("pl");
		 * representationAsLabel.setLocation(getLocation());
		 * representationAsLabel.setSize(getSize()); add(representationAsLabel);
		 */ addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent mouseEvent) {
				int mouseX = mouseEvent.getX();
				int mouseY = mouseEvent.getY();

				// TODO Auto-generated method stub
				LOGGER.info(name + " mouseMoved x:" + mouseX + ", y:" + mouseY + " : " + mouseEvent);
			}

			@Override
			public void mouseDragged(MouseEvent mouseEvent) {
				LOGGER.info("mouseDragged" + mouseEvent);

			}
		});

		addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

}
