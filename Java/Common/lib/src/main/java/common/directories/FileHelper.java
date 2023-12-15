package common.directories;

import java.io.File;

public class FileHelper {

	public static boolean removeFileIfExists(String fileName) {
		File myObj = new File(fileName);
		if (myObj.delete()) {
			System.out.println("Delete previous version of file file: " + myObj.getName());
			return true;
		} else {
			System.out.println("Failed to delete the file.");
			return false;
		}
	}

}
