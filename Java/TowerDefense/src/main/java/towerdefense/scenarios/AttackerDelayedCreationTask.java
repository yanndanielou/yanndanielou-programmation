package towerdefense.scenarios;

import towerdefense.builders.belligerents.AttackerDataModel;
import towerdefense.game.Game;

public class AttackerDelayedCreationTask {

	private Game game;
	private AttackerDataModel attackerToCreateDataModel;

	public AttackerDelayedCreationTask(AttackerDataModel attackerToCreateDataModel, Game game) {
		this.attackerToCreateDataModel = attackerToCreateDataModel;
		this.game = game;
	}

}
