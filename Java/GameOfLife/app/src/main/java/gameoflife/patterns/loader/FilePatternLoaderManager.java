package gameoflife.patterns.loader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import com.google.common.io.Files;

import gameoflife.patterns.Pattern;

public class FilePatternLoaderManager {
	public static final String RUN_LENGTH_ENCODED_EXTENSION = "rle";
	public static final String PLAINTEXT_EXTENSION = "cells";

	public static Pattern loadFilePattern(File patternfile) {
		String canonicalPath;
		try {
			canonicalPath = patternfile.getCanonicalPath();
		} catch (IOException e) {
			return null;
		}
		FilenameUtils.getExtension(canonicalPath);
		String fileExtension = Files.getFileExtension(canonicalPath);

		if (RUN_LENGTH_ENCODED_EXTENSION.equals(fileExtension)) {
			return null;
		} else if (PLAINTEXT_EXTENSION.equals(fileExtension)) {
			PlainTextFileFormatPatternLoader loader = new PlainTextFileFormatPatternLoader();
			return loader.loadFile(canonicalPath);
		}

		return null;
	}

	public static Pattern loadFilePattern(String patternFilePath) {
		File patternFile = new File(patternFilePath);
		return loadFilePattern(patternFile);
	}
}
