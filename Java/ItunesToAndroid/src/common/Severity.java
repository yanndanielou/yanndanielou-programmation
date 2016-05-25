package common;

public enum Severity {
  FULL_DEBUG,
  DEBUG,
  NOTE,
  INFO,
  WARNING,
  ERROR,
  FATAL;

  public boolean include(Severity isIncluded) {
    switch (isIncluded) {
    case FULL_DEBUG:
      return includeFullDebug();
    case DEBUG:
      return includeDebug();
    case NOTE:
      return includeNote();
    case INFO:
      return includeInfo();
    case WARNING:
      return includeWarning();
    case ERROR:
      return includeError();
    case FATAL:
      return includeFatal();
    default:
      return true;
    }
  }

  private boolean includeFullDebug() {
    return this == FULL_DEBUG;
  }

  private boolean includeDebug() {
    return this == DEBUG || includeFullDebug();
  }

  private boolean includeNote() {
    return this == Severity.NOTE || includeDebug();
  }

  private boolean includeInfo() {
    return this == INFO || includeNote();
  }

  private boolean includeWarning() {
    return this == WARNING || includeInfo();
  }

  private boolean includeError() {
    return this == ERROR || includeWarning();
  }

  private boolean includeFatal() {
    return this == FATAL || includeError();
  }
}
//common.all.enums.enum_