package matcher;

import static common.collection.CollectionUtils.asList;
import static matcher.BasicMatchers.contains;
import static matcher.BasicMatchers.containsAll;
import static matcher.BasicMatchers.containsExactly;
import static matcher.BasicMatchers.containsExactlyInAnyOrder;
import static matcher.BasicMatchers.empty;
import static matcher.BasicMatchers.equalTo;
import static matcher.BasicMatchers.greaterThan;
import static matcher.BasicMatchers.hasSize;
import static matcher.BasicMatchers.is;
import static matcher.BasicMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

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
