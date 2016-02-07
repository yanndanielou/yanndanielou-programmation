package hmi.views;

import hmi.widgets.DoubleValueLabel;

import java.awt.event.ComponentEvent;

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
  protected void placeWidgetsAtInit() {
    setLayout(null);
    addWidgets();
    replaceWidgets();
  }

  @Override
  protected void replaceWidgets() {
    resizeWidgets();
    placeWidgetsWithoutLayout();
  }

  @Override
  protected void addWidgets() {
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
  protected void resizeWidgets() {
    setLabelSize(montantTotalEmprunteLabel);
    setLabelSize(montantTotalEmprunteValue);

    setLabelSize(montantTotalInteretsLabel);
    setLabelSize(montantTotalInteretsValue);

    setLabelSize(montantTotalAssurancesLabel);
    setLabelSize(montantTotalAssurancesValue);

    setLabelSize(fraisNotaireLabel);
    setLabelSize(fraisNotaireValue);
  }

  private void placeWidgetsWithoutLayout() {
    int nombreElements = getComponentCount();
    int nombreChampsARemplir = nombreElements / 2;
    int margeRestante = getWidth() - 2 * horizontal_margin_from_component_and_first_widgets - nombreChampsARemplir * marginBetweenLabelAndValue - getTotalComponentWidth();
    int margeEntreInputs = margeRestante / nombreChampsARemplir;

    montantTotalEmprunteLabel.setLocation(horizontal_margin_from_component_and_first_widgets, vertical_margin_from_component_and_first_widgets);
    montantTotalEmprunteValue.setLocation(montantTotalEmprunteLabel.getX() + montantTotalEmprunteLabel.getWidth() + marginBetweenLabelAndValue, vertical_margin_from_component_and_first_widgets);

    montantTotalInteretsLabel.setLocation(montantTotalEmprunteValue.getX() + montantTotalEmprunteValue.getWidth() + margeEntreInputs, vertical_margin_from_component_and_first_widgets);
    montantTotalInteretsValue.setLocation(montantTotalInteretsLabel.getX() + montantTotalInteretsLabel.getWidth() + marginBetweenLabelAndValue, vertical_margin_from_component_and_first_widgets);

    montantTotalAssurancesLabel.setLocation(montantTotalInteretsValue.getX() + montantTotalInteretsValue.getWidth() + margeEntreInputs, vertical_margin_from_component_and_first_widgets);
    montantTotalAssurancesValue.setLocation(montantTotalAssurancesLabel.getX() + montantTotalAssurancesLabel.getWidth() + marginBetweenLabelAndValue, vertical_margin_from_component_and_first_widgets);

    fraisNotaireLabel.setLocation(montantTotalAssurancesValue.getX() + montantTotalAssurancesValue.getWidth() + margeEntreInputs, vertical_margin_from_component_and_first_widgets);
    fraisNotaireValue.setLocation(fraisNotaireLabel.getX() + fraisNotaireLabel.getWidth() + marginBetweenLabelAndValue, vertical_margin_from_component_and_first_widgets);
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

  public void afterEmpruntDeleted() {
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

  public void afterAssurancesMensuellesModified() {
    updateMontantTotalAssurancesField();
  }

  private void updateMontantTotalInteretsField() {
    double montantTotalInterets = projetImmobilier.getMontantTotalInterets();
    montantTotalInteretsValue.setTextWithRoundedValue(montantTotalInterets);
    replaceWidgets();
  }

  private void updateMontantTotalAssurancesField() {
    double montantTotalInterets = projetImmobilier.getMontantTotalAssurances();
    montantTotalAssurancesValue.setTextWithRoundedValue(montantTotalInterets);
    replaceWidgets();
  }

  private void updateFraisNotaireField() {
    double montantTotalInterets = projetImmobilier.getRealEstate().getFraisNotaire();
    fraisNotaireValue.setTextWithRoundedValue(montantTotalInterets);
    replaceWidgets();
  }

  private void montantTotalEmprunteField() {
    double capitalEmprunte = projetImmobilier.getCapitalEmprunte();
    montantTotalEmprunteValue.setTextWithRoundedValue(capitalEmprunte);
    replaceWidgets();
  }

  @Override
  public void componentResized(ComponentEvent event) {
    if (event.getComponent() == this) {
      replaceWidgets();
    }
  }

}