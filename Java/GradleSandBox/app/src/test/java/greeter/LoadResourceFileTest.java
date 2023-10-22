package greeter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import builders.LoadResourceFile;

public class LoadResourceFileTest {

	@Test
	public void sayHelloTest() {
		LoadResourceFile loadResourceFile = new LoadResourceFile();
		assertNotNull(loadResourceFile);
	}
}
