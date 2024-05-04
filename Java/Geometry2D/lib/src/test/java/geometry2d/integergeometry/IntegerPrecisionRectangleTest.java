package geometry2d.integergeometry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IntegerPrecisionRectangleTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	class GetAllPointss {
		@ParameterizedTest
		// @formatter:off
        @CsvSource(value = {
                "1,1",
                "2,2",
                "3,5",
                "5,7",
                "6,7",
                "13,17",
                "14,17",
                "14,17",
                "2000,2003"})
            // @formatter:on
		void ofRectangleCreatedByDimensions(int width, int height) {
			IntegerPrecisionRectangle rectangle = new IntegerPrecisionRectangle(width, height);
			List<IntegerPrecisionPoint> allRectanglePoints = rectangle.getAllPoints();
			assertEquals((width + 1) * (height + 1), allRectanglePoints.size());
		}
	}

	@Nested
	class GetRectangleBoundingBoxOfPoints {
		@ParameterizedTest
		// @formatter:off
        @CsvSource(value = {
                "1,2",
                "2,3",
                "3,4",
                "4,5",
                "5,6",
                "3,5",
                "5,7",
                "6,7",
                "13,17",
                "14,17",
                "14,17",
                "2000,2003"})
            // @formatter:on
		void ofRectangle(int width, int height) {
			IntegerPrecisionRectangle rectangle = new IntegerPrecisionRectangle(width, height);
			List<IntegerPrecisionPoint> allRectanglePoints = rectangle.getAllPoints();
			IntegerPrecisionRectangle rectangleBoundingBoxOfPoints = IntegerPrecisionRectangle
					.getRectangleBoundingBoxOfPoints(allRectanglePoints);
			assertEquals(rectangle, rectangleBoundingBoxOfPoints);
		}
	}

}
