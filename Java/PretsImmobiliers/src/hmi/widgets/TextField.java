package hmi.widgets;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentListener;

public class TextField extends JFormattedTextField {

  private static final long serialVersionUID = 685178584720700695L;

  public TextField(NumberFormat integerInstance) {
    super(integerInstance);
  }

  public void changeValueWithoutCallingObservers(Object value, DocumentListener listener) {
    getDocument().removeDocumentListener(listener);
    setValue(value);
    getDocument().addDocumentListener(listener);
  }
}
