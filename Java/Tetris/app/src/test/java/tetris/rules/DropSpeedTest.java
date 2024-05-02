package tetris.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DropSpeedTest {

	@ParameterizedTest
	// @formatter:off
	@CsvSource({
		"0.1, 200",
		"0.5, 40",
		"1, 20",
		"2, 10"
		})
	// @formatter:on
	void getNumberOfMillisecondsBetweenEachStepDown(float gravity, int numberOfMillisecondsBetweenEachStepDown) {
		DropSpeed dropSpeed = new DropSpeed(gravity);
		assertEquals(numberOfMillisecondsBetweenEachStepDown, dropSpeed.getNumberOfMillisecondsBetweenEachStepDown());
	}

}
