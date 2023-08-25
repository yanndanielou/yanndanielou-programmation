package main.hmi;

import main.builders.belligerents.TowerDataModel;
import main.gameboard.GameBoardPredefinedConstructionLocation;

public class HmiEditionMode {

	private GameBoardPanel gameBoardPanel;
	@SuppressWarnings("unused")
	private SideCommandPanel sideCommandPanel;
	@SuppressWarnings("unused")
	private TowerDefenseMainViewFrame towerDefenseMainViewFrame;

	private TowerDataModel selectedForConstructionTower = null;

	public HmiEditionMode(TowerDefenseMainViewFrame towerDefenseMainViewFrame, TopPanel topPanel,
			GameBoardPanel gameBoardPanel, SideCommandPanel sideCommandPanel) {
		this.gameBoardPanel = gameBoardPanel;
		this.sideCommandPanel = sideCommandPanel;
		this.towerDefenseMainViewFrame = towerDefenseMainViewFrame;
	}

	public void selectTowerForConstruction(TowerDataModel newlySelectedForConstructionTower) {
		if (selectedForConstructionTower != newlySelectedForConstructionTower) {
			this.selectedForConstructionTower = newlySelectedForConstructionTower;
		}
	}

	public TowerDataModel getSelectedForConstructionTower() {
		return selectedForConstructionTower;
	}

	public void createSelectedTower(GameBoardPredefinedConstructionLocation gameBoardPredefinedConstructionLocation) {
		gameBoardPanel.getGameBoard().getGame().getGameManager().createTower(selectedForConstructionTower,
				gameBoardPredefinedConstructionLocation);
	}
}
