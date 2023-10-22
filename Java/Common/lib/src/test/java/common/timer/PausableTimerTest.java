package common.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PausableTimerTest {
	static final Logger LOGGER = LogManager.getLogger(PausableTimerTest.class);

	private final static int PERIOD_1_SECOND = 1000;

	private final static int VERY_SLOW_PERIOD_TO_WAIT_FIRST_RUN_WHEN_NO_DELAY = 10;

	protected PausableTimer pausableTimer;
	protected TimerTaskForTests timerTaskForTests;

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

	class TimerTaskForTests extends TimerTask {

		int numberOfTimerRuns = 0;

		@Override
		public void run() {
			System.out.println(new Date() + " PausableTimerTest : run");
			numberOfTimerRuns++;
		}

		public int getNumberOfTimerRuns() {
			return numberOfTimerRuns;
		}
	}

	@BeforeEach
	public void before() {
		timerTaskForTests = new TimerTaskForTests();
	}

	@Nested
	public class SlowTimer {
		@Nested
		public class PausableTimerWithDelay {

			protected int taskDelay = PERIOD_1_SECOND;
			protected int taskPeriod = 5 * PERIOD_1_SECOND;

			@BeforeEach
			public void before() {
				pausableTimer = new PausableTimer();
				pausableTimer.start(timerTaskForTests, taskDelay, taskPeriod);
			}

			@Test
			public void onlyRunsAfterDelay() {
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);
				sleepThread(taskDelay / 2);

				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);
				sleepThread(taskDelay * 2);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);
			}

			@Test
			public void canPauseAndResume() {

				System.out.println(new Date() + " canPauseAndResume : begin");

				sleepThread(taskDelay * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

				pausableTimer.pause();
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

				sleepThread(1000);
				pausableTimer.resume();
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);

			}

			@Test
			public void nothingBadIfPauseATimerAlreadyPaused() {
				pausableTimer.pause();
				pausableTimer.pause();
			}

			@Test
			public void nothingBadIfResumeATimerThatIsNotPaused() {
				pausableTimer.resume();
			}
		}

		@Nested
		public class PausableTimerWithoutDelay {
			protected int taskPeriod = 10 * PERIOD_1_SECOND;

			@BeforeEach
			public void before() {
				pausableTimer = new PausableTimer();
				pausableTimer.start(timerTaskForTests, 0, taskPeriod);
			}

			@Test
			public void canPauseAndResume() {
				System.out.println(new Date() + " canPauseAndResume : begin");

				sleepThread(VERY_SLOW_PERIOD_TO_WAIT_FIRST_RUN_WHEN_NO_DELAY);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

				pausableTimer.pause();
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

				sleepThread(1000);
				pausableTimer.resume();
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);

			}

			@Test
			public void runsJustAfterStart() {

				sleepThread(taskPeriod / 10);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

				sleepThread(taskPeriod * 1.5);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);
			}

			@Test
			public void nothingBadIfPauseATimerAlreadyPaused() {
				pausableTimer.pause();
				pausableTimer.pause();
			}

			@Test
			public void nothingBadIfResumeATimerThatIsNotPaused() {
				pausableTimer.resume();
			}
		}
	}

	@Nested
	public class OneSecondTimer {

		@Nested
		public class TimerWithoutDelay {
			protected int taskPeriod = PERIOD_1_SECOND;

			@BeforeEach
			public void before() {
				pausableTimer = new PausableTimer();
				pausableTimer.start(timerTaskForTests, 0, taskPeriod);
			}

			@Test
			public void doesNotRunDuringPause() {
				sleepThread(VERY_SLOW_PERIOD_TO_WAIT_FIRST_RUN_WHEN_NO_DELAY);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);

				pausableTimer.pause();

				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);
				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);

				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);

			}

			@Test
			public void runAgainAfterResume() {
				sleepThread(VERY_SLOW_PERIOD_TO_WAIT_FIRST_RUN_WHEN_NO_DELAY);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);

				pausableTimer.pause();

				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);
				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);

				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);

				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 2);

				pausableTimer.resume();

				sleepThread(taskPeriod * 1.1);
				assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 3);

			}


		}
	}
}
