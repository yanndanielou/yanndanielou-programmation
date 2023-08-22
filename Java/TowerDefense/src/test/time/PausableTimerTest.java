package test.time;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import main.time.PausableTimer;

@RunWith(HierarchicalContextRunner.class)
public class PausableTimerTest {
	static final Logger LOGGER = LogManager.getLogger(PausableTimerTest.class);

	private final static int PERIOD_1_SECOND = 1000;
	private final static int PERIOD_5_SECONDS = 5 * PERIOD_1_SECOND;
	private final static int PERIOD_10_SECONDS = 10 * PERIOD_1_SECOND;

	protected PausableTimer pausableTimer;
	protected TimerTaskForTests timerTaskForTests;

	class TimerTaskForTests extends TimerTask {

		int numberOfTimerRuns = 0;

		@Override
		public void run() {
			PausableTimerTest.LOGGER.info("PausableTimerTest : run");
			System.out.println(new Date() + " PausableTimerTest : run");
			numberOfTimerRuns++;
		}

		public int getNumberOfTimerRuns() {
			return numberOfTimerRuns;
		}
	}

	@Before
	public void before() {
		timerTaskForTests = new TimerTaskForTests();
	}

	public class PausableTimerWithDelay {

		protected int taskDelay = PERIOD_1_SECOND;
		protected int taskPeriod = 5 * PERIOD_1_SECOND;

		@Before
		public void before() {
			pausableTimer = new PausableTimer();
			pausableTimer.start(timerTaskForTests, taskDelay, taskPeriod);
		}

		@Test
		public void onlyRunsAfterDelay() {
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);
			try {
				Thread.sleep(taskDelay / 2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 0);
			try {
				Thread.sleep(taskDelay * 2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);
		}

		@Test
		public void canPauseAndResume() {

			System.out.println(new Date() + " canPauseAndResume : begin");

			try {
				Thread.sleep(taskDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

			pausableTimer.pause();
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pausableTimer.resume();
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

			try {
				Thread.sleep((long) (taskPeriod * 1.1));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public class PausableTimerWithoutDelay {
		protected int taskPeriod = 5 * PERIOD_1_SECOND;

		@Before
		public void before() {
			pausableTimer = new PausableTimer();
			pausableTimer.start(timerTaskForTests, 0, taskPeriod);
		}

		@Test
		public void canPauseAndResume() {
			System.out.println(new Date() + " canPauseAndResume : begin");

			PausableTimer pausableTimer = new PausableTimer();
			TimerTaskForTests timerTaskForTests = new TimerTaskForTests();
			pausableTimer.start(timerTaskForTests, PERIOD_10_SECONDS, PERIOD_10_SECONDS);

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
		public void runsJustAfterStart() {

			try {
				Thread.sleep((long) (taskPeriod / 10));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(timerTaskForTests.getNumberOfTimerRuns(), 1);

			try {
				Thread.sleep((long) (taskPeriod * 1.5));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
