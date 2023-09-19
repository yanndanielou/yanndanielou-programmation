package geometry2d.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BadGeometryExceptionTest {

	private void throwNewBadGeometryExceptionWithoutParameter() {
		throw new BadGeometryException();
	}

	private void throwNewBadGeometryExceptionWithMessage(String message) {
		throw new BadGeometryException(message);
	}

	@Nested
	class Constructors {

		@Test
		void defaultConstructor() {
			assertThrows(BadGeometryException.class, () -> throwNewBadGeometryExceptionWithoutParameter());
		}

		@Test
		void constructorWithString() {
			String message = "exception message";

			Exception exceptionThrown = assertThrows(BadGeometryException.class,
					() -> throwNewBadGeometryExceptionWithMessage(message));

			assertEquals(message, exceptionThrown.getMessage());
		}
	}

}
