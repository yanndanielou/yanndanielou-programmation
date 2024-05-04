package common.filesanddirectories;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FileNameExtensionAndPathHelper {

	public static String getExtensionWithoutPointOfFile(String fileNameOrPath) {
		return FilenameUtils.getExtension(fileNameOrPath);
	}

	public static String getFileNameWithoutExtension(String fileNameOrPath) {
		return FilenameUtils.getBaseName(fileNameOrPath);
	}

	public static File[] getAllFilesNamesWithExtension(String directoryName, String... extensions) {
		File dir = new File(directoryName);
		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				for (String extension : extensions) {
					if (name.toLowerCase().endsWith(extension)) {
						return true;
					}
				}
				return false;
			}
		});
	}

	public static File[] getAllFilesNamesMatchingMask(String directoryName, String... masks) {
		File directory = new File(directoryName);
		WildcardFileFilter wildcardFileFilter = WildcardFileFilter.builder().setWildcards(masks).get();
		FilenameFilter filenameFilter = (FilenameFilter) wildcardFileFilter;
		File[] listFiles = directory.listFiles(filenameFilter);
		return listFiles;
	}

}
