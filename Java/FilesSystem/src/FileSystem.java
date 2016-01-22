import java.io.File;

class FileSystem {

	public void run() {

		UserInputs userInputs = new UserInputs();
		UserInputsUI userInputsUI = new UserInputsUI();

		userInputsUI.fillUserInputs(userInputs);

		File directory = userInputs.getFilesDirectory();
		File[] allFilesInDirectory = directory.listFiles();
		String directoryName = directory.getAbsolutePath();

		String stringToRemoveFromFileName = "iphone avant android ";

		for (File fileInDirectory : allFilesInDirectory) {
			String fileName = fileInDirectory.getName();
			if (fileName.contains(stringToRemoveFromFileName)) {
				String newFileName = fileName.replaceAll(stringToRemoveFromFileName, "");
				File newFile = new File(directory, newFileName);
				fileInDirectory.renameTo(newFile);
			}

		}
		// inputSubtitlesFile.re

	}

}