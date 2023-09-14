package test.common.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.common.exceptions.BadLogicException;
import main.common.timer.PausableOneShotDelayedTask;

public class PausableOneShotDelayedTaskTest {
	static final Logger LOGGER = LogManager.getLogger(PausableOneShotDelayedTaskTest.class);

	private final static int DELAY_1_SECOND = 1000;
	private final static int DELAY_5_SECONDS = 5 * DELAY_1_SECOND;
	private final static int DELAY_10_SECONDS = 10 * DELAY_1_SECOND;

	protected PausableOneShotDelayedTaskForTests timerTaskForTests;

	private static void sleepThread(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void sleepThread(double milliseconds) {
		sleepThread((int) milliseconds);
	}

	class PausableOneShotDelayedTaskForTests extends PausableOneShotDelayedTask {

		int numberOfTimerRuns = 0;

		public PausableOneShotDelayedTaskForTests(long delay) {
			super(delay);
		}

		@Override
		public void run() {
			System.out.println(new Date() + " PausableTimerTest : run");
			numberOfTimerRuns++;

			if (numberOfTimerRuns > 1) {
				throw new BadLogicException("numberOfTimerRuns cannot be greater than 1, but is " + numberOfTimerRuns);
			}

		}

		public int getNumberOfTimerRuns() {
			return numberOfTimerRuns;
		}
	}

	@Nested
	public class OneSecondTimer {

		private int taskDelay = DELAY_1_SECOND;

		@BeforeEach
		public void before() {
			timerTaskForTests = new PausableOneShotDelayedTaskForTests(taskDelay);
		}

		@Test
		public void runAfterDelay() {
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			sleepThread(taskDelay * 1.1);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);
		}

		@Test
		public void onlyRunOnceAfterDelay() {
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			sleepThread(taskDelay * 1.1);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

			sleepThread(taskDelay * 1.1);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);
		}

		@Test
		public void doesNotRunDuringPause() {
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.pause();
			sleepThread(taskDelay * 1.1);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			sleepThread(taskDelay * 1.1);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);
		}

		@Test
		public void runAgainAfterResume() {
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.pause();
			sleepThread(taskDelay * 1.1);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.resume();
			sleepThread(taskDelay * 1.1);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);
		}

		@Test
		public void doesNotRunIfCancel() {
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.cancel();
			sleepThread(taskDelay * 1.1);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);
		}

		@Test
		public void cannotBeResumedIfCancel() {
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.cancel();
			sleepThread(taskDelay * 1.1);

			assertThrows(BadLogicException.class, () -> timerTaskForTests.resume());
		}

		@Test
		public void cannotBePausedIfCancel() {
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.cancel();
			sleepThread(100);

			assertThrows(BadLogicException.class, () -> timerTaskForTests.pause());
		}

		@Test
		public void cannotBePausedIfAlreadyPaused() {
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.pause();
			sleepThread(100);

			assertThrows(BadLogicException.class, () -> timerTaskForTests.pause());
		}

		@Test
		public void canPauseAndResumeSeveralTimesAndRunAfterGoodDelay() {

			int cumulatedTimeOutsideOfPause = 0;
			int cumulatedTimeDuringPause = 0;

			for (int i = 0; i < 9; i++) {

				timerTaskForTests.pause();
				sleepThread(200);
				cumulatedTimeDuringPause += 100;
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

				timerTaskForTests.resume();
				sleepThread(100);
				cumulatedTimeOutsideOfPause += 100;
				System.out.println("Cumulated waited outside of pause: " + cumulatedTimeOutsideOfPause);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);
			}

			sleepThread(100);
			cumulatedTimeOutsideOfPause += 100;
			System.out.println("Cumulated waited outside of pause: " + cumulatedTimeOutsideOfPause);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);
		}

		@Test
		public void canPauseAndResumeSeveralTimesWithoutCrash() {
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.pause();
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.resume();
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.pause();
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.resume();
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.pause();
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

			timerTaskForTests.resume();
			sleepThread(100);
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);

		}

	}
}
