package gameoflife.patterns.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.patterns.Pattern;
import geometry2d.integergeometry.IntegerPrecisionPoint;

/***
 * @link https://conwaylife.com/wiki/Plaintext
 */
public class PlainTextFileFormatPatternLoader extends FilePatternLoader {
	private static final Logger LOGGER = LogManager.getLogger(PlainTextFileFormatPatternLoader.class);

	private static final char COMMENT_LINE_FIRST_CHARACTER = '!';
	private static final char DEAD_CELL_CHARACTER = '.';
	private static final char ALIVE_CELL_CHARACTER = 'O';

	java.util.regex.Pattern cellsDefinitionPattern = java.util.regex.Pattern
			.compile("^[" + "\\" + DEAD_CELL_CHARACTER + ALIVE_CELL_CHARACTER + "]*$");

	@Override
	protected Pattern parseCellsDefinitionsLines(List<String> allCellsDefinitionsLines) {
		Pattern pattern = new Pattern();
		IntStream.range(0, allCellsDefinitionsLines.size()).forEachOrdered(cellDefinitionLineNumber -> {
			String cellDefinitionLine = allCellsDefinitionsLines.get(cellDefinitionLineNumber);
			for (int charPositionInLine = 0; charPositionInLine < cellDefinitionLine.length(); charPositionInLine++) {
				char charAt = cellDefinitionLine.charAt(charPositionInLine);
				if (charAt == ALIVE_CELL_CHARACTER) {
					pattern.addAliveCell(new IntegerPrecisionPoint(charPositionInLine, cellDefinitionLineNumber));
				} else if (charAt == DEAD_CELL_CHARACTER) {
					pattern.addDeadCell(new IntegerPrecisionPoint(charPositionInLine, cellDefinitionLineNumber));
				}
			}
		});
		return pattern;
	}

	@Override
	protected List<Character> commentsCharacters() {
		return List.of(COMMENT_LINE_FIRST_CHARACTER);
	}

	@Override
	protected List<java.util.regex.Pattern> cellsDefinitionPatterns() {
		return List.of(cellsDefinitionPattern);
	}

}
