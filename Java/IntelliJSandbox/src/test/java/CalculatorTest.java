import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {


    @org.junit.jupiter.api.Test
    void minus() {
        assertEquals(3, Calculator.minus(5, 2));
    }

    @org.junit.jupiter.api.Test
    void multiply() {
        assertEquals(15, Calculator.multiply(5, 3));

    }

    @Nested
    class Divide {
        @org.junit.jupiter.api.Test
        void noRoundedNeeded() {
            assertEquals(5, Calculator.divide(10, 2));
        }

        @org.junit.jupiter.api.Test
        void rounded() {
            assertEquals(5, Calculator.divide(11, 2));
        }

        @org.junit.jupiter.api.Test
        void divideBy0() {
            assertThrows(ArithmeticException.class,
                    () -> Calculator.divide(10, 0));

        }
    }

    @Nested
    class Plus {
        @org.junit.jupiter.api.Test
        void plus() {
            assertEquals(5, Calculator.plus(3, 2));
        }
    }
}