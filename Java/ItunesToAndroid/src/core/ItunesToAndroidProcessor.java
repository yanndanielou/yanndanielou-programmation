package core;

import java.util.List;

import org.w3c.dom.Document;

import ihm.UserInputsMainView;
import model.ItunesLibraryModel;
import model.ListOfSongs;
import model.Song;

public class ItunesToAndroidProcessor {

  private UserInputs userInputs;
  private UserInputsMainView mainView;

  private ItunesLibraryModelBuilder itunesLibraryModelBuilder;
  private ItunesLibraryModel itunesLibraryModel;

  public ItunesToAndroidProcessor() {
    itunesLibraryModelBuilder = new ItunesLibraryModelBuilder();
  }

  public void run(UserInputs userInputs) {

    XmlItunesLibraryParser xmlItunesLibraryParser = new XmlItunesLibraryParser();
    Document document = xmlItunesLibraryParser.parse(userInputs.getItunesLibraryFile());
    if (document == null) {
      System.out.println("XML was invalid");
      return;
    }
    printDocumentInfos(document);
    itunesLibraryModel = itunesLibraryModelBuilder.build(document);

    printItunesLibraryInfos();
  }

  private void printItunesLibraryInfos() {
    List<ListOfSongs> listOfSongsDictionnaries = itunesLibraryModel.getListOfSongsDictionnaries();

    for (ListOfSongs listOfSongs : listOfSongsDictionnaries) {
      System.out.println("list of songs dictionnary. Number of songs:" + listOfSongs.size());
      for (Song song : listOfSongs) {
        System.out.println("  Song:  ");
        song.printFields();
      }
    }
  }

  private void printDocumentInfos(Document document) {
    //Affiche la version de XML
    System.out.println("XmlVersion: " + document.getXmlVersion());

    //Affiche l'encodage
    System.out.println("XmlEncoding: " + document.getXmlEncoding());

    //Affiche s'il s'agit d'un document standalone    
    System.out.println("XmlStandalone: " + document.getXmlStandalone());

    System.out.println("DocumentURI: " + document.getDocumentURI());

    System.out.println("DocumentElement: " + document.getDocumentElement());
  }
}
