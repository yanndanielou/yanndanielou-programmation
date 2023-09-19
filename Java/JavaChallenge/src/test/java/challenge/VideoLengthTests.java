package challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VideoLengthTests {
	private String[] strVector = new String[] {"01:00", "13:56", "10:60", "132:21", "132:271", "01:30", "10:00"};
	private int[] resVector = new int[] {60, 836, -1, 7941, -1, 90, 600};
	
	@Test
	public void test01() {
		assertEquals(VideoLength.minutesToSeconds(strVector[0]), resVector[0]);
	}
	
	@Test
	public void test02() {
		assertEquals(VideoLength.minutesToSeconds(strVector[1]), resVector[1]);
	}
	
	@Test
	public void test03() {
		assertEquals(VideoLength.minutesToSeconds(strVector[2]), resVector[2]);
	}
	
	@Test
	public void test04() {
		assertEquals(VideoLength.minutesToSeconds(strVector[3]), resVector[3]);
	}
	
	@Test
	public void test05() {
		assertEquals(VideoLength.minutesToSeconds(strVector[4]), resVector[4]);
	}
	
	@Test
	public void test06() {
		assertEquals(VideoLength.minutesToSeconds(strVector[5]), resVector[5]);
	}
	
	@Test
	public void test07() {
		assertEquals(VideoLength.minutesToSeconds(strVector[6]), resVector[6]);
	}
}