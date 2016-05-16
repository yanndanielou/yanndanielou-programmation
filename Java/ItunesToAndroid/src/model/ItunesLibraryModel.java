package model;

import java.util.ArrayList;
import java.util.List;

public class ItunesLibraryModel {

  private List<ListOfSongs> listOfSongsDictionnaries = new ArrayList<ListOfSongs>();

  public ItunesLibraryModel() {
  }

  public void add(ListOfSongs listOfSongs) {
    listOfSongsDictionnaries.add(listOfSongs);
  }

  public List<ListOfSongs> getListOfSongsDictionnaries() {
    return listOfSongsDictionnaries;
  }
}
