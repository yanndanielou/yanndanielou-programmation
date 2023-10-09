import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Calculator {
    private static final Logger LOGGER = LogManager.getLogger(Calculator.class);

    public static int plus(int a, int b) {
        int res = a + b;
        LOGGER.info(() -> a + " plus " + b + " = " + res);
        return res;
    }

    public static int minus(int a, int b) {
        int res = a - b;
        LOGGER.info(() -> a + " minus " + b + " = " + res);
        return res;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static int divide(int a, int b) {
        return a / b;
    }

}
