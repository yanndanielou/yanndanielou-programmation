package core;

import java.io.File;

public class UserInputs {

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

}
