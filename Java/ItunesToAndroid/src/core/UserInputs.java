package core;

import java.io.File;

public class UserInputs {

  private File localTopLevelFolder;
  private File targetTopLevelFolder;
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

  public void setLocalTopLevelFolder(File topLevelFolder) {
    this.localTopLevelFolder = topLevelFolder;
  }

  public File getLocalTopLevelFolder() {
    return localTopLevelFolder;
  }

  public void setTargetTopLevelFolder(File targetTopLevelFolder) {
    this.targetTopLevelFolder = targetTopLevelFolder;
  }

  public File getTargetTopLevelFolder() {
    return targetTopLevelFolder;
  }

}
