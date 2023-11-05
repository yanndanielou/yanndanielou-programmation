package gameoflife.patterns.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.patterns.Pattern;

public abstract class FilePatternLoader {
	private static final Logger LOGGER = LogManager.getLogger(PlainTextFileFormatPatternLoader.class);

	public Pattern loadFile(String filePath) {
		LOGGER.info(() -> " Load pattern file:" + filePath);
		File file = new File(filePath);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		List<String> allCellsDefinitionsLines = getAllCellsDefinitionsLines(scanner);
		parseCellsDefinitionsLines(allCellsDefinitionsLines);

		scanner.close();
		return null;
	}

	protected abstract Pattern parseCellsDefinitionsLines(List<String> allCellsDefinitionsLines);

	public abstract List<Character> commentsCharacters();

	public abstract List<java.util.regex.Pattern> cellsDefinitionPatterns();

	private boolean isCommentLine(String line) {
		for (Character commentCharacter : commentsCharacters()) {
			if (line.startsWith("" + commentCharacter)) {
				return true;
			}
		}
		return false;
	}

	private boolean isCellDefinition(String line) {
		for (java.util.regex.Pattern cellsDefinitionPattern : cellsDefinitionPatterns()) {
			Matcher matcher = cellsDefinitionPattern.matcher(line);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

	public List<String> getAllCellsDefinitionsLines(Scanner scanner) {
		int lineNumber = 0;
		List<String> allCellsDefinitionsLines = new ArrayList<>();

		while (scanner.hasNextLine()) {
			lineNumber++;
			String line = scanner.nextLine();

			if (isCommentLine(line)) {
				LOGGER.info(() -> "Found comment line:" + line);
			} else if (isCellDefinition(line)) {
				allCellsDefinitionsLines.add(line);
			} else {
				LOGGER.info(() -> "Unsupported line:" + line + " at number");
			}

		}

		return allCellsDefinitionsLines;

	}
}
