package hmi.views;

import hmi.LoanViewsMediator;
import hmi.widgets.TextField;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ProjetImmobilier;

public abstract class ProjetImmobilierBaseView extends JPanel {
  private static final long serialVersionUID = -5601747344003397604L;

  public static final int marginBetweenLabelAndValue = 5;
  public static final int horizontal_margin_from_component_and_first_widgets = 5;
  public static final int vertical_margin_from_component_and_first_widgets = 5;
  public static final int widget_height = 20;

  protected LoanViewsMediator loanViewsMediator;
  protected ProjetImmobilier projetImmobilier;

  protected ProjetImmobilierBaseView() {
    init();
  }

  private void init() {
    projetImmobilier = ProjetImmobilier.getInstance();
    loanViewsMediator = LoanViewsMediator.getInstance();
    createWidgets();
    placeWidgetsAtInit();
  }

  protected void setTextFieldSize(TextField textField, int numberOfDigitsMaxValue) {
    textField.setSize(numberOfDigitsMaxValue * 10, widget_height);
  }

  protected void setLabelSize(JLabel label) {
    label.setSize((int) label.getPreferredSize().getWidth(), widget_height);
  }

  protected int getTotalComponentWidth() {
    int totalWidth = 0;
    for (Component component : getComponents()) {
      totalWidth += component.getWidth();
    }
    return totalWidth;
  }

  protected abstract void createWidgets();

  protected abstract void placeWidgetsAtInit();

  public void printLocationAndSize() {
    String viewType = getClass().getSimpleName();
    System.out.println(viewType + ": x=" + getX() + ", y=" + getY() + ", width:" + getWidth() + ", height:" + getHeight());
  }

  public Integer getRequiredHeight() {
    return null;
  }
}
