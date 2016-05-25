package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import org.w3c.dom.Document;

import model.ItunesLibraryModel;
import model.ListOfSongs;
import model.Song;

public class ItunesToAndroidProcessor {

  private UserInputs userInputs;

  private ItunesLibraryModelBuilder itunesLibraryModelBuilder;
  private ItunesLibraryModel itunesLibraryModel;

  public ItunesToAndroidProcessor(UserInputs userInputs) {
    itunesLibraryModelBuilder = new ItunesLibraryModelBuilder();
    this.userInputs = userInputs;
  }

  public void loadItunesLibraryXml() {

    XmlItunesLibraryParser xmlItunesLibraryParser = new XmlItunesLibraryParser();
    Document document = xmlItunesLibraryParser.parse(userInputs.getItunesLibraryFile());
    if (document == null) {
      System.out.println("XML was invalid");
      return;
    }
    printDocumentInfos(document);
    itunesLibraryModel = itunesLibraryModelBuilder.build(document, userInputs);

    //  printItunesLibraryInfos();
    consolidateAfterLoadingItunesLibrary();
  }

  private void consolidateAfterLoadingItunesLibrary() {
    itunesLibraryModel.consolidateAfterLoadingItunesLibrary();
  }

  public void compareWithTargetFolder() {
    itunesLibraryModel.consolidate();
    buildDiagnostic();
  }

  private void buildDiagnostic() {
    handleMissingFilesAndDirectoryInTarget();
    handleFilesAndDirectoryToDeleteInTarget();
  }

  private void handleMissingFilesAndDirectoryInTarget() {

    // Starting from top level folder in source
    File localTopLevelFolder = userInputs.getLocalTopLevelFolder();
    File targetTopLevelFolder = userInputs.getTargetTopLevelFolder();
    handleMissingFilesAndDirectoryInTarget(localTopLevelFolder, targetTopLevelFolder);

  }

  private void handleMissingFilesAndDirectoryInTarget(File localFolder, File targetFolder) {
    List<File> localChildren = itunesLibraryModel.getChildren(localFolder);

    for (File localChild : localChildren) {
      String name = localChild.getName();

      if (localChild.isDirectory()) {
        File targetChild = new File(targetFolder.getAbsolutePath() + "\\" + name);
        if (!targetChild.exists()) {
          System.out.println("diagnostic; target folder " + targetChild.getAbsolutePath() + " does not exist");
          handleAllMissingFilesAndDirectoryBecauseMissingFolder(localChild, targetChild);

          if (!userInputs.isNoOperation()) {
            FileUtils.createFolder(targetChild);
          }
        }
        handleMissingFilesAndDirectoryInTarget(localChild, targetChild);
      } else {
        File targetChild = new File(targetFolder.getAbsolutePath() + "\\" + name);
        if (!targetChild.exists()) {
          System.out.println("diagnostic; target file " + targetChild.getAbsolutePath() + " does not exist");
          if (!userInputs.isNoOperation()) {
            FileUtils.copyFile(localChild, targetFolder);
          }
        }
      }
    }
  }

  private void handleAllMissingFilesAndDirectoryBecauseMissingFolder(File localDirectory, File missingTargetDirectory) {
    //  System.out.println("diagnostic; target folder " + targetChild.getAbsolutePath() + " does not exist");
  }

  private void handleFilesAndDirectoryToDeleteInTarget() {
    File localTopLevelFolder = userInputs.getLocalTopLevelFolder();
    File targetTopLevelFolder = userInputs.getTargetTopLevelFolder();
    handleFilesAndDirectoryToDeleteInTarget(localTopLevelFolder, targetTopLevelFolder);
  }

  private void handleFilesAndDirectoryToDeleteInTarget(File localFolder, File targetFolder) {
    if (targetFolder.isDirectory()) {
      File[] targetChildren = targetFolder.listFiles();
      for (File targetChild : targetChildren) {
        String childName = targetChild.getName();

        File localChildWithSameName = itunesLibraryModel.getChildWithName(localFolder, childName);
        if (localChildWithSameName == null) {
          System.out.println("diagnostic; target file " + targetChild.getAbsolutePath() + " must be deleted because does not exist in local");

          if (!userInputs.isNoOperation()) {
            FileUtils.deleteFileOrDirectory(targetChild);
          }
        } else {
          handleFilesAndDirectoryToDeleteInTarget(localChildWithSameName, targetChild);
        }
      }
    }
  }

  protected void printItunesLibraryInfos() {
    List<ListOfSongs> listOfSongsDictionnaries = itunesLibraryModel.getListOfSongsDictionnaries();

    for (ListOfSongs listOfSongs : listOfSongsDictionnaries) {
      System.out.println("list of songs dictionnary. Number of songs:" + listOfSongs.size());
      for (Song song : listOfSongs) {
        System.out.println("Begin song");
        song.printAllAttributes();
        System.out.println("End song");
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

  public ItunesLibraryModel getItunesLibraryModel() {
    return itunesLibraryModel;
  }
}
