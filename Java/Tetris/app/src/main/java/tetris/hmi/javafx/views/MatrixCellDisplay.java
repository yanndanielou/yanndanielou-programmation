package tetris.hmi.javafx.views;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import tetris.game.CellListener;
import tetris.game_objects.Mino;
import tetris.gameboard.MatrixCell;
import tetris.hmi.javafx.logic.MatrixCellContentManager;

public class MatrixCellDisplay extends Label implements CellListener {

	private MatrixView matrixView;
	private MatrixCell matrixCell;
	private ContextMenu contextMenu;
	private Tooltip tooltip;

	public MatrixCellDisplay(MatrixView matrixView, MatrixCell matrixCell) {
		// this.setText("toto");

		this.matrixView = matrixView;
		this.matrixCell = matrixCell;

		int cellX = matrixCell.getXAsInt();
		int cellY = matrixCell.getYAsInt();

		contextMenu = new ContextMenu();
		tooltip = new Tooltip("x:" + cellX + " y:" + cellY);
		this.setTooltip(tooltip);

		matrixCell.addGameBoardPointListener(this);
		this.setMinSize(20, 20);
		
		refreshContentColor();

	}

	public void refreshContentColor() {
		setBackground(MatrixCellContentManager.getMatrixCellBackground(matrixCell));
	}

	@Override
	public void onCellContentChanged(MatrixCell matrixCells, Mino mino) {
		refreshContentColor();		
	}

}
