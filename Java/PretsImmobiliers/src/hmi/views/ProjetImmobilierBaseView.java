package hmi.views;

import hmi.LoanViewsMediator;

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

  public void printLocationAndSize() {
    String viewType = getClass().getSimpleName();
    System.out.println(viewType + ": x=" + getX() + ", y=" + getY() + ", width:" + getWidth() + ", height:" + getHeight());
  }

  public Integer getRequiredHeight() {
    return null;
  }
}
