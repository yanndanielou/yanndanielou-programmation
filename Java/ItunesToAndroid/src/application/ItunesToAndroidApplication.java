package application;

import java.io.File;

import common.Logger;
import common.Severity;
import core.ItunesToAndroidProcessor;
import core.UserInputs;
import ihm.ItunesLibraryFileChooser;
import ihm.LocalTopLevelDirectoryChooser;
import ihm.TargetTopLevelFolderChooser;

public class ItunesToAndroidApplication {

  public static void main(String[] args) {

    Logger.configureLevel(Severity.NOTE);

    UserInputs userInputs = new UserInputs();
    ItunesToAndroidProcessor itunesToAndroidProcessor = new ItunesToAndroidProcessor(userInputs);

    ItunesLibraryFileChooser itunesLibraryFileChooser = new ItunesLibraryFileChooser();
    File itunesLibraryFile = itunesLibraryFileChooser.retrieveItunesLibraryFile();

    if (itunesLibraryFile == null) {
      Logger.info("No itunes library file selected. Exiting application");
      return;
    }

    userInputs.setItunesLibraryFile(itunesLibraryFile);

    itunesToAndroidProcessor.loadItunesLibraryXml();

    userInputs.setExcludeDisabled(true);

    File rootDirectoryOfAllSongs = itunesToAndroidProcessor.getItunesLibraryModel().getRootDirectoryOfAllSongs();
    if (rootDirectoryOfAllSongs == null) {
      LocalTopLevelDirectoryChooser localTopLevelDirectoryChooser = new LocalTopLevelDirectoryChooser();
      rootDirectoryOfAllSongs = localTopLevelDirectoryChooser.retrieveLocalTopLevelFolder();
    }
    userInputs.setLocalTopLevelFolder(rootDirectoryOfAllSongs);

    TargetTopLevelFolderChooser targetTopLevelFolderChooser = new TargetTopLevelFolderChooser();
    File retrieveTargetTopLevelFolder = targetTopLevelFolderChooser.retrieveTargetTopLevelFolder();
    if (retrieveTargetTopLevelFolder == null) {
      Logger.info("No target top level folder selected. Exiting application");
      return;
    }

    userInputs.setTargetTopLevelFolder(retrieveTargetTopLevelFolder);

    itunesToAndroidProcessor.compareWithTargetFolder();

  }
}
