package test.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import main.time.PausableTimer;

class PausableTimerTest {
	private static final Logger LOGGER = LogManager.getLogger(PausableTimerTest.class);

	private final static int PERIOD_1_SECOND = 1000;
	private final static int PERIOD_5_SECONDS = 5 * PERIOD_1_SECOND;
	private final static int PERIOD_10_SECONDS = 10 * PERIOD_1_SECOND;

	private class TimerTaskForTests extends TimerTask {

		int numberOfTimerRuns = 0;

		@Override
		public void run() {
			LOGGER.info("PausableTimerTest : run");
			System.out.println(new Date() + " PausableTimerTest : run");
			numberOfTimerRuns++;
		}

		public int getNumberOfTimerRuns() {
			return numberOfTimerRuns;
		}
	}

	@Test
	void canPauseAndResume() {
		System.out.println(new Date() + " canPauseAndResume : begin");

		PausableTimer pausableTimer = new PausableTimer();
		TimerTaskForTests timerTaskForTests = new TimerTaskForTests();
		pausableTimer.start(timerTaskForTests, PERIOD_10_SECONDS, PERIOD_10_SECONDS);
		assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

		pausableTimer.pause();
		assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pausableTimer.resume();
		assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
