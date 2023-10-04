package gameoflife.hmi.panel;

import javax.swing.JPanel;

import gameoflife.hmi.HmiPresenter;

public abstract class BasePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 375063230319308187L;
	
	protected HmiPresenter hmiPresenter;

	public HmiPresenter getHmiPresenter() {
		return hmiPresenter;
	}
	
	public void setHmiPresenter(HmiPresenter hmiPresenter) {
		this.hmiPresenter = hmiPresenter;
	}
}
