package tetris.game_objects.tetrominoes_types;

import tetris.game_objects.patterns.Pattern;
import tetris.game_objects.patterns.TetroMinoOSquarePattern;

public enum TetrominoType {

	I_STRAIGHT_LONG_STICK(null), O_SQUARE(new TetroMinoOSquarePattern());

	private Pattern pattern;

	private TetrominoType(Pattern pattern) {
		this.pattern = pattern;
	}

	public Pattern getPattern() {
		return pattern;
	}
}
