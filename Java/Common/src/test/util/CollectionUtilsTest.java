package test.util;

import static main.matcher.BasicMatchers.containsExactly;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Test;

import main.util.CollectionUtils;

public class CollectionUtilsTest {

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