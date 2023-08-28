package main.scenarios;

import main.builders.belligerents.AttackerDataModel;
import main.game.Game;

public class AttackerDelayedCreationTask {

	private Game game;
	private AttackerDataModel attackerToCreateDataModel;

	public AttackerDelayedCreationTask(AttackerDataModel attackerToCreateDataModel, Game game) {
		this.attackerToCreateDataModel = attackerToCreateDataModel;
		this.game = game;
	}

}
