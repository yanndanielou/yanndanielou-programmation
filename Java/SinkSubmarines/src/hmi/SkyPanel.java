package hmi;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;

import builders.GameBoardDataModel;

public class SkyPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898026190790239124L;
	private Container parentContainer = null;

	public SkyPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel, JPanel pannel_above) {

		super(parentContainer, window_width, 77, Color.CYAN, pannel_above);
	/*	this.setSize(window_width, 77);
		parentContainer.add(this);
		this.setBackground(Color.CYAN);
		this.setBounds(0, pannel_above.getY() + pannel_above.getHeight(), this.getSize().width, this.getSize().height);
*/
	}

}
