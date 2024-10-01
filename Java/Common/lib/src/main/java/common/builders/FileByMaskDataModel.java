package common.builders;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import common.collection.CollectionUtils;
import common.filesanddirectories.FileNameExtensionAndPathHelper;

public class FileByMaskDataModel extends NamedDataModel {

	private String folderPath;
	private String fileNameMask;
	private String fileName;

	public List<File> getResolvedFiles() {
		if (fileName != null) {
			return CollectionUtils.asList(new File(folderPath + "\\" + fileName));
		} else {

			File[] allFilesNamesWithExtension = FileNameExtensionAndPathHelper.getAllFilesNamesMatchingMask(folderPath,
					fileNameMask);
			return Arrays.asList(allFilesNamesWithExtension);
		}
	}

	@Override
	public String toString() {
		return "FileByMaskDataModel [folderPath=" + folderPath + ", fileNameMask=" + fileNameMask + "]";
	}

}
