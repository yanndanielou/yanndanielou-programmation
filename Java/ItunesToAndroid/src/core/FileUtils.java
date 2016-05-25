package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import common.Logger;

public final class FileUtils {

  private FileUtils() {
  }

  public static boolean deleteDirectory(File directory) {
    boolean cleanDirectory = cleanDirectory(directory);
    if (cleanDirectory) {
      return deleteEmptyDirectory(directory);
    }
    return false;
  }

  public static boolean cleanDirectory(File directory) {
    boolean result = true;
    File[] children = directory.listFiles();
    for (File child : children) {
      result = result && deleteFileOrDirectory(child);
    }
    return result;
  }

  private static boolean deleteEmptyDirectory(File emptyDirectory) {
    boolean result = emptyDirectory.delete();
    if (!result) {
      Logger.error("Could not delete directory:" + emptyDirectory.getAbsolutePath());
    } else {
      Logger.note("FileUtils; directory " + emptyDirectory.getAbsolutePath() + " deleted with success");
    }
    return result;
  }

  public static boolean deleteFile(File file) {
    boolean result = file.delete();
    if (!result) {
      Logger.error("Could not delete file:" + file.getAbsolutePath());
    } else {
      Logger.note("FileUtils; file " + file.getAbsolutePath() + " deleted with success");
    }
    return result;
  }

  public static boolean deleteFileOrDirectory(File toBeDeleted) {
    if (toBeDeleted.isDirectory()) {
      return deleteDirectory(toBeDeleted);
    } else {
      return deleteFile(toBeDeleted);
    }
  }

  public static void copyFile(File source, File destination) {
    Path sourcePath = source.toPath();
    Path targetDirectoryPath = destination.toPath();
    Path newCopiedFile = null;
    try {
      newCopiedFile = Files.copy(sourcePath, targetDirectoryPath.resolve(sourcePath.getFileName()));
    } catch (IOException e) {
      Logger.error("could not copy file:" + destination.getAbsolutePath() + " . Error:" + e.getMessage());
      return;
    }
    if (newCopiedFile == null) {
      Logger.error("could not copy folder:" + destination.getAbsolutePath());
    } else {
      Logger.note("FileUtils; file " + source.getAbsolutePath() + " copied to " + destination.getAbsolutePath() + " with success");
    }
  }

  public static void createFolder(File directoryToCreate) {
    boolean mkdir = directoryToCreate.mkdir();
    if (!mkdir) {
      Logger.error("could not create folder:" + directoryToCreate.getAbsolutePath());
    } else {
      Logger.note("FileUtils; folder created with success:" + directoryToCreate.getAbsolutePath());
    }
  }

  public static List<File> getParentFilesHierarchy(File file) {
    List<File> parentFilesHierarchy = new ArrayList<>();
    File currentFile = file;
    while ((currentFile = currentFile.getParentFile()) != null) {
      parentFilesHierarchy.add(currentFile);
    }
    return parentFilesHierarchy;
  }

  public static boolean areTheSame(File left, File right) {
    return left.equals(right);
  }

  public static boolean doesFolderContains(File folder, File searched) {
    File[] children = folder.listFiles();
    boolean contains = false;
    for (File child : children) {
      if (child.equals(searched)) {
        contains = true;
      }
      if (child.isDirectory()) {
        contains = contains || doesFolderContains(child, searched);
      }
    }
    return contains;
  }

}
