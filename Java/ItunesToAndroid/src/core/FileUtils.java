package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
      System.out.println("ERROR; Could not delete directory:" + emptyDirectory.getAbsolutePath());
    }
    return result;
  }

  public static boolean deleteFile(File file) {
    boolean result = file.delete();
    if (!result) {
      System.out.println("ERROR; Could not delete file:" + file.getAbsolutePath());
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
      e.printStackTrace();
      System.out.println("ERROR; could not copy file:" + destination.getAbsolutePath() + " . Error:" + e.getMessage());
    }
    if (newCopiedFile == null) {
      System.out.println("ERROR; could not create folder:" + destination.getAbsolutePath());
    }
  }

  public static void createFolder(File directoryToCreate) {
    boolean mkdir = directoryToCreate.mkdir();
    if (!mkdir) {
      System.out.println("ERROR; could not create folder:" + directoryToCreate.getAbsolutePath());
    }
  }

}
