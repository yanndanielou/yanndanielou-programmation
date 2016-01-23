package hmi.widgets;

import static Core.StringUtils.*;

import java.text.NumberFormat;

public class NumberTextField extends TextField {

  private static final long serialVersionUID = 685178584720700695L;

  public NumberTextField(NumberFormat integerInstance) {
    super(integerInstance);
  }

  public Integer getValueAsInt() {
    Object valueAsObject = getValue();
    if (valueAsObject == null) {
      return null;
    } else if (valueAsObject instanceof Integer) {
      Integer valueAsInteger = (Integer) valueAsObject;
      return valueAsInteger;
    } else if (getValueAsLong() != null) {
      long valueAsLong = getValueAsLong();
      return (int) valueAsLong;
    }
    return null;
  }

  public Long getValueAsLong() {
    Object valueAsObject = getValue();
    if (valueAsObject == null) {
      return null;
    } else if (valueAsObject instanceof Long) {
      Long valueAsLong = (Long) valueAsObject;
      return valueAsLong;
    }
    return null;
  }

  public Double getValueAsDouble() {
    Object valueAsObject = getValue();
    if (valueAsObject == null) {
      return null;
    } else if (valueAsObject instanceof Double) {
      Double valueAsDouble = (Double) valueAsObject;
      return valueAsDouble;
    } else if (valueAsObject instanceof Long) {
      Long valueAsLong = (Long) valueAsObject;
      return Double.valueOf(valueAsLong);
    }
    return (Double) valueAsObject;
  }

  public int getTextAsInt() {
    String text = getText();
    if (isTextValidForInt(text)) {
      return Integer.parseInt(text);
    } else {
      Integer valueAsInt = getValueAsInt();
      if (valueAsInt != null) {
        return valueAsInt;
      }
    }
    return 0;
  }

  public double getTextAsDouble() {
    String text = getText();
    if (isTextValidForDouble(text)) {
      return Double.parseDouble(text);
    } else {
      Double valueAsDouble = getValueAsDouble();
      if (valueAsDouble != null) {
        return valueAsDouble;
      }
    }
    return 0;
  }
}
