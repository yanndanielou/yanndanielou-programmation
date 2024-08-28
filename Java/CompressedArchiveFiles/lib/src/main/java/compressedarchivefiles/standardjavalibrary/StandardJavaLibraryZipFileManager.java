package compressedarchivefiles.standardjavalibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.filesanddirectories.FileHelper;

@Deprecated()
public class StandardJavaLibraryZipFileManager {

	private static final Logger LOGGER = LogManager.getLogger(StandardJavaLibraryZipFileManager.class);

	public StandardJavaLibraryZipFileManager() {
	}
	
	public boolean createZipFileWithFilesFullPaths(String outputZipFileName, List<String> filesToIncludeInZipFileFullPaths) {
	FileHelper.removeFileIfExists(outputZipFileName);
		
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(outputZipFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOGGER.error(() -> "Could not create output zip file:" + outputZipFileName);
			return false;
		}
		ZipOutputStream zipOutputStream = new ZipOutputStream(fout);
		try {

			for (String fileToIncludeInZipFileFullPath : filesToIncludeInZipFileFullPaths) {
				ZipEntry zipEntry = new ZipEntry(fileToIncludeInZipFileFullPath);
				zipOutputStream.putNextEntry(zipEntry);
				zipOutputStream.closeEntry();
				LOGGER.info(() -> "File :" + fileToIncludeInZipFileFullPath + " added to zip " + outputZipFileName);

			}
			zipOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();

			LOGGER.error(() -> "Error while processing zip file:" + outputZipFileName);
			return false;
		}

		LOGGER.info(() -> "Output zip file:" + outputZipFileName + " created with success");
		return true;
	}


	public boolean createZipFileWithFiles(String outputZipFileName, List<File> filesToIncludeInZipFile) {

		FileHelper.removeFileIfExists(outputZipFileName);
		
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(outputZipFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOGGER.error(() -> "Could not create output zip file:" + outputZipFileName);
			return false;
		}
		ZipOutputStream zipOutputStream = new ZipOutputStream(fout);
		try {

			for (File file : filesToIncludeInZipFile) {
				ZipEntry zipEntry = new ZipEntry(file.getAbsolutePath());
				zipOutputStream.putNextEntry(zipEntry);
				zipOutputStream.closeEntry();
				LOGGER.info(() -> "File :" + file.getName() + " added to zip " + outputZipFileName);

			}
			zipOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();

			LOGGER.error(() -> "Error while processing zip file:" + outputZipFileName);
			return false;
		}

		LOGGER.info(() -> "Output zip file:" + outputZipFileName + " created with success");
		return true;
	}
}
