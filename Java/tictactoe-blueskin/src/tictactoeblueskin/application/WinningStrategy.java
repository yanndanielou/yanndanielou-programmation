package tictactoeblueskin.application;

import java.util.HashMap;
import java.util.Map;

public class WinningStrategy {
	private final tictactoeblueskin.game.Board board;

	private static final int NOUGHT_WON = 3;
	private static final int CROSS_WON = 30;

	private static final Map<Square.State, Integer> values = new HashMap<>();
	static {
		values.put(Square.State.EMPTY, 0);
		values.put(Square.State.NOUGHT, 1);
		values.put(Square.State.CROSS, 10);
	}

	public WinningStrategy(tictactoeblueskin.game.Board board) {
		this.board = board;
	}

	public Square.State getWinner() {
		for (int i = 0; i < 3; i++) {
			int score = 0;
			for (int j = 0; j < 3; j++) {
				score += valueOf(i, j);
			}
			if (isWinning(score)) {
				return winner(score);
			}
		}

		for (int i = 0; i < 3; i++) {
			int score = 0;
			for (int j = 0; j < 3; j++) {
				score += valueOf(j, i);
			}
			if (isWinning(score)) {
				return winner(score);
			}
		}

		int score = 0;
		score += valueOf(0, 0);
		score += valueOf(1, 1);
		score += valueOf(2, 2);
		if (isWinning(score)) {
			return winner(score);
		}

		score = 0;
		score += valueOf(2, 0);
		score += valueOf(1, 1);
		score += valueOf(0, 2);
		if (isWinning(score)) {
			return winner(score);
		}

		return Square.State.EMPTY;
	}

	public boolean isDrawn() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board.getSquare(i, j).getState() == Square.State.EMPTY) {
					return false;
				}
			}
		}

		return getWinner() == Square.State.EMPTY;
	}

	private Integer valueOf(int i, int j) {
		return values.get(board.getSquare(i, j).getState());
	}

	private boolean isWinning(int score) {
		return score == NOUGHT_WON || score == CROSS_WON;
	}

	private Square.State winner(int score) {
		if (score == NOUGHT_WON)
			return Square.State.NOUGHT;
		if (score == CROSS_WON)
			return Square.State.CROSS;

		return Square.State.EMPTY;
	}
}

