package common.builders;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import common.filesanddirectories.FileNameExtensionAndPathHelper;

public class FileByMaskDataModel extends NamedDataModel {

	private String folderPath;
	private String fileNameMask;

	public List<File> getResolvedFiles() {
		File[] allFilesNamesWithExtension = FileNameExtensionAndPathHelper.getAllFilesNamesMatchingMask(folderPath,
				fileNameMask);
		return Arrays.asList(allFilesNamesWithExtension);
	}

}
