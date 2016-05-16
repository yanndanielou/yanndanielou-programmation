package ihm.util;

import java.awt.Component;
import java.awt.Container;

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

  public static int getXSoComponentIsAtHorizontalCenterOfContainer(Component component, Container container) {
    int x = (container.getWidth() - component.getWidth()) / 2;
    return x;
  }
}
