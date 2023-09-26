package withouthmi.functional;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import gameoflife.core.GameManager;
import gameoflife.game.Game;

class ConwaysGameOfLifeManuelStepsTest {

	protected GameManager gameManager;
	protected Game game;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Nested
	public class OneStepTest {

		@BeforeEach
		void setUp() throws Exception {
		}

		@AfterEach
		void tearDown() throws Exception {
		}

		@Test
		void test() {
			fail("Not yet implemented");
		}

	}

}