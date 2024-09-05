package zip4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.duration.CodeDurationCounter;
import common.duration.FormatterUtils;
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

	private List<File> filesToIncludeInZip;
	private List<File> directoriesToIncludeInZip;
	private String outputZipFileFullPath;
	private String plainTextPassword;

	private boolean isPasswordEncrypted() {
		return plainTextPassword != null;
	}

	public Zip4JZipManager(ZipCreationBuilder zipCreationBuilder) {
		this.filesToIncludeInZip = zipCreationBuilder.filesToIncludeInZip;
		this.directoriesToIncludeInZip = zipCreationBuilder.directoriesToIncludeInZip;
		this.outputZipFileFullPath = zipCreationBuilder.outputZipFileFullPath;
		this.plainTextPassword = zipCreationBuilder.plainTextPassword;
	}

	/**
	 * https://github.com/srikanth-lingala/zip4j?tab=readme-ov-file#creating-a-zip-file-with-multiple-files--adding-multiple-files-to-an-existing-zip
	 * 
	 */
	public boolean createZip() {
		LOGGER.info(() -> "Begin to create zip:" + outputZipFileFullPath);

		CodeDurationCounter saveTimeDurationCounter = new CodeDurationCounter();

		try {

			ZipParameters zipParameters = new ZipParameters();
			zipParameters.setEncryptFiles(true);
			zipParameters.setEncryptionMethod(EncryptionMethod.AES);
			// Below line is optional. AES 256 is used by default. You can override it to
			// use AES 128. AES 192 is supported only for extracting.
			zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);

			ZipFile zipFile = isPasswordEncrypted()
					? new ZipFile(outputZipFileFullPath, plainTextPassword.toCharArray())
					: new ZipFile(outputZipFileFullPath);

			directoriesToIncludeInZip.forEach(e -> {
				try {
					if (isPasswordEncrypted()) {
						zipFile.addFolder(e, zipParameters);

					} else {
						zipFile.addFolder(e);
					}
				} catch (ZipException e1) {
					LOGGER.error("could not create " + outputZipFileFullPath + " for " + e);
					e1.printStackTrace();
				}
			});

			if (!filesToIncludeInZip.isEmpty()) {
				if (isPasswordEncrypted()) {
					zipFile.addFiles(filesToIncludeInZip, zipParameters);

				} else {
					zipFile.addFiles(filesToIncludeInZip);
				}
			}

			LOGGER.info(outputZipFileFullPath + " processed for " + filesToIncludeInZip.size() + " files" + " and "
					+ directoriesToIncludeInZip.size() + " directories, and closed");

			LOGGER.info(() -> "Zip created in "
					+ FormatterUtils.GetDurationAsString(saveTimeDurationCounter.getDuration()));

			zipFile.close();

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

	/**
	 * https://www.digitalocean.com/community/tutorials/builder-design-pattern-in-java
	 */
	public static class ZipCreationBuilder {
		private List<File> filesToIncludeInZip = new ArrayList<File>();
		private List<File> directoriesToIncludeInZip = new ArrayList<File>();
		private String outputZipFileFullPath;
		private String plainTextPassword;

		public ZipCreationBuilder(String outputZipFileFullPath) {
			this.outputZipFileFullPath = outputZipFileFullPath;
		}

		public ZipCreationBuilder addFile(File file) {
			filesToIncludeInZip.add(file);
			return this;
		}

		public ZipCreationBuilder addFiles(List<File> files) {
			filesToIncludeInZip.addAll(files);
			return this;
		}

		public ZipCreationBuilder addFilesByFullPaths(List<String> filesToIncludeInZipFullPaths) {
			List<File> fileFromFullPaths = FileHelper.getFileFromFullPaths(filesToIncludeInZipFullPaths);
			addFiles(fileFromFullPaths);
			return this;
		}

		public ZipCreationBuilder addDirectory(File directory) {
			directoriesToIncludeInZip.add(directory);
			return this;
		}

		public ZipCreationBuilder addDirectories(List<File> directories) {
			directoriesToIncludeInZip.addAll(directories);
			return this;
		}

		public ZipCreationBuilder addDirectoriesByFullPaths(List<String> directoriesToIncludeInZipFullPaths) {
			List<File> directoriesFromFullPaths = FileHelper.getFileFromFullPaths(directoriesToIncludeInZipFullPaths);
			addDirectories(directoriesFromFullPaths);
			return this;
		}

		public ZipCreationBuilder encrypt(String plainTextPassword) {
			this.plainTextPassword = plainTextPassword;
			return this;
		}

		public Zip4JZipManager build() {
			return new Zip4JZipManager(this);
		}

	}
}
