package secondregard.data.inputpdfdocument.builders;

import java.io.File;
import java.util.List;

import common.filesanddirectories.FileNameExtensionAndPathHelper;

public class FilesToZipDataModel {

	private String zipToCreatePrefix;
	private List<String> filesToAddFullPath;
	private List<String> foldersToAddFullPath;

	public String getZipToCreatePrefix() {
		return zipToCreatePrefix;
	}

	public List<String> getFilesToAddFullPath() {
		return filesToAddFullPath;
	}

	public List<String> getFoldersToAddFullPath() {
		return foldersToAddFullPath;
	}

}
