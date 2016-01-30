package hmi.views;

import hmi.widgets.DoubleValueLabel;
import hmi.widgets.Label;
import hmi.widgets.MoneyTextField;
import hmi.widgets.NumberTextField;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import model.Emprunt;

public class EmpruntInitialPropertiesPanel extends ProjetImmobilierBaseView implements DocumentListener {

  private static final long serialVersionUID = -5637680529592116720L;

  protected Emprunt emprunt;

  private Label capitalEmprunteLabel;
  private MoneyTextField capitalEmprunteInput;
  private Label annualInterestRateLabel;
  private NumberTextField annualInterestRateInput;
  private Label mensualiteHorsAssuranceLabel;
  private MoneyTextField mensualiteHorsAssuranceInput;
  private Label nombreEcheancesLabel;
  private NumberTextField nombreEcheancesInput;
  private Label montantInteretsLabel;
  private DoubleValueLabel montantInteretsValue;
  private Label montantAssurancesLabel;
  private DoubleValueLabel montantAssurancesValue;

  public EmpruntInitialPropertiesPanel(Emprunt emprunt) {
    this.emprunt = emprunt;
    double capitalEmprunte = emprunt.getCapitalEmprunte();
    capitalEmprunteInput.setValue(capitalEmprunte);
    setBorder(BorderFactory.createLineBorder(Color.black));
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
    placeWidgets();
  }

  @Override
  protected void resizeWidgets() {
    setLabelSize(capitalEmprunteLabel);
    setTextFieldSize(capitalEmprunteInput, 8);

    setLabelSize(annualInterestRateLabel);
    setTextFieldSize(annualInterestRateInput, 7);

    setLabelSize(mensualiteHorsAssuranceLabel);
    setTextFieldSize(mensualiteHorsAssuranceInput, 5);

    setLabelSize(nombreEcheancesLabel);
    setTextFieldSize(nombreEcheancesInput, 3);

    setLabelSize(montantInteretsLabel);
    setTextFieldSize(montantInteretsValue, 5);

    setLabelSize(montantAssurancesLabel);
    setTextFieldSize(montantAssurancesValue, 5);

    int widerInput = getWiderComponentWidth(capitalEmprunteInput, annualInterestRateInput, mensualiteHorsAssuranceInput, nombreEcheancesInput);
    capitalEmprunteInput.setWidth(widerInput);
    annualInterestRateInput.setWidth(widerInput);
    mensualiteHorsAssuranceInput.setWidth(widerInput);
    nombreEcheancesInput.setWidth(widerInput);
  }

  @Override
  protected void createWidgets() {
    capitalEmprunteLabel = new Label("Capital emprunte");
    capitalEmprunteInput = new MoneyTextField(NumberFormat.getNumberInstance());
    capitalEmprunteInput.getDocument().addDocumentListener(this);
    annualInterestRateLabel = new Label("Taux intérêt annuel");
    NumberFormat percentInstance = NumberFormat.getPercentInstance();
    percentInstance.setMaximumFractionDigits(3);
    annualInterestRateInput = new NumberTextField(percentInstance);
    annualInterestRateInput.getDocument().addDocumentListener(this);
    mensualiteHorsAssuranceLabel = new Label("Mensualité");
    mensualiteHorsAssuranceInput = new MoneyTextField(NumberFormat.getNumberInstance());
    mensualiteHorsAssuranceInput.getDocument().addDocumentListener(this);
    nombreEcheancesLabel = new Label("Nombre Echeances");
    nombreEcheancesInput = new NumberTextField(NumberFormat.getIntegerInstance());
    nombreEcheancesInput.getDocument().addDocumentListener(this);
    montantInteretsLabel = new Label("Montant total interets");
    montantInteretsValue = new DoubleValueLabel();
    montantAssurancesLabel = new Label("Montant total assurances");
    montantAssurancesValue = new DoubleValueLabel();
  }

  @Override
  protected void addWidgets() {
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

  protected void placeWidgets() {
    int widerLabel = getWiderComponentWidth(capitalEmprunteLabel, annualInterestRateLabel, mensualiteHorsAssuranceLabel, nombreEcheancesLabel, montantInteretsLabel, montantAssurancesLabel);

    capitalEmprunteLabel.setLocation(horizontal_margin_from_component_and_first_widgets, vertical_margin_from_component_and_first_widgets);
    capitalEmprunteInput.setLocation(horizontal_margin_from_component_and_first_widgets + widerLabel + marginBetweenLabelAndValue, capitalEmprunteLabel.getY());

    annualInterestRateLabel.setLocation(horizontal_margin_from_component_and_first_widgets, capitalEmprunteInput.getBottom() + vertial_margin_beween_widgets);
    annualInterestRateInput.setLocation(horizontal_margin_from_component_and_first_widgets + widerLabel + marginBetweenLabelAndValue, annualInterestRateLabel.getY());

    mensualiteHorsAssuranceLabel.setLocation(horizontal_margin_from_component_and_first_widgets, annualInterestRateInput.getBottom() + vertial_margin_beween_widgets);
    mensualiteHorsAssuranceInput.setLocation(horizontal_margin_from_component_and_first_widgets + widerLabel + marginBetweenLabelAndValue, mensualiteHorsAssuranceLabel.getY());

    nombreEcheancesLabel.setLocation(horizontal_margin_from_component_and_first_widgets, mensualiteHorsAssuranceInput.getBottom() + vertial_margin_beween_widgets);
    nombreEcheancesInput.setLocation(horizontal_margin_from_component_and_first_widgets + widerLabel + marginBetweenLabelAndValue, nombreEcheancesLabel.getY());

    montantInteretsLabel.setLocation(horizontal_margin_from_component_and_first_widgets, nombreEcheancesInput.getBottom() + vertial_margin_beween_widgets);
    montantInteretsValue.setLocation(horizontal_margin_from_component_and_first_widgets + widerLabel + marginBetweenLabelAndValue, montantInteretsLabel.getY());

    montantAssurancesLabel.setLocation(horizontal_margin_from_component_and_first_widgets, montantInteretsValue.getBottom() + vertial_margin_beween_widgets);
    montantAssurancesValue.setLocation(horizontal_margin_from_component_and_first_widgets + widerLabel + marginBetweenLabelAndValue, montantAssurancesLabel.getY());
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

  @Override
  public void componentResized(ComponentEvent event) {
    if (event.getComponent() == this) {
      replaceWidgets();
    }
  }

  @Override
  public Integer getRequiredHeight() {
    return montantAssurancesValue.getBottom() + horizontal_margin_from_component_and_last_widgets;
  }

  @Override
  public Integer getRequiredWidth() {
    return getRightOfComponentWithBiggestRight();
  }
}
