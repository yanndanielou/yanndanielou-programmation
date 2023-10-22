package game.gameboard;

public class GenericGameBoardForTest extends GenericGameBoard {

	private int width;
	private int height;
	
	public GenericGameBoardForTest(int width, int height) {
		this.width = width;
		this.height = height;
		afterConstructor();
	}

	@Override
	protected GenericIntegerGameBoardPoint createGameBoardPoint(int x, int y) {
		return new GenericIntegerGameBoardPoint(x, y);
	}

	@Override
	public int getTotalWidth() {
		return width;
	}

	@Override
	public int getTotalHeight() {
		return height;
	}
}
