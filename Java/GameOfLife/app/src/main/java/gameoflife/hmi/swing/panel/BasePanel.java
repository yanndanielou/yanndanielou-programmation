package gameoflife.hmi.swing.panel;

import javax.swing.JPanel;

import gameoflife.hmi.swing.HmiPresenter;
import gameoflife.hmi.swing.KeyBoardInputs;

public abstract class BasePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 375063230319308187L;

	protected HmiPresenter hmiPresenter;

	public BasePanel() {
		this.addKeyListener(new KeyBoardInputs(this, hmiPresenter));
	}

	public HmiPresenter getHmiPresenter() {
		return hmiPresenter;
	}

	public void setHmiPresenter(HmiPresenter hmiPresenter) {
		this.hmiPresenter = hmiPresenter;
	}
}
