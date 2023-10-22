package util;

import static matcher.BasicMatchers.containsExactly;
import static matcher.BasicMatchers.containsExactlyAll;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CollectionUtilsTest {

	@Nested
	public class ToList {

		@Test
		public void toListConvertsCollectionToList() {
			// given
			List<String> input = CollectionUtils.asList("a", "a", "b", "b", "b");
			// when
			List<String> output = CollectionUtils.toList(input);
			// then
			assertThat(output, containsExactly("a", "a", "b", "b", "b"));
		}
	}

	@Nested
	public class CopyList {

		@Test
		public void doesNotModifyInitialList() {
			// given
			List<String> input = CollectionUtils.asList("a", "a", "b", "b", "b");
			List<String> copy = CollectionUtils.copy(input);

			assertThat(copy, containsExactlyAll(input));

			input.remove(1);
			assertThat(copy, not(containsExactlyAll(input)));

		}
	}

}