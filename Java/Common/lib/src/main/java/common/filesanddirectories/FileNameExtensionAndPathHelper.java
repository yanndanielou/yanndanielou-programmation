package common.filesanddirectories;

import org.apache.commons.io.FilenameUtils;

public class FileNameExtensionAndPathHelper {

	public static String getExtensionWithoutPointOfFile(String fileNameOrPath) {
		return FilenameUtils.getExtension(fileNameOrPath);
	}

	public static String getFileNameWithoutExtension(String fileNameOrPath) {
		return FilenameUtils.getBaseName(fileNameOrPath);
	}

}
