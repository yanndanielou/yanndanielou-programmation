package main.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CollectionUtils {

	public static <T> List<T> toList(Collection<T> from) {
		return new ArrayList<>(from);
	}

	@SafeVarargs
	public static <T> List<T> asList(T... items) {
		List<T> ret = new ArrayList<>();
		if (items != null) {
			for (T item : items) {
				ret.add(item);
			}
		}
		return ret;
	}

	public static <T> List<T> emptyList() {
		return new ArrayList<>();
	}
}