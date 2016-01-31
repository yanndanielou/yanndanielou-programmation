package hmi.views;

import hmi.LoanViewsMediator;
import hmi.util.WidgetUtil;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ProjetImmobilier;

public abstract class ProjetImmobilierBaseView extends JPanel implements ComponentListener {
  private static final long serialVersionUID = -5601747344003397604L;

  public static final int marginBetweenLabelAndValue = 5;
  public static final int vertial_margin_beween_widgets = 5;
  public static final int horizontal_margin_from_component_and_first_widgets = 5;
  public static final int horizontal_margin_from_component_and_last_widgets = horizontal_margin_from_component_and_first_widgets;
  public static final int vertical_margin_from_component_and_first_widgets = 5;
  public static final int vertical_margin_from_component_and_last_widgets = vertical_margin_from_component_and_first_widgets;
  public static final int widget_height = 20;

  protected LoanViewsMediator loanViewsMediator;
  protected ProjetImmobilier projetImmobilier;

  protected ProjetImmobilierBaseView() {
    init();
    addComponentListener(this);
  }

  protected void init() {
    projetImmobilier = ProjetImmobilier.getInstance();
    loanViewsMediator = LoanViewsMediator.getInstance();
    createWidgets();
    placeWidgetsAtInit();
  }

  protected void setTextFieldSize(Component component, int numberOfDigitsMaxValue) {
    component.setSize(numberOfDigitsMaxValue * 10, widget_height);
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

  protected int getRightOfComponentWithBiggestRight() {
    return getRightOfComponentWithBiggestRight(getComponents());
  }

  protected int getBottomOfTheMostBottomComponent() {
    return getBottomOfTheMostBottomComponent(getComponents());
  }

  protected int getBottomOfTheMostBottomComponent(Component... components) {
    int biggestBottom = 0;
    for (Component component : components) {
      biggestBottom = Math.max(biggestBottom, WidgetUtil.getWidgetBottom(component));
    }
    return biggestBottom;
  }

  protected int getRightOfComponentWithBiggestRight(Component... components) {
    int biggestRight = 0;
    for (Component component : components) {
      biggestRight = Math.max(biggestRight, WidgetUtil.getWidgetRight(component));
    }
    return biggestRight;
  }

  protected int getWiderComponentWidth() {
    return getWiderComponentWidth(getComponents());
  }

  protected int getWiderComponentWidth(Component... components) {
    int biggestWidth = 0;
    for (Component component : components) {
      biggestWidth = Math.max(biggestWidth, component.getWidth());
    }
    return biggestWidth;
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

  public Integer getRequiredWidth() {
    return null;
  }

  protected void addWidgets() {
  }

  protected void replaceWidgets() {
  }

  protected void resizeWidgets() {
  }

  @Override
  public void componentMoved(ComponentEvent e) {
  }

  @Override
  public void componentShown(ComponentEvent e) {
  }

  @Override
  public void componentHidden(ComponentEvent e) {
  }
}
