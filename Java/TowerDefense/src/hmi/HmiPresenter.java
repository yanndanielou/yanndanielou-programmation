package hmi;

import builders.TowerDataModel;

public class HmiPresenter {

	private GameBoardPanel gameBoardPanel;
	private SideCommandPanel sideCommandPanel;
	private TowerDefenseMainViewFrame towerDefenseMainViewFrame;

	private TowerDataModel selectedForConstructionTower = null;

	public HmiPresenter(TowerDefenseMainViewFrame towerDefenseMainViewFrame2, TopPanel topPanel,
			GameBoardPanel gameFieldPanel, SideCommandPanel sideCommandPanel2) {
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
}
