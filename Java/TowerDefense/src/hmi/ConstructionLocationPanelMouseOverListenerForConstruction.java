package hmi;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.HMIConstants;
import game_board.GameBoardAttackersEntryArea;

public class ConstructionLocationPanelMouseOverListenerForConstruction extends MouseAdapter {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardAttackersEntryArea.class);

	private ConstructionLocationPanel constructionLocationPanel;
	private JPanel mouseOverSelectionForConstructionWhenEmptyPanel;
	private boolean constructionAllowed;

	public ConstructionLocationPanelMouseOverListenerForConstruction(
			ConstructionLocationPanel constructionLocationPanel,
			JPanel mouseOverSelectionForConstructionWhenEmptyPanel) {
		this.mouseOverSelectionForConstructionWhenEmptyPanel = mouseOverSelectionForConstructionWhenEmptyPanel;
		this.constructionLocationPanel = constructionLocationPanel;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		constructionLocationPanel.setBackground(null);
		mouseOverSelectionForConstructionWhenEmptyPanel.setBackground(null);
		constructionLocationPanel.setOpaque(false);
		mouseOverSelectionForConstructionWhenEmptyPanel.setOpaque(false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		if (getHmiPresenter().getSelectedForConstructionTower() == null) {
			return;
		}

		updateConstructionAllowed();

		if (constructionAllowed) {
			constructionLocationPanel.setBackground(HMIConstants.POSSIBLE_CONSTRUCTION_COLOR);
			mouseOverSelectionForConstructionWhenEmptyPanel.setBackground(HMIConstants.POSSIBLE_CONSTRUCTION_COLOR);
		} else {
			constructionLocationPanel.setBackground(HMIConstants.IMPOSSIBLE_CONSTRUCTION_COLOR);
			mouseOverSelectionForConstructionWhenEmptyPanel.setBackground(HMIConstants.IMPOSSIBLE_CONSTRUCTION_COLOR);
		}
		constructionLocationPanel.setOpaque(true);
		mouseOverSelectionForConstructionWhenEmptyPanel.setOpaque(true);
	}

	private void updateConstructionAllowed() {
		boolean canAffordConstruction = constructionLocationPanel.getGameBoardPanel().getGameBoard().getGame()
				.getPlayer().canAffordToConstruct(getHmiPresenter().getSelectedForConstructionTower());

		constructionAllowed = canAffordConstruction
				&& constructionLocationPanel.getGameBoardPredefinedConstructionLocation().isNewConstructionAllowed();
	}

	private HmiPresenter getHmiPresenter() {
		return constructionLocationPanel.getHmiPresenter();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (constructionAllowed) {
			getHmiPresenter().createSelectedTower(constructionLocationPanel.getGameBoardPredefinedConstructionLocation());
		} else {
			LOGGER.info("Ignored click, construction is forbidden");
		}
	}
}
