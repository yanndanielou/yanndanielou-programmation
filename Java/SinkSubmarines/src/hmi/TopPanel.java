package hmi;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;

public class TopPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898026190790239124L;
	private Container parentContainer = null;
	
	public TopPanel(Container parentContainer, int window_width) {
		this.setSize(window_width, 100);
		parentContainer.add(this);
		this.setBackground(Color.BLACK);
		this.setBounds(0, 0, this.getSize().width, this.getSize().height);
	}
	
}
