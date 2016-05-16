package application;

import core.ItunesToAndroidProcessor;
import core.UserInputs;
import ihm.ItunesLibraryFileChooser;
import ihm.LocalTopLevelDirectoryChooser;
import ihm.TargetTopLevelFolderChooser;

public class ItunesToAndroidApplication {

  public static void main(String[] args) {

    UserInputs userInputs = new UserInputs();
    ItunesToAndroidProcessor itunesToAndroidProcessor = new ItunesToAndroidProcessor(userInputs);

    ItunesLibraryFileChooser itunesLibraryFileChooser = new ItunesLibraryFileChooser();
    userInputs.setItunesLibraryFile(itunesLibraryFileChooser.retrieveItunesLibraryFile());

    itunesToAndroidProcessor.loadItunesLibraryXml();

    userInputs.setExcludeDisabled(true);

    LocalTopLevelDirectoryChooser localTopLevelDirectoryChooser = new LocalTopLevelDirectoryChooser();
    userInputs.setLocalTopLevelFolder(localTopLevelDirectoryChooser.retrieveLocalTopLevelFolder());

    TargetTopLevelFolderChooser targetTopLevelFolderChooser = new TargetTopLevelFolderChooser();
    userInputs.setTargetTopLevelFolder(targetTopLevelFolderChooser.retrieveTargetTopLevelFolder());

    itunesToAndroidProcessor.compareWithTargetFolder();

  }
}
