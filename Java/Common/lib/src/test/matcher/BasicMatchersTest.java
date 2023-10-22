package test.matcher;

import static main.matcher.BasicMatchers.contains;
import static main.matcher.BasicMatchers.containsAll;
import static main.matcher.BasicMatchers.containsExactly;
import static main.matcher.BasicMatchers.containsExactlyInAnyOrder;
import static main.matcher.BasicMatchers.empty;
import static main.matcher.BasicMatchers.equalTo;
import static main.matcher.BasicMatchers.greaterThan;
import static main.matcher.BasicMatchers.hasSize;
import static main.matcher.BasicMatchers.is;
import static main.matcher.BasicMatchers.not;
import static main.util.CollectionUtils.asList;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;

import org.junit.Test;


public class BasicMatchersTest {

	@Test
	public void collectionMatchers() throws Exception {
		assertThat(new ArrayList<>(), is(empty()));

		assertThat(asList("1", "2", "3"), hasSize(3));
		assertThat(asList("1", "2", "3"), hasSize(greaterThan(2)));

		assertThat(asList("1", "2", "3"), contains("1"));
		assertThat(asList("1", "2", "3"), contains("1", "2"));
		assertThat(asList("1", "2", "3"), contains("2", "1"));

		assertThat(asList("1", "2", "3"), containsAll(asList("3", "2")));

		assertThat(asList("1", "2", "3"), containsExactly("1", "2", "3"));
		assertThat(asList("1", "2", "3"), containsExactly(equalTo("1"), equalTo("2"), equalTo("3")));
		assertThat(asList("1", "2", "3", "4"), not(containsExactly("1", "2", "3")));

		assertThat(asList("1", "2", "3"), containsExactlyInAnyOrder("3", "2", "1"));

	}

}
