package hmi;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game_board.GameBoardAttackersEntryArea;

public class ConstructionLocationPanelMouseOverListenerForConstruction extends MouseAdapter {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardAttackersEntryArea.class);

	private ConstructionLocationPanel constructionLocationPanel;
	private JPanel mouseOverSelectionForConstructionWhenEmptyPanel;

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
		constructionLocationPanel.setBackground(constructionLocationPanel.getRandomSelectedBackgroundColorForDebug());
		mouseOverSelectionForConstructionWhenEmptyPanel
				.setBackground(constructionLocationPanel.getRandomSelectedBackgroundColorForDebug());
		constructionLocationPanel.setOpaque(true);
		mouseOverSelectionForConstructionWhenEmptyPanel.setOpaque(true);
	}

	public void mouseClicked(MouseEvent e) {
		if (constructionLocationPanel.getGameBoardPredefinedConstructionLocation().isNewConstructionAllowed()) {

		} else {
			LOGGER.info("");
		}
	}
}
