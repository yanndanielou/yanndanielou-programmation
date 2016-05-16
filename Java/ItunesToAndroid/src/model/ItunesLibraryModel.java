package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.UserInputs;

public class ItunesLibraryModel {

  private List<ListOfSongs> listOfSongsDictionnaries = new ArrayList<>();
  private Map<File, List<File>> parentAndChildrenRelations = new HashMap<>();
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

  public void consolidate() {
    extractPathForAllSongs();
    buildFilesHierarchy();
  }

  private void buildFilesHierarchy() {
    for (Song song : allSongs()) {
      File songFile = song.getFile();
      registerFileUntilTopLevel(songFile);
      File parentFile = songFile.getParentFile();
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

      if (!parentFile.equals(userInputs.getTopLevelFolder())) {
        registerFileUntilTopLevel(parentFile);
      }
    }
  }

  private void extractPathForAllSongs() {
    for (Song song : allSongs()) {
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

  private List<Song> allSongs() {
    List<Song> allSongs = new ArrayList<>();
    for (ListOfSongs listOfSongsDictionnary : listOfSongsDictionnaries) {
      allSongs.addAll(listOfSongsDictionnary);
    }
    return allSongs;
  }
}
