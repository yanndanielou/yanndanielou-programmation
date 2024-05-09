package tetris.game_objects.tetrominoes_types;

import common.random.RandomEnumGenerator;
import tetris.game_objects.patterns.Pattern;
import tetris.game_objects.patterns.TetroMinoIStraightLongStickPattern;
import tetris.game_objects.patterns.TetroMinoOSquarePattern;

public enum TetrominoType {

	I_STRAIGHT_LONG_STICK(new TetroMinoIStraightLongStickPattern()), O_SQUARE(new TetroMinoOSquarePattern());

	private Pattern pattern;

	private TetrominoType(Pattern pattern) {
		this.pattern = pattern;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public static TetrominoType random() {
		RandomEnumGenerator<TetrominoType> randomEnumGenerator = new RandomEnumGenerator<TetrominoType>(
				TetrominoType.class);
		return randomEnumGenerator.randomEnum();
	}
}
