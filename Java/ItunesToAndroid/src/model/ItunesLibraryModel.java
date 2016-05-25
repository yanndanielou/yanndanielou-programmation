package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Logger;
import core.FileUtils;
import core.UserInputs;

public class ItunesLibraryModel {

  private List<ListOfSongs> listOfSongsDictionnaries = new ArrayList<>();
  private Map<File, List<File>> parentAndChildrenRelations = new HashMap<>();
  private File rootDirectoryOfAllSongs = null;
  private UserInputs userInputs;

  public ItunesLibraryModel(UserInputs userInputs) {
    this.userInputs = userInputs;
  }

  public void add(ListOfSongs listOfSongs) {
    listOfSongsDictionnaries.add(listOfSongs);
  }

  public List<ListOfSongs> getListOfSongsDictionnaries() {
    return listOfSongsDictionnaries;
  }

  public void consolidateAfterLoadingItunesLibrary() {
    extractPathForAllSongs();
    tryAndFindRootDirectoryOfAllSongs();
  }

  public void consolidate() {
    buildFilesHierarchy();
  }

  public void tryAndFindRootDirectoryOfAllSongs() {
    for (Song song : allSongsToTakeIntoAccount()) {
      List<File> parentFilesOfCurrentSong = song.getParentFilesHierarchy();
      for (File parentFile : parentFilesOfCurrentSong) {
        boolean currentFileIsCandidate = true;
        for (Song otherSong : allSongsToTakeIntoAccount()) {
          if (song != otherSong) {
            boolean otherSongContainsSameParent = otherSong.getParentFilesHierarchy().contains(parentFile);
            currentFileIsCandidate = currentFileIsCandidate && otherSongContainsSameParent;
          }
        }
        if (currentFileIsCandidate) {
          rootDirectoryOfAllSongs = parentFile;
          Logger.info("Common root directory for all songs found is:" + rootDirectoryOfAllSongs.getAbsolutePath());
          return;
        }
      }
    }
    Logger.info("Could not find common root directory for all songs");
  }

  private void buildFilesHierarchy() {
    for (Song song : allSongsToTakeIntoAccount()) {
      File songFile = song.getFile();
      registerFileUntilTopLevel(songFile);
    }
  }

  private void registerFileUntilTopLevel(File file) {
    File parentFile = file.getParentFile();
    if (parentFile != null) {
      if (!parentAndChildrenRelations.containsKey(parentFile)) {
        parentAndChildrenRelations.put(parentFile, new ArrayList<File>());
      }
      List<File> childrenFiles = parentAndChildrenRelations.get(parentFile);
      if (!childrenFiles.contains(file)) {
        childrenFiles.add(file);
      }

      if (!parentFile.equals(userInputs.getLocalTopLevelFolder())) {
        registerFileUntilTopLevel(parentFile);
      }
    }
  }

  public List<File> getChildren(File file) {
    if (parentAndChildrenRelations.containsKey(file)) {
      return parentAndChildrenRelations.get(file);
    }
    else {
      return new ArrayList<>();
    }
  }

  public File getChildWithName(File file, String searchedName) {
    List<File> children = getChildren(file);
    for (File child : children) {
      String childName = child.getName();
      if (childName.equals(searchedName)) {
        return child;
      }
    }
    return null;
  }

  private void extractPathForAllSongs() {
    for (Song song : allSongsToTakeIntoAccount()) {
      song.extractPath();
    }
  }

  private List<Song> allNotDisabledSongs() {
    List<Song> allNotDisabledSongs = new ArrayList<>();
    for (Song song : allSongs()) {
      if (!song.isDisabled()) {
        allNotDisabledSongs.add(song);
      }
    }
    return allNotDisabledSongs;
  }

  public List<Song> allSongsToTakeIntoAccount() {
    if (userInputs.isExcludeDisabled()) {
      return allNotDisabledSongs();
    } else {
      return allSongs();
    }
  }

  private List<Song> allSongs() {
    List<Song> allSongs = new ArrayList<>();
    for (ListOfSongs listOfSongsDictionnary : listOfSongsDictionnaries) {
      allSongs.addAll(listOfSongsDictionnary);
    }
    return allSongs;
  }

  public File getRootDirectoryOfAllSongs() {
    return rootDirectoryOfAllSongs;
  }
}
