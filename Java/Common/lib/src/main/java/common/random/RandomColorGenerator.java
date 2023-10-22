package common.random;

import java.awt.Color;

public class RandomColorGenerator {

	private RandomColorGenerator() {
	}

	public static Color getRandomColor() {
		int r = RandomIntegerGenerator.getRandomNumberUsingNextInt(0, 255);
		int g = RandomIntegerGenerator.getRandomNumberUsingNextInt(0, 255);
		int b = RandomIntegerGenerator.getRandomNumberUsingNextInt(0, 255);

		return new Color(r, g, b);
	}
}
