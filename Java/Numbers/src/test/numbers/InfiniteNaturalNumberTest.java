

package test.numbers;

import static org.junit.Assert.*;

import org.junit.Test;

import main.numbers.InfiniteNaturalNumber;

public class InfiniteNaturalNumberTest {

	  @Test
	  public void sameNumberAreEquals() {
		  InfiniteNaturalNumber first_ten = new InfiniteNaturalNumber("10");
		  InfiniteNaturalNumber second_ten = new InfiniteNaturalNumber("10");
		  assertTrue(first_ten.equals(second_ten));
	  }
	  
	  @Test
	  public void tenAndTwentyAreNotEqual() {
		  InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
		  InfiniteNaturalNumber twenty = new InfiniteNaturalNumber("20");
		  assertFalse(ten.equals(twenty));
	  }
	  @Test
	  public void oneAndTenAreNotEqual() {
		  InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
		  InfiniteNaturalNumber one = new InfiniteNaturalNumber("1");
		  assertFalse(ten.equals(one));
	  }
	
}
