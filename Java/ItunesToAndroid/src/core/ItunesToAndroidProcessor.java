package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import org.w3c.dom.Document;

import common.Logger;
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
      Logger.error("XML was invalid");
      return;
    }
    printDocumentInfos(document);
    itunesLibraryModel = itunesLibraryModelBuilder.build(document, userInputs);

    printItunesLibraryInfos();
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
    checkThatSourceIsNotTarget();
    checkThatSourceIsNotInsideTarget();
    handleMissingFilesAndDirectoryInTarget();
    handleFilesAndDirectoryToDeleteInTarget();
  }

  private void checkThatSourceIsNotTarget() {
    File localTopLevelFolder = userInputs.getLocalTopLevelFolder();
    File targetTopLevelFolder = userInputs.getTargetTopLevelFolder();

    if (FileUtils.areTheSame(localTopLevelFolder, targetTopLevelFolder)) {
      Logger.fatal("Target and source are the same :" + targetTopLevelFolder.getAbsolutePath());
    }
  }

  private void checkThatSourceIsNotInsideTarget() {
    File localTopLevelFolder = userInputs.getLocalTopLevelFolder();
    File targetTopLevelFolder = userInputs.getTargetTopLevelFolder();

    if (FileUtils.doesFolderContains(targetTopLevelFolder, localTopLevelFolder)) {
      Logger.fatal("Target " + targetTopLevelFolder.getAbsolutePath() + " shoud not contain " + localTopLevelFolder.getAbsolutePath());
    }
  }

  private void handleMissingFilesAndDirectoryInTarget() {

    // Starting from top level folder in source
    File localTopLevelFolder = userInputs.getLocalTopLevelFolder();
    File targetTopLevelFolder = userInputs.getTargetTopLevelFolder();
    handleMissingFilesAndDirectoryInTarget(localTopLevelFolder, targetTopLevelFolder);

  }

  private void handleMissingFilesAndDirectoryInTarget(File localFolder, File targetFolder) {
    Logger.debug("diagnostic (debug); Analyzing " + localFolder.getAbsolutePath() + " to detect if exists in target " + targetFolder.getAbsolutePath());

    List<File> localChildren = itunesLibraryModel.getChildren(localFolder);

    for (File localChild : localChildren) {
      String name = localChild.getName();

      if (localChild.isDirectory()) {
        File targetChild = new File(targetFolder.getAbsolutePath() + "\\" + name);
        if (!targetChild.exists()) {
          Logger.info("diagnostic; target folder " + targetChild.getAbsolutePath() + " does not exist");
          handleAllMissingFilesAndDirectoryBecauseMissingFolder(localChild, targetChild);

          if (!userInputs.isNoOperation()) {
            FileUtils.createFolder(targetChild);
          }
        } else {
          Logger.note("diagnostic (debug); target folder " + targetChild.getName() + " exists in both directories. Nothing to be done");
        }
        handleMissingFilesAndDirectoryInTarget(localChild, targetChild);
      } else {
        File targetChild = new File(targetFolder.getAbsolutePath() + "\\" + name);
        if (!targetChild.exists()) {
          Logger.info("diagnostic; target file " + targetChild.getAbsolutePath() + " does not exist");
          if (!userInputs.isNoOperation()) {
            FileUtils.copyFile(localChild, targetFolder);
          }
        } else {
          Logger.note("diagnostic (debug); target file " + targetChild.getName() + " exists in both directories. Nothing to be done");
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
    Logger.debug("diagnostic (debug); Analyzing directory " + targetFolder.getAbsolutePath() + " to detect if something must be deleted");

    if (targetFolder.isDirectory()) {
      File[] targetChildren = targetFolder.listFiles();
      for (File targetChild : targetChildren) {
        String childName = targetChild.getName();

        File localChildWithSameName = itunesLibraryModel.getChildWithName(localFolder, childName);
        if (localChildWithSameName == null) {
          Logger.info("diagnostic; target file " + targetChild.getAbsolutePath() + " must be deleted because does not exist in local");

          if (!userInputs.isNoOperation()) {
            FileUtils.deleteFileOrDirectory(targetChild);
          }
        } else {
          Logger.note("diagnostic (debug); target file " + targetChild.getName() + " exists in both directories. Nothing to be done");

          handleFilesAndDirectoryToDeleteInTarget(localChildWithSameName, targetChild);
        }
      }
    }
  }

  protected void printItunesLibraryInfos() {
    List<ListOfSongs> listOfSongsDictionnaries = itunesLibraryModel.getListOfSongsDictionnaries();

    for (ListOfSongs listOfSongs : listOfSongsDictionnaries) {
      Logger.fullDebug("list of songs dictionnary. Number of songs:" + listOfSongs.size());
      for (Song song : listOfSongs) {
        Logger.fullDebug("Begin song");
        song.printAllAttributes();
        Logger.fullDebug("End song");
      }
    }
  }

  private void printDocumentInfos(Document document) {
    //Affiche la version de XML
    Logger.note("XmlVersion: " + document.getXmlVersion());

    //Affiche l'encodage
    Logger.note("XmlEncoding: " + document.getXmlEncoding());

    //Affiche s'il s'agit d'un document standalone    
    Logger.note("XmlStandalone: " + document.getXmlStandalone());

    Logger.note("DocumentURI: " + document.getDocumentURI());

    Logger.note("DocumentElement: " + document.getDocumentElement());
  }

  public ItunesLibraryModel getItunesLibraryModel() {
    return itunesLibraryModel;
  }
}
