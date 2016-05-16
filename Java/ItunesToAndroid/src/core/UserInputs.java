package core;

import java.io.File;

public class UserInputs {

  private File topLevelFolder = new File("D:\\temp\\musique");
  private File itunesLibraryFile;
  private boolean excludeDisabled;

  public void setItunesLibraryFile(File itunesLibraryFile) {
    this.itunesLibraryFile = itunesLibraryFile;
  }

  public File getItunesLibraryFile() {
    return itunesLibraryFile;
  }

  public boolean isExcludeDisabled() {
    return excludeDisabled;
  }

  public void setTopLevelFolder(File topLevelFolder) {
    this.topLevelFolder = topLevelFolder;
  }

  public File getTopLevelFolder() {
    return topLevelFolder;
  }

}
