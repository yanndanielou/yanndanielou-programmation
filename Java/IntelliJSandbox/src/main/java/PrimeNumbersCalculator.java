import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrimeNumbersCalculator {
    private static final Logger LOGGER = LogManager.getLogger(Calculator.class);

    private static final ArrayList<Integer> KNOWN_FIRST_PRIME_NUMBERS = new ArrayList<>(Arrays.asList(2, 3, 5, 7));

    // FROM Java 21: use List.getLast
    private static final int BIGGEST_KNOWN_PRIME_NUMBERS = KNOWN_FIRST_PRIME_NUMBERS.get(KNOWN_FIRST_PRIME_NUMBERS.size() - 1);

    // public static List<Integer> getAll
/*
    public static boolean isPrimeMethodA(int a) {
        List<Integer> foundPrimeNumbers = new ArrayList<Integer>(KNOWN_FIRST_PRIME_NUMBERS);

        for (int i = 2; i < a; i++) {
            Boolean isIPrime = false;
            if (i < foundPrimeNumbers.get(foundPrimeNumbers.size() - 1)) {
                isIPrime = foundPrimeNumbers.contains(i);
            } else {
                for (int primeNumber : foundPrimeNumbers) {
                    if (i % primeNumber == 0) {
                        LOGGER.info(i + " can be divided by " + primeNumber + " so is not prime");
                        isIPrime = false;
                    }
                }
                if (isIPrime == null) {
                    isIPrime = true;
                    LOGGER.info(i + " is newly detected prime");
                    foundPrimeNumbers.add(i);
                }
            }
        }

        return true;
    }
*/

    public static List<Integer> printAllPrimeNumberIntoTextFileUntil(int limitOfNumberToTestIfPrime, String filePath) {
        List<Integer> allPrimeNumbersUntil = computeAllPrimeNumbersUntil(limitOfNumberToTestIfPrime);

        // Open the file.
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        PrintWriter finalPrintWriter = printWriter;
        allPrimeNumbersUntil.forEach(e -> finalPrintWriter.println(e));

        // Close the file.
        printWriter.close();  // Step 4

        return allPrimeNumbersUntil;
    }

    public static List<Integer> computeAllPrimeNumbersUntil(int limitOfNumberToTestIfPrime) {
        // FROM Java 21: use List.getLast
        if (limitOfNumberToTestIfPrime < KNOWN_FIRST_PRIME_NUMBERS.get(KNOWN_FIRST_PRIME_NUMBERS.size() - 1)) {
            return KNOWN_FIRST_PRIME_NUMBERS.stream().filter(e -> e <= limitOfNumberToTestIfPrime).collect(Collectors.toList());
        }

        List<Integer> primeNumbersFound = new ArrayList<Integer>(KNOWN_FIRST_PRIME_NUMBERS);
        for (int currentTestedNumber = BIGGEST_KNOWN_PRIME_NUMBERS + 1; currentTestedNumber <= limitOfNumberToTestIfPrime; currentTestedNumber++) {
            int newlyDiscoveredPrimeNumber = getNextPrimeNumberGreaterThan(currentTestedNumber, primeNumbersFound);
            if (newlyDiscoveredPrimeNumber <= limitOfNumberToTestIfPrime) {
                LOGGER.debug("Prime number found:" + newlyDiscoveredPrimeNumber);
                primeNumbersFound.add(newlyDiscoveredPrimeNumber);
                currentTestedNumber = newlyDiscoveredPrimeNumber;
            }
        }

        return primeNumbersFound;
    }

    public static boolean isPrime(int numberToTest, List<Integer> knownConsecutiveFirstPrimeNumbers) {

        if (numberToTest == 0 || numberToTest == 1) {
            return false;
        }

        if (knownConsecutiveFirstPrimeNumbers.isEmpty()) {
            return isPrime(numberToTest);
        }

        int biggestKnownPrimeNumber = knownConsecutiveFirstPrimeNumbers.get(knownConsecutiveFirstPrimeNumbers.size() - 1);
        if (biggestKnownPrimeNumber > numberToTest / 2) {
            List<Integer> divisors = knownConsecutiveFirstPrimeNumbers.stream().filter(e -> Integer.valueOf(numberToTest) % e == 0).collect(Collectors.toList());
            return divisors.isEmpty();
        } else {
            // Try to extend
        }

        return isPrime(numberToTest);

    }

    public static List<Integer> getUniquePrimeDivisors(int number) {
        // TODO: use List<Integer> primeDivisors = new ArrayList<>(KNOWN_FIRST_PRIME_NUMBERS.stream().filter(e -> e < numberToTest && numberToTest % e == 0).collect(Collectors.toList()));
        List<Integer> primeDivisors = new ArrayList<>();

        int remainingNumberOnceDividedByDivisors = number;

        for (int i = 2; i < number && i <= remainingNumberOnceDividedByDivisors; i++) {
            if (remainingNumberOnceDividedByDivisors % i == 0) {
                LOGGER.info(number + " can be divided by " + i);
                primeDivisors.add(i);
                while (remainingNumberOnceDividedByDivisors % i == 0) {
                    remainingNumberOnceDividedByDivisors /= i;
                }

            }
        }
        return primeDivisors;
    }

    public static Integer getAnyPrimeDivisor(int number) {
        if (number == 0 || number == 1) {
            LOGGER.info(number + " is not prime by convention");
            return number;
        }

        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return i;
            }
        }
        return null;
    }

    public static Integer getNextPrimeNumberGreaterThan(int startNumber) {
        List<Integer> startNumberDivisors = getUniquePrimeDivisors(startNumber);
        boolean startNumberIsPrime = startNumberDivisors.isEmpty();
        if (startNumberIsPrime) {
            startNumberDivisors.addAll(computeAllPrimeNumbersUntil(startNumber));
        }
        LOGGER.info("getNextPrimeNumberGreaterThan for " + startNumber + ". Divisors found: " + startNumberDivisors);

        return getNextPrimeNumberGreaterThan(startNumber, startNumberDivisors);
    }

    public static Integer getNextPrimeNumberGreaterThan(int startNumber, List<Integer> startNumberDivisors) {
        for (int i = startNumber + 1; ; i++) {
            boolean iIsPrime = isPrime(i, startNumberDivisors);
            LOGGER.debug("getNextPrimeNumberGreaterThan, tested: " + i + " prime:" + iIsPrime);
            if (iIsPrime) {
                return i;
            }
        }
    }

    public static boolean isPrime(int number) {
        Integer anyDivisor = getAnyPrimeDivisor(number);

        if (anyDivisor != null) {
            LOGGER.info(number + " is not prime because can be divided by " + anyDivisor);
        }

        return anyDivisor == null;
    }

}
