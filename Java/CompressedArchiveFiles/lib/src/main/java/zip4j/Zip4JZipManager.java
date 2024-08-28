package zip4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.filesanddirectories.FileHelper;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * https://github.com/srikanth-lingala/zip4j
 */
public class Zip4JZipManager {
	private static final Logger LOGGER = LogManager.getLogger(Zip4JZipManager.class);

	
	/**
	 * https://github.com/srikanth-lingala/zip4j?tab=readme-ov-file#creating-a-zip-file-with-multiple-files--adding-multiple-files-to-an-existing-zip
	 * 
	 */
	public static boolean createEncryptedZipFileWithFilesFullPaths(String outputZipFileFullPath, String password,
			List<String> filesToIncludeInZipFullPaths) {
		List<File> filesToIncludeInZip = FileHelper.getFileFromFullPaths(filesToIncludeInZipFullPaths);
		return createEncryptedZipFileWithFiles(outputZipFileFullPath, password, filesToIncludeInZip);
	}

	/**
	 * https://github.com/srikanth-lingala/zip4j?tab=readme-ov-file#creating-a-zip-file-with-multiple-files--adding-multiple-files-to-an-existing-zip
	 * 
	 */
	public static boolean createEncryptedZipFileWithFiles(String outputZipFileFullPath, String password,
			List<File> filesToIncludeInZipFullPaths) {
		try {
			ZipFile zipFile = new ZipFile(outputZipFileFullPath);
			zipFile.addFiles(filesToIncludeInZipFullPaths);
			zipFile.close();
			return true;
		} catch (ZipException e) {
			e.printStackTrace();
			return false;
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		}
	}
	

	public static boolean createZipFileWithFilesFullPaths(String outputZipFileFullPath,
			List<String> filesToIncludeInZipFullPaths) {
		return createZipFileWithFiles(outputZipFileFullPath, FileHelper.getFileFromFullPaths(filesToIncludeInZipFullPaths));
	}
	/**
	 * https://github.com/srikanth-lingala/zip4j?tab=readme-ov-file#creating-a-zip-file-with-multiple-files--adding-multiple-files-to-an-existing-zip
	 * 
	 */
	public static boolean createZipFileWithFiles(String outputZipFileFullPath,
			List<File> filesToIncludeInZipFullPaths) {
		try {
			ZipFile zipFile = new ZipFile(outputZipFileFullPath);
			zipFile.addFiles(filesToIncludeInZipFullPaths);
			zipFile.close();
			return true;
		} catch (ZipException e) {
			e.printStackTrace();
			return false;
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		}
	}
}
