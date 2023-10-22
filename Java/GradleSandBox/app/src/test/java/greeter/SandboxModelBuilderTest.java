package greeter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import builders.DimensionDataModel;
import builders.SandboxDataModel;
import builders.SandboxModelBuilder;

public class SandboxModelBuilderTest {

	@Test
	public void checkDataModel() {
		SandboxModelBuilder sandboxModelBuilder = new SandboxModelBuilder();
		SandboxDataModel gameBoardDataModel = sandboxModelBuilder.getGameBoardDataModel();
		assertNotNull(gameBoardDataModel);
		DimensionDataModel gameBoardDimensions = gameBoardDataModel.getGameBoardDimensions();
		assertNotNull(gameBoardDimensions);
		assertEquals(99, gameBoardDimensions.getDimension().getHeight());
		assertEquals(98, gameBoardDimensions.getDimension().getWidth());
	}
}
