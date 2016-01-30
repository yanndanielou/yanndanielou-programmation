package hmi.widgets;

import javax.swing.JLabel;

public class Label extends JLabel {

  private static final long serialVersionUID = 4872586718976446293L;

  public Label() {
  }

  public Label(String text) {
    super(text);
  }

  public int getRight() {
    return getX() + getWidth();
  }

  public int getBottom() {
    return getY() + getHeight();
  }
}
