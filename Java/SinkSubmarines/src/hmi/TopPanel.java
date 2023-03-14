package hmi;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;

import builders.GameBoardDataModel;

public class TopPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898026190790239124L;
	private Container parentContainer = null;

	public TopPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel, JPanel pannel_above) {

		super(parentContainer, window_width, 100, Color.LIGHT_GRAY, pannel_above);
		/*
		 * this.setSize(window_width, 100); parentContainer.add(this);
		 * this.setBackground(Color.BLACK); this.setBounds(0, 0, this.getSize().width,
		 * this.getSize().height);
		 */
	}

}
