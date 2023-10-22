package builders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.core.io.FileSystemResource;

public class LoadResourceFile {

	public LoadResourceFile() {
		System.out.println(getClass().getName());

		String[] gameBoardDataModelJsonFiles = new String[] { "app/src/main/resources/datamodels/SandboxDataModel.json",
				"src/main/resources/datamodels/SandboxDataModel.json",
				"main/resources/datamodels/SandboxDataModel.json", "resources/datamodels/SandboxDataModel.json",
				"datamodels/SandboxDataModel.json", "SandboxDataModel.json" };
		for (String gameBoardDataModelJsonFile : gameBoardDataModelJsonFiles) {
			System.out.println("Test " + gameBoardDataModelJsonFile + " begin");
			System.out.println("FileSystemResource:" + gameBoardDataModelJsonFile + new FileSystemResource(gameBoardDataModelJsonFile));
			System.out.println(LoadResourceFile.class.getResource(gameBoardDataModelJsonFile));
			System.out.println(LoadResourceFile.class.getResourceAsStream(gameBoardDataModelJsonFile));
			System.out.println(getClass().getResource(gameBoardDataModelJsonFile));
			System.out.println(getClass().getResourceAsStream(gameBoardDataModelJsonFile));
			//System.out.println("Bundle:" + gameBoardDataModelJsonFile);
			try {
				System.out.println(ResourceBundle.getBundle(gameBoardDataModelJsonFile));
				System.out.println("Bundle found");
			} catch (MissingResourceException e) {
				System.out.println("Bundle not found");
			}
			try {
				System.out.println(new FileReader(gameBoardDataModelJsonFile));
				System.out.println("FileReader found");
			} catch (FileNotFoundException e) {
				System.out.println("FileReader not found");

			}
			System.out.println("Test " + gameBoardDataModelJsonFile + " end");
		}

	}

}
