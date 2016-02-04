package hmi.widgets;

import Core.util.DisplayUtils;

public class DoubleValueLabel extends Label {

  private static final long serialVersionUID = 6667073742788924547L;

  public DoubleValueLabel() {
  }

  public DoubleValueLabel(String text) {
    super(text);
  }

  public void setTextWithRoundedValue(double valueToRound) {
    double roundedValueForDisplay = DisplayUtils.getRoundedValueForDisplay(valueToRound);
    String text = String.valueOf(roundedValueForDisplay);
    setText(text);
  }

}
