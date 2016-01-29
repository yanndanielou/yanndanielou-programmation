package hmi.views;

import hmi.widgets.DoubleValueLabel;

import java.awt.GridLayout;

import javax.swing.JLabel;

public class LoanOverviewView extends ProjetImmobilierBaseView {
  private static final long serialVersionUID = -3015965288824537079L;

  private static final LoanOverviewView INSTANCE = new LoanOverviewView();

  private JLabel montantTotalEmprunteLabel;
  private DoubleValueLabel montantTotalEmprunteValue;
  private JLabel montantTotalInteretsLabel;
  private DoubleValueLabel montantTotalInteretsValue;
  private JLabel montantTotalAssurancesLabel;
  private DoubleValueLabel montantTotalAssurancesValue;
  private JLabel fraisNotaireLabel;
  private DoubleValueLabel fraisNotaireValue;

  private LoanOverviewView() {
    loanViewsMediator.setLoanOverviewView(this);
    // setBackground(Color.BLUE);
  }

  public static LoanOverviewView getInstance() {
    return INSTANCE;
  }

  @Override
  protected void placeWidgets() {
    int columns = 2;
    int rows = 4;
    setLayout(new GridLayout(rows, columns));
    add(montantTotalEmprunteLabel);
    add(montantTotalEmprunteValue);
    add(montantTotalInteretsLabel);
    add(montantTotalInteretsValue);
    add(montantTotalAssurancesLabel);
    add(montantTotalAssurancesValue);
    add(fraisNotaireLabel);
    add(fraisNotaireValue);
  }

  @Override
  protected void createWidgets() {
    montantTotalEmprunteLabel = new JLabel("Montant total emprunté");
    montantTotalEmprunteValue = new DoubleValueLabel();
    montantTotalInteretsLabel = new JLabel("Montant total interets");
    montantTotalInteretsValue = new DoubleValueLabel();
    montantTotalAssurancesLabel = new JLabel("Montant total assurances");
    montantTotalAssurancesValue = new DoubleValueLabel();
    fraisNotaireLabel = new JLabel("Frais notaire");
    fraisNotaireValue = new DoubleValueLabel();
  }

  public void afterEmpruntCreated() {
    updateMontantTotalInteretsField();
  }

  public void afterEmpruntModified() {
    updateMontantTotalInteretsField();
    montantTotalEmprunteField();
  }

  public void afterFraisAgenceModified() {
    updateFraisNotaireField();
  }

  public void afterFraisNotaireModified() {
    updateFraisNotaireField();
  }

  public void afterPrixNetAcheteurModified() {
    updateFraisNotaireField();
  }

  private void updateMontantTotalInteretsField() {
    double montantTotalInterets = projetImmobilier.getMontantTotalInterets();
    montantTotalInteretsValue.setTextWithRoundedValue(montantTotalInterets);
  }

  private void updateFraisNotaireField() {
    double montantTotalInterets = projetImmobilier.getRealEstate().getFraisNotaire();
    fraisNotaireValue.setTextWithRoundedValue(montantTotalInterets);
  }

  private void montantTotalEmprunteField() {
    double capitalEmprunte = projetImmobilier.getCapitalEmprunte();
    montantTotalEmprunteValue.setTextWithRoundedValue(capitalEmprunte);
  }

}