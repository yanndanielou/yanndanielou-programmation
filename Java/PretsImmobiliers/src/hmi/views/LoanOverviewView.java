package hmi.views;

import java.awt.GridLayout;

import javax.swing.JLabel;

public class LoanOverviewView extends ProjetImmobilierBaseView {
  private static final long serialVersionUID = -3015965288824537079L;

  private static final LoanOverviewView INSTANCE = new LoanOverviewView();

  private JLabel montantTotalInteretsLabel;
  private JLabel montantTotalInteretsValue;

  private LoanOverviewView() {
    loanViewsMediator.setLoanOverviewView(this);
  }

  public static LoanOverviewView getInstance() {
    return INSTANCE;
  }

  @Override
  protected void placeWidgets() {
    int columns = 2;
    int rows = 1;
    setLayout(new GridLayout(rows, columns));
    add(montantTotalInteretsLabel);
    add(montantTotalInteretsValue);
  }

  @Override
  protected void createWidgets() {
    montantTotalInteretsLabel = new JLabel("Montant total interets");
    montantTotalInteretsValue = new JLabel();
  }

  public void afterEmpruntCreated() {
    updateMontantTotalInteretsField();
  }

  public void afterEmpruntModified() {
    updateMontantTotalInteretsField();
  }

  private void updateMontantTotalInteretsField() {
    String text = String.valueOf(projetImmobilier.getMontantTotalInterets());
    montantTotalInteretsValue.setText(text);
  }
}
