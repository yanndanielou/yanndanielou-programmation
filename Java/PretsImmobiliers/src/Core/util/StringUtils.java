package Core.util;

public class StringUtils {

  public static boolean isTextValidForInt(String text) {
    try {
      Integer.parseInt(text);
      return true;
    } catch (NumberFormatException numberFormatException) {
      return false;
    }
  }

  public static boolean isTextValidForDouble(String text) {
    try {
      Double.parseDouble(text);
      return true;
    } catch (NumberFormatException numberFormatException) {
      return false;
    }
  }
}
