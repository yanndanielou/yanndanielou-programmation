package common;

public class Logger {

  private static Severity configuredLevel = Severity.INFO;

  public static void fullDebug(String trace) {
    log(Severity.DEBUG, trace);
  }

  public static void debug(String trace) {
    log(Severity.DEBUG, trace);
  }

  public static void note(String trace) {
    log(Severity.NOTE, trace);
  }

  public static void info(String trace) {
    log(Severity.INFO, trace);
  }

  public static void warning(String trace) {
    log(Severity.WARNING, trace);
  }

  public static void error(String trace) {
    log(Severity.ERROR, trace);
  }

  public static void fatal(String trace) {
    log(Severity.FATAL, trace + ". Application is shut down");
    Runtime.getRuntime().exit(0);
  }

  public static void log(Severity level, String trace) {
    if (configuredLevel.include(level)) {
      System.out.println(level + ";" + trace);
    }
  }

  public static void configureLevel(Severity configuredLevel) {
    Logger.configuredLevel = configuredLevel;
  }

}
