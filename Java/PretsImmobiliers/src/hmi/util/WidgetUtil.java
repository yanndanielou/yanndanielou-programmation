/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.util;

import java.awt.Component;

public class WidgetUtil {

  public static int getWidgetRight(Component component) {
    return component.getX() + component.getWidth();
  }

  public static int getWidgetBottom(Component component) {
    return component.getY() + component.getHeight();
  }

  public static void setWidgetWidth(Component component, int width) {
    component.setSize(width, component.getHeight());
  }
}
