import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimeNumbersCalculatorTest {


    @Nested
    class Divisors {
        private static Stream<Arguments> provideUniquePrimeDivisors() {
            // @formatter:off
            return Stream.of(
                    Arguments.of(6, new ArrayList<Integer>(Arrays.asList(2,3))),
                    Arguments.of(8, new ArrayList<Integer>(List.of(2))),
                    Arguments.of(9, new ArrayList<Integer>(List.of(3))),
                    Arguments.of(30, new ArrayList<Integer>(Arrays.asList(2,3,5))),
                    Arguments.of(13, new ArrayList<Integer>()),
                    Arguments.of(163789, new ArrayList<Integer>()));
            // @formatter:on
        }

        @ParameterizedTest
        @MethodSource("provideUniquePrimeDivisors")
        void getUniquePrimeDivisors(int number, List<Integer> expectedDivisors) {
            assertEquals(expectedDivisors, PrimeNumbersCalculator.getUniquePrimeDivisors(number));
        }
    }

    @Nested
    class Prime {
        @Nested
        class ComputeAllPrimeNumbers {
            private static Stream<Arguments> provideAllPrimeNumbersUntil() {
                // @formatter:off
                return Stream.of(
                        Arguments.of(0, new ArrayList<Integer>()),
                        Arguments.of(1, new ArrayList<Integer>()),
                        Arguments.of(2, new ArrayList<Integer>(List.of(2))),
                        Arguments.of(3, new ArrayList<Integer>(Arrays.asList(2,3))),
                        Arguments.of(4, new ArrayList<Integer>(Arrays.asList(2,3))),
                        Arguments.of(5, new ArrayList<Integer>(Arrays.asList(2,3,5))),
                        Arguments.of(10, new ArrayList<Integer>(Arrays.asList(2,3,5,7))),
                        Arguments.of(13, new ArrayList<Integer>(Arrays.asList(2,3,5,7,11,13))));
                // @formatter:on
            }

            @ParameterizedTest
            @MethodSource("provideAllPrimeNumbersUntil")
            void computeAllPrimeNumbersUntil(int number, List<Integer> expectedPrimeNumbers) {
                assertEquals(expectedPrimeNumbers, PrimeNumbersCalculator.computeAllPrimeNumbersUntil(number));
            }
        }


        @Nested
        class IsPrime {
            @ParameterizedTest
            // @formatter:off
		@CsvSource(value = {
                "0,FALSE",
                "1,FALSE",
                "2,TRUE",
                "3,TRUE",
                "4,FALSE",
                "5,TRUE",
                "6,FALSE",
                "7,TRUE",
                "8,FALSE",
                "9,FALSE",
                "10,FALSE",
                "11,TRUE",
                "12,FALSE",
                "13,TRUE",
                "14,FALSE",
                "15,FALSE",
                "16,FALSE",
                "17,TRUE",
                "18,FALSE",
                "19,TRUE",
                "20,FALSE",
                "2000,FALSE",
                "2001,FALSE",
                "2003,TRUE",
                "81919,TRUE",
                "163819,TRUE",
                "10007, TRUE",
                "49999, TRUE",
                "100003, TRUE",
                "199999, TRUE",
                "655357,TRUE"},
                nullValues = "NIL")
        // @formatter:on
            void testPrime(Integer numberToTest, Boolean expectedPrimeResult) {
                assertEquals(expectedPrimeResult.booleanValue(), PrimeNumbersCalculator.isPrime(numberToTest));
            }
        }


        @Nested
        class GetNextPrimeNumberGreaterThan {
            @Timeout(value = 100, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
            @ParameterizedTest
            // @formatter:off
            @CsvSource(value = {
                    "3,5",
                    "5,7",
                    "6,7",
                    "13,17",
                    "14,17",
                    "14,17",
                    "2000,2003",
                    "156007,156011"})
                // @formatter:on
            void getNextPrimeNumberGreaterThan(Integer startNumber, Integer expectedNextPrime) {
                assertEquals(expectedNextPrime, PrimeNumbersCalculator.getNextPrimeNumberGreaterThan(startNumber));
            }
        }

    }

}