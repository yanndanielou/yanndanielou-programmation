package gameoflife.patterns.loader;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameoflife.patterns.Pattern;
import geometry2d.integergeometry.IntegerPrecisionRectangle;

class PlainTextFileFormatPatternLoaderTest {
	PlainTextFileFormatPatternLoader loader = new PlainTextFileFormatPatternLoader();

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

	@Test
	void gliderCells() {
		Pattern pattern = FilePatternLoaderManager.loadFilePattern("app/src/main/resources/gameoflife/patterns/glider.cells");
		IntegerPrecisionRectangle rectangleBoundingBox = pattern.getRectangleBoundingBox();
		assertNotNull(pattern);
	}

}
