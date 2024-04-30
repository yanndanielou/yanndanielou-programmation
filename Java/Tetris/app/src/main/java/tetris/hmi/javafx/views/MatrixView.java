package tetris.hmi.javafx.views;

import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import tetris.game.Game;
import tetris.gameboard.MatrixCell;
import tetris.hmi.generic.views.MatrixViewI;

public class MatrixView extends GridPane implements MatrixViewI {

	private HashMap<MatrixCell, MatrixCellDisplay> gameObjectToHmiObjectMap = new HashMap<>();

	public void initialize(Game game) {

		
		setHgap(0.5); // horizontal gap in pixels => that's what you are asking for
		setVgap(0.5); // vertical gap in pixels
		setPadding(new Insets(10, 10, 10, 10)); // margins around the whole grid (top/right/bottom/left)

		for (MatrixCell matrixCell : game.getGameBoard().getAllGameBoardPointsAsOrderedList().stream()
				.map(MatrixCell.class::cast).toList()) {
			MatrixCellDisplay displayedObject = new MatrixCellDisplay(this, matrixCell);

			gameObjectToHmiObjectMap.put(matrixCell, displayedObject);

			int cellX = matrixCell.getXAsInt() + 1;
			int cellY = matrixCell.getYAsInt() + 1;

			add(displayedObject, cellX, cellY);

		}

	}

}
