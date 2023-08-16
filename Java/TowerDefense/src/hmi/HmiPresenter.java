package hmi;

import builders.TowerDataModel;
import game_board.GameBoardPredefinedConstructionLocation;

public class HmiPresenter {

	private GameBoardPanel gameBoardPanel;
	private SideCommandPanel sideCommandPanel;
	private TowerDefenseMainViewFrame towerDefenseMainViewFrame;

	private TowerDataModel selectedForConstructionTower = null;

	public HmiPresenter(TowerDefenseMainViewFrame towerDefenseMainViewFrame, TopPanel topPanel,
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
