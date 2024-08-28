package common.random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RandomEnumGeneratorTest {
	static final Logger LOGGER = LogManager.getLogger(RandomEnumGeneratorTest.class);

	@Nested
	public class CustomEnum {

		private enum EnumWith5Values {
			A, B, C, D, E;
		}

		@Nested
		public class ManyGenerations {
			final int numberOfRandomGenerations = 100000;
			Map<EnumWith5Values, Integer> occurencesPerEnumValue = new HashMap<>();

			@BeforeEach
			public void before() {
				for (EnumWith5Values value : EnumWith5Values.values()) {
					occurencesPerEnumValue.put(value, 0);
				}
			}

			void logOccurencesPerValue() {

				occurencesPerEnumValue.entrySet().forEach(e -> {
					LOGGER.info(() -> e.getKey() + ", entries:" + e.getValue());

				});

			}

			@Nested
			public class SameInstanceOfGenarator {
				RandomEnumGenerator<EnumWith5Values> randomEnumGenerator = new RandomEnumGenerator<EnumWith5Values>(
						EnumWith5Values.class);

				@Test
				void randomValuesAreApproximatelyEquiDistributed() {
					for (int i = 0; i < numberOfRandomGenerations; i++) {

						EnumWith5Values randomEnum = randomEnumGenerator.randomEnum();
						occurencesPerEnumValue.put(randomEnum, occurencesPerEnumValue.get(randomEnum) + 1);
					}
					logOccurencesPerValue();
					for (EnumWith5Values value : EnumWith5Values.values()) {
						assertThat(value.name(), occurencesPerEnumValue.get(value),
								greaterThan(numberOfRandomGenerations / (EnumWith5Values.values().length + 1)));
					}

				}
			}

			@Nested
			public class NewInstanceOfGenaratorEachTime {
				RandomEnumGenerator<EnumWith5Values> randomEnumGenerator;

				@BeforeEach
				public void before() {
					randomEnumGenerator = new RandomEnumGenerator<EnumWith5Values>(EnumWith5Values.class);
				}

				@Test
				void randomValuesAreApproximatelyEquiDistributed() {
					for (int i = 0; i < numberOfRandomGenerations; i++) {
						EnumWith5Values randomEnum = randomEnumGenerator.randomEnum();
						occurencesPerEnumValue.put(randomEnum, occurencesPerEnumValue.get(randomEnum) + 1);
					}
					logOccurencesPerValue();
					for (EnumWith5Values value : EnumWith5Values.values()) {
						assertThat(value.name(), occurencesPerEnumValue.get(value),
								greaterThan(numberOfRandomGenerations / (EnumWith5Values.values().length + 1)));
					}

				}
			}
		}
	}
}
