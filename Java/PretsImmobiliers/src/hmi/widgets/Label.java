package hmi.widgets;

import hmi.util.WidgetUtil;

import javax.swing.JLabel;

public class Label extends JLabel {

  private static final long serialVersionUID = 4872586718976446293L;

  public Label() {
  }

  public Label(String text) {
    super(text);
  }

  public int getRight() {
    return WidgetUtil.getWidgetRight(this);
  }

  public int getBottom() {
    return WidgetUtil.getWidgetBottom(this);
  }

  public void setWidth(int width) {
    WidgetUtil.setWidgetWidth(this, width);
  }
}
