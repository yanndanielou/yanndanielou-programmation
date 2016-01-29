package hmi.views;

import hmi.LoanViewsMediator;
import hmi.widgets.DoubleValueLabel;
import hmi.widgets.MoneyTextField;
import hmi.widgets.NumberTextField;

import java.awt.Color;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import model.Emprunt;

public class EmpruntInitialPropertiesPanel extends JPanel implements DocumentListener {

  private static final long serialVersionUID = -5637680529592116720L;

  protected LoanViewsMediator loanViewsMediator;

  protected Emprunt emprunt;

  private JLabel capitalEmprunteLabel;
  private MoneyTextField capitalEmprunteInput;
  private JLabel annualInterestRateLabel;
  private NumberTextField annualInterestRateInput;
  private JLabel mensualiteHorsAssuranceLabel;
  private MoneyTextField mensualiteHorsAssuranceInput;
  private JLabel nombreEcheancesLabel;
  private NumberTextField nombreEcheancesInput;
  private JLabel montantInteretsLabel;
  private DoubleValueLabel montantInteretsValue;
  private JLabel montantAssurancesLabel;
  private DoubleValueLabel montantAssurancesValue;

  public EmpruntInitialPropertiesPanel(Emprunt emprunt) {
    this.emprunt = emprunt;
    init();
  }

  private void init() {
    loanViewsMediator = LoanViewsMediator.getInstance();
    createWidgets();
    placeWidgets();
    double capitalEmprunte = emprunt.getCapitalEmprunte();
    capitalEmprunteInput.setValue(capitalEmprunte);
    setBorder(BorderFactory.createLineBorder(Color.black));
  }

  protected void createWidgets() {
    capitalEmprunteLabel = new JLabel("Capital emprunte");
    capitalEmprunteInput = new MoneyTextField(NumberFormat.getNumberInstance());
    capitalEmprunteInput.getDocument().addDocumentListener(this);
    annualInterestRateLabel = new JLabel("Taux intérêt annuel");
    NumberFormat percentInstance = NumberFormat.getPercentInstance();
    percentInstance.setMaximumFractionDigits(3);
    annualInterestRateInput = new NumberTextField(percentInstance);
    annualInterestRateInput.getDocument().addDocumentListener(this);
    mensualiteHorsAssuranceLabel = new JLabel("Mensualité");
    mensualiteHorsAssuranceInput = new MoneyTextField(NumberFormat.getNumberInstance());
    mensualiteHorsAssuranceInput.getDocument().addDocumentListener(this);
    nombreEcheancesLabel = new JLabel("Nombre Echeances");
    nombreEcheancesInput = new NumberTextField(NumberFormat.getIntegerInstance());
    nombreEcheancesInput.getDocument().addDocumentListener(this);
    montantInteretsLabel = new JLabel("Montant total interets");
    montantInteretsValue = new DoubleValueLabel();
    montantAssurancesLabel = new JLabel("Montant total assurances");
    montantAssurancesValue = new DoubleValueLabel();
  }

  protected void placeWidgets() {
    int columns = 2;
    int rows = 6;
    setLayout(new GridLayout(rows, columns));
    add(capitalEmprunteLabel);
    add(capitalEmprunteInput);
    add(annualInterestRateLabel);
    add(annualInterestRateInput);
    add(mensualiteHorsAssuranceLabel);
    add(mensualiteHorsAssuranceInput);
    add(nombreEcheancesLabel);
    add(nombreEcheancesInput);
    add(montantInteretsLabel);
    add(montantInteretsValue);
    add(montantAssurancesLabel);
    add(montantAssurancesValue);
  }

  private void onCapitalEmprunteModified(double capitalEmprunte) {
    loanViewsMediator.onCapitalEmprunteModified(emprunt, capitalEmprunte);
  }

  private void onAnnualInterestRateModified(double annualInterestRate) {
    loanViewsMediator.onAnnualInterestRateModified(emprunt, annualInterestRate);
  }

  private void onmensualiteHorsAssuranceModified(double mensualiteHorsAssurance) {
    loanViewsMediator.onMensualiteHorsAssuranceModified(emprunt, mensualiteHorsAssurance);
  }

  private void onNombreEcheancesDesireModified(int nombreEcheances) {
    loanViewsMediator.onNombreEcheancesDesireModified(emprunt, nombreEcheances);
  }

  @Override
  public void changedUpdate(DocumentEvent event) {
    onTextFieldChanged(event);
  }

  @Override
  public void insertUpdate(DocumentEvent event) {
    onTextFieldChanged(event);
  }

  @Override
  public void removeUpdate(DocumentEvent event) {
    onTextFieldChanged(event);
  }

  private void onTextFieldChanged(DocumentEvent event) {
    JFormattedTextField textField = getTextFieldFromEvent(event);
    if (textField == capitalEmprunteInput) {
      double capitalEmprunte = capitalEmprunteInput.getTextAsDouble();
      onCapitalEmprunteModified(capitalEmprunte);
    } else if (textField == annualInterestRateInput) {
      double annualInterestRate = annualInterestRateInput.getTextAsDouble();
      onAnnualInterestRateModified(annualInterestRate);
    } else if (textField == mensualiteHorsAssuranceInput) {
      double mensualiteHorsAssurance = mensualiteHorsAssuranceInput.getTextAsDouble();
      onmensualiteHorsAssuranceModified(mensualiteHorsAssurance);
    } else if (textField == nombreEcheancesInput) {
      int nombreEcheances = nombreEcheancesInput.getTextAsInt();
      onNombreEcheancesDesireModified(nombreEcheances);
    }
  }

  private JFormattedTextField getTextFieldFromEvent(DocumentEvent event) {
    Document eventDocument = event.getDocument();
    if (eventDocument == capitalEmprunteInput.getDocument()) {
      return capitalEmprunteInput;
    } else if (eventDocument == annualInterestRateInput.getDocument()) {
      return annualInterestRateInput;
    } else if (eventDocument == mensualiteHorsAssuranceInput.getDocument()) {
      return mensualiteHorsAssuranceInput;
    } else if (eventDocument == nombreEcheancesInput.getDocument()) {
      return nombreEcheancesInput;
    }
    return null;
  }

  public Emprunt getEmprunt() {
    return emprunt;
  }

  public void refreshMontantInterets() {
    double montantTotalInterets = emprunt.getMontantTotalInterets();
    montantInteretsValue.setTextWithRoundedValue(montantTotalInterets);
  }

  public void refreshMontantAssurance() {
    double montantTotalAssurance = emprunt.getMontantTotalAssurance();
    montantAssurancesValue.setTextWithRoundedValue(montantTotalAssurance);
  }

  public void refresh() {
    if (!emprunt.isMensualiteHorsAssuranceFilled() && emprunt.getMensualiteHorsAssurance() != null) {
      mensualiteHorsAssuranceInput.changeValueWithoutCallingObservers(emprunt.getMensualiteHorsAssurance(), this);
    }
    if (!emprunt.isNombreEcheancesFilled() && emprunt.getActualNombreEcheances() > 0) {
      nombreEcheancesInput.changeValueWithoutCallingObservers(emprunt.getActualNombreEcheances(), this);
    }
    refreshMontantInterets();
    refreshMontantAssurance();
  }
}
