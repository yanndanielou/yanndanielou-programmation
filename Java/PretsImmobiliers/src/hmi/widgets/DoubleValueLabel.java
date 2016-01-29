package hmi.widgets;

import javax.swing.JLabel;

import Core.DisplayUtils;

public class DoubleValueLabel extends JLabel {

  private static final long serialVersionUID = 6667073742788924547L;

  public void setTextWithRoundedValue(double valueToRound) {
    double roundedValueForDisplay = DisplayUtils.getRoundedValueForDisplay(valueToRound);
    String text = String.valueOf(roundedValueForDisplay);
    setText(text);
  }

}
