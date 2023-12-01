package tictactoeblueskin.game;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Node;
import tictactoeblueskin.hmi.SquareSkin;


public class Square {


	private final SquareSkin skin;

	private ReadOnlyObjectWrapper<SquareState> state = new ReadOnlyObjectWrapper<>(SquareState.EMPTY);

	public ReadOnlyObjectProperty<SquareState> stateProperty() {
		return state.getReadOnlyProperty();
	}

	public SquareState getState() {
		return state.get();
	}

	private final Game game;

	public Square(Game game) {
		this.game = game;

		skin = new SquareSkin(this);
	}

	public void pressed() {
		if (!game.isGameOver() && state.get() == SquareState.EMPTY) {
			state.set(game.getCurrentPlayer());
			game.boardUpdated();
			game.nextTurn();
		}
	}

	public Node getSkin() {
		return skin;
	}
}
