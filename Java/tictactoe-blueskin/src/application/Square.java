package application;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Node;


class Square {
	enum State {
		EMPTY, NOUGHT, CROSS
	}

	private final SquareSkin skin;

	private ReadOnlyObjectWrapper<State> state = new ReadOnlyObjectWrapper<>(State.EMPTY);

	public ReadOnlyObjectProperty<State> stateProperty() {
		return state.getReadOnlyProperty();
	}

	public State getState() {
		return state.get();
	}

	private final Game game;

	public Square(Game game) {
		this.game = game;

		skin = new SquareSkin(this);
	}

	public void pressed() {
		if (!game.isGameOver() && state.get() == State.EMPTY) {
			state.set(game.getCurrentPlayer());
			game.boardUpdated();
			game.nextTurn();
		}
	}

	public Node getSkin() {
		return skin;
	}
}
