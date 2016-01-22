package hmi.views;

import hmi.LoanViewsMediator;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import model.ProjetImmobilier;

public abstract class ProjetImmobilierBaseView extends JPanel {
  private static final long serialVersionUID = -5601747344003397604L;

  protected LoanViewsMediator loanViewsMediator;
  protected ProjetImmobilier projetImmobilier;

  protected ProjetImmobilierBaseView() {
    init();
  }

  private void init() {
    projetImmobilier = ProjetImmobilier.getInstance();
    loanViewsMediator = LoanViewsMediator.getInstance();
    createWidgets();
    placeWidgets();
  }

  protected abstract void createWidgets();

  protected abstract void placeWidgets();

  protected Long getValueAsLong(JFormattedTextField formattedTextField) {
    Object valueAsObject = formattedTextField.getValue();
    if (valueAsObject == null) {
      return null;
    } else if (valueAsObject instanceof Long) {
      Long valueAsLong = (Long) valueAsObject;
      return valueAsLong;
    }
    return (Long) valueAsObject;
  }

  protected Double getValueAsDouble(JFormattedTextField formattedTextField) {
    Object valueAsObject = formattedTextField.getValue();
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

  private boolean isTextValidForInt(String text) {
    try {
      Integer.parseInt(text);
      return true;
    } catch (NumberFormatException numberFormatException) {
      return false;
    }
  }

  protected int getTextAsInt(JFormattedTextField formattedTextField) {
    String text = formattedTextField.getText();
    if (isTextValidForInt(text)) {
      return Integer.parseInt(text);
    } else {
      Long valueAsLong = getValueAsLong(formattedTextField);
      if (valueAsLong != null) {
        return (int) (long) valueAsLong;
      }
    }
    return 0;
  }
}
