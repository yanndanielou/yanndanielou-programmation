package application;

import java.io.File;

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
    File itunesLibraryFile = itunesLibraryFileChooser.retrieveItunesLibraryFile();

    if (itunesLibraryFile == null) {
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
    userInputs.setTargetTopLevelFolder(targetTopLevelFolderChooser.retrieveTargetTopLevelFolder());

    itunesToAndroidProcessor.compareWithTargetFolder();

  }
}
