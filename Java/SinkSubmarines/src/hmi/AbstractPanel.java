package hmi;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5846785174444770001L;

	public AbstractPanel(Container parentContainer, int width, int height, Color color, JPanel pannel_above) {
		this.setSize(width, height);
		parentContainer.add(this);
		this.setBackground(color);

		int pannel_above_Y = pannel_above != null ? (int)pannel_above.getBounds().getY() : 0;
		int pannel_above_height = pannel_above != null ? pannel_above.getHeight() : 0;
		this.setBounds(0, pannel_above_Y + pannel_above_height, this.getSize().width, this.getSize().height);
	}

}
