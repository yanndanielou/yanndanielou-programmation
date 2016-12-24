package main.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	  public static <T> Set<T> emptySet() {
	    return new HashSet<>();
	  }

	public static <T> List<T> emptyList() {
		return new ArrayList<>();
	}
}