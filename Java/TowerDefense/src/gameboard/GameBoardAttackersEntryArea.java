package gameboard;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardNamedAreaDataModel;
import builders.gameboard.RectangleDataModel;
import geometry.IntegerRectangle;

public class GameBoardAttackersEntryArea extends GameBoardArea {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardAttackersEntryArea.class);

	private String associatedAttackersExitAreaName;

	private GameBoardAttackersExitArea associatedGameBoardAttackersExitArea;

	public GameBoardAttackersEntryArea(GameBoard gameBoard, RectangleDataModel rectangleDataModel,
			String associatedAttackersExitAreaName) {
		super(gameBoard, rectangleDataModel);
		this.associatedAttackersExitAreaName = associatedAttackersExitAreaName;
		computeAssociatedGameBoardAttackersExitArea(associatedAttackersExitAreaName);
	}

	public GameBoardAttackersEntryArea(GameBoard gameBoard, IntegerRectangle rectangleInImageWithRGB,
			GameBoardNamedAreaDataModel gameBoardNamedAreaDataModel, String associatedAttackersExitAreaName) {
		super(gameBoard, rectangleInImageWithRGB, gameBoardNamedAreaDataModel);
		this.associatedAttackersExitAreaName = associatedAttackersExitAreaName;
		computeAssociatedGameBoardAttackersExitArea(associatedAttackersExitAreaName);
	}

	private void computeAssociatedGameBoardAttackersExitArea(String associatedAttackersExitAreaName) {

		Optional<GameBoardAttackersExitArea> findAny = gameBoard.getGameBoardAttackersExitAreas().stream()
				.filter(item -> Objects.equals(item.getName(), associatedAttackersExitAreaName)).findAny();
		if (findAny.isEmpty()) {
			throw new NoSuchElementException(
					"GameBoardAttackersExitAreas with name:" + associatedAttackersExitAreaName);
		}
		associatedGameBoardAttackersExitArea = findAny.get();
	}

}
