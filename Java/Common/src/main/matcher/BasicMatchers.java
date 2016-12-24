package main.matcher;

import java.util.Collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class BasicMatchers {
	public static <T> org.hamcrest.Matcher<T> is(T value) {
		return org.hamcrest.core.Is.<T>is(value);
	}

	public static <T> org.hamcrest.Matcher<T> is(org.hamcrest.Matcher<T> matcher) {
		return org.hamcrest.core.Is.<T>is(matcher);
	}

	public static <T> org.hamcrest.Matcher<T> not(org.hamcrest.Matcher<T> matcher) {
		return org.hamcrest.core.IsNot.<T>not(matcher);
	}

	public static <T> org.hamcrest.Matcher<T> not(T value) {
		return org.hamcrest.core.IsNot.<T>not(value);
	}

	public static org.hamcrest.Matcher<java.lang.Object> nullValue() {
		return org.hamcrest.core.IsNull.nullValue();
	}

	public static <T> org.hamcrest.Matcher<T> nullValue(java.lang.Class<T> type) {
		return org.hamcrest.core.IsNull.<T>nullValue(type);
	}

	public static org.hamcrest.Matcher<java.lang.Object> notNullValue() {
		return org.hamcrest.core.IsNull.notNullValue();
	}

	public static <T> org.hamcrest.Matcher<T> notNullValue(java.lang.Class<T> type) {
		return org.hamcrest.core.IsNull.<T>notNullValue(type);
	}

	@SafeVarargs
	public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> contains(T... items) {
		return org.hamcrest.core.IsCollectionContaining.<T>hasItems(items);
	}

	@SafeVarargs
	public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> containsExactly(E... items) {
		return org.hamcrest.collection.IsIterableContainingInOrder.<E>contains(items);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> containsExactlyAll(
			final Collection<? super E> collection) {
		Matcher ret = containsExactly(collection.toArray());
		return ret;
	}

	@SafeVarargs
	public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> containsExactly(
			org.hamcrest.Matcher<? super E>... itemMatchers) {
		return org.hamcrest.collection.IsIterableContainingInOrder.<E>contains(itemMatchers);
	}

	public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> containsExactly(
			java.util.List<org.hamcrest.Matcher<? super E>> itemMatchers) {
		return org.hamcrest.collection.IsIterableContainingInOrder.<E>contains(itemMatchers);
	}

	@SafeVarargs
	public static <T> org.hamcrest.Matcher<java.lang.Iterable<? extends T>> containsExactlyInAnyOrder(T... items) {
		return org.hamcrest.collection.IsIterableContainingInAnyOrder.<T>containsInAnyOrder(items);
	}

	public static <T> Matcher<Collection<? super T>> containsAll(final Collection<? super T> collection) {
		Matcher<Collection<? super T>> contains = new BaseMatcher<Collection<? super T>>() {
			@Override
			public boolean matches(Object item) {
				@SuppressWarnings("unchecked")
				Collection<T> matchWith = (Collection<T>) item;
				return matchWith.containsAll(collection);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(collection + "");
			}
		};
		return contains;
	}

	public static <E> org.hamcrest.Matcher<java.util.Collection<? extends E>> hasSize(
			org.hamcrest.Matcher<? super java.lang.Integer> sizeMatcher) {
		return org.hamcrest.collection.IsCollectionWithSize.<E>hasSize(sizeMatcher);
	}

	public static <E> org.hamcrest.Matcher<java.util.Collection<? extends E>> hasSize(int size) {
		return org.hamcrest.collection.IsCollectionWithSize.<E>hasSize(size);
	}

	public static <E> org.hamcrest.Matcher<java.util.Collection<? extends E>> empty() {
		return org.hamcrest.collection.IsEmptyCollection.<E>empty();
	}

	public static <T> org.hamcrest.Matcher<T> equalTo(T operand) {
		return org.hamcrest.core.IsEqual.<T>equalTo(operand);
	}

	public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThan(T value) {
		return org.hamcrest.number.OrderingComparison.<T>greaterThan(value);
	}

	public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThanOrEqualTo(T value) {
		return org.hamcrest.number.OrderingComparison.<T>greaterThanOrEqualTo(value);
	}

	public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThanOrEqualTo(T value) {
		return org.hamcrest.number.OrderingComparison.<T>lessThanOrEqualTo(value);
	}
}
