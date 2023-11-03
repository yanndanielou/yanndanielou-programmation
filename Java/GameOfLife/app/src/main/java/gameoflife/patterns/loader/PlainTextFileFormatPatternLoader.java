package gameoflife.patterns.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import gameoflife.patterns.Pattern;

/***
 * @link https://conwaylife.com/wiki/Plaintext
 */
public class PlainTextFileFormatPatternLoader {

	public Pattern loadFile(String filePath) {
		File file = new File(filePath);
		Scanner sc;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		while (sc.hasNextLine()) {

			System.out.println(sc.nextLine());
		}
		sc.close();
		return null;
	}
}
