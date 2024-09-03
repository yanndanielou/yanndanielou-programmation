package secondregard.data.inputpdfdocument.builders;

import java.util.List;

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
