package zip4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.filesanddirectories.FileHelper;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

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
			LOGGER.info(outputZipFileFullPath + " processed for " + filesToIncludeInZipFullPaths.size()
					+ " files and closed");
			return true;
		} catch (ZipException e) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			e.printStackTrace();
			return false;
		} catch (IOException io) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			io.printStackTrace();
			return false;
		}
	}

	public static boolean createZipFileWithFilesFullPaths(String outputZipFileFullPath,
			List<String> filesToIncludeInZipFullPaths) {
		return createZipFileWithFiles(outputZipFileFullPath,
				FileHelper.getFileFromFullPaths(filesToIncludeInZipFullPaths));
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
			LOGGER.info(outputZipFileFullPath + " processed for " + filesToIncludeInZipFullPaths.size()
					+ " files and closed");
			return true;
		} catch (ZipException e) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			e.printStackTrace();
			return false;
		} catch (IOException io) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			io.printStackTrace();
			return false;
		}
	}

	public static boolean createZipFileWithFoldersFullPaths(String outputZipFileFullPath,
			List<String> filesToIncludeInZipFullPaths) {
		return createZipFileWithFolders(outputZipFileFullPath,
				FileHelper.getFileFromFullPaths(filesToIncludeInZipFullPaths));
	}

	/**
	 * https://github.com/srikanth-lingala/zip4j?tab=readme-ov-file#creating-a-zip-file-by-adding-a-folder-to-it--adding-a-folder-to-an-existing-zip
	 * 
	 */
	public static boolean createZipFileWithFolders(String outputZipFileFullPath, List<File> foldersToIncludeInZip) {
		try {
			ZipFile zipFile = new ZipFile(outputZipFileFullPath);
			foldersToIncludeInZip.forEach(e -> {
				try {
					zipFile.addFolder(e);
				} catch (ZipException e1) {
					LOGGER.error("could not create " + outputZipFileFullPath + " for " + e);
					e1.printStackTrace();
				}
			});
			zipFile.close();
			LOGGER.info(
					outputZipFileFullPath + " processed for " + foldersToIncludeInZip.size() + " folders and closed");
			return true;
		} catch (ZipException e) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			e.printStackTrace();
			return false;
		} catch (IOException io) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			io.printStackTrace();
			return false;
		}
	}

	public static boolean createEncryptedZipFileWithFilesFolders(String outputZipFileFullPath, String password,
			List<File> foldersToIncludeInZip, List<File> filesToIncludeInZip) {
		foldersToIncludeInZip = ListUtils.emptyIfNull(foldersToIncludeInZip);
		filesToIncludeInZip = ListUtils.emptyIfNull(filesToIncludeInZip);

		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setEncryptFiles(true);
		zipParameters.setEncryptionMethod(EncryptionMethod.AES);
		// Below line is optional. AES 256 is used by default. You can override it to
		// use AES 128. AES 192 is supported only for extracting.
		zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);

		try {
			ZipFile zipFile = new ZipFile(outputZipFileFullPath, password.toCharArray());
			foldersToIncludeInZip.forEach(e -> {
				try {
					zipFile.addFolder(e, zipParameters);
				} catch (ZipException e1) {
					LOGGER.error("could not create " + outputZipFileFullPath + " for " + e);
					e1.printStackTrace();
				}
			});

			if (!filesToIncludeInZip.isEmpty()) {
				zipFile.addFiles(filesToIncludeInZip, zipParameters);
			}

			zipFile.close();
			LOGGER.info(outputZipFileFullPath + " processed for " + foldersToIncludeInZip.size() + " folders and "
					+ filesToIncludeInZip.size() + " files, and closed");
			return true;
		} catch (ZipException e) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			e.printStackTrace();
			return false;
		} catch (IOException io) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			io.printStackTrace();
			return false;
		}
	}

	public static boolean createZipFileWithFolders(String outputZipFileFullPath, File folderToIncludeInZip) {
		try {
			ZipFile zipFile = new ZipFile(outputZipFileFullPath);
			zipFile.addFolder(folderToIncludeInZip);
			zipFile.close();
			LOGGER.info(outputZipFileFullPath + " processed for " + folderToIncludeInZip.getCanonicalPath()
					+ " and closed");
			return true;
		} catch (ZipException e) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			e.printStackTrace();
			return false;
		} catch (IOException io) {
			LOGGER.error("could not create " + outputZipFileFullPath);
			io.printStackTrace();
			return false;
		}
	}
}
