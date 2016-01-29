/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core;

public class DisplayUtils {

  public static double getRoundedValueForDisplay(double value) {
    int valueFoix10 = (int) (value * 100);
    double roundedValue = ((double) valueFoix10) / 100;
    return roundedValue;
  }
}
