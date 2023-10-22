package greeter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GreeterTest {
	
	@Test
	public void sayHelloTest() {
		Greeter greeter = new Greeter();
		assertEquals("Hello world!", greeter.sayHello());
	}
}
