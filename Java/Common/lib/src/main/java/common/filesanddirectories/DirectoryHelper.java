package common.filesanddirectories;

import java.io.File;

public class DirectoryHelper {

	public static boolean createFolderIfNotExists(String folderName) {
		File dir = new File(folderName);
		if (!dir.exists()) {
			dir.mkdirs();
			return true;
		}
		return false;
	}

}
