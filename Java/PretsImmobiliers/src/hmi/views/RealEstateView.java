package hmi.views;

import hmi.widgets.MoneyTextField;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class RealEstateView extends ProjetImmobilierBaseView implements DocumentListener {

  private static final long serialVersionUID = 863212423235558907L;

  private static final RealEstateView INSTANCE = new RealEstateView();

  private JLabel prixNetAcheteurLabel;
  private MoneyTextField prixNetAcheteurInput;
  private JLabel fraisAgenceLabel;
  private MoneyTextField fraisAgenceInput;
  private JLabel apportPersonnelLabel;
  private MoneyTextField apportPersonnelInput;
  private JLabel fraisNotaireLabel;
  private MoneyTextField fraisNotaireInput;

  private RealEstateView() {
    loanViewsMediator.setRealEstateView(this);
    setBackground(Color.PINK);
  }

  public static RealEstateView getInstance() {
    return INSTANCE;
  }

  @Override
  protected void createWidgets() {
    prixNetAcheteurLabel = new JLabel("Prix net acheteur");
    prixNetAcheteurInput = new MoneyTextField(NumberFormat.getNumberInstance());
    prixNetAcheteurInput.getDocument().addDocumentListener(this);
    fraisAgenceLabel = new JLabel("Frais agence");
    fraisAgenceInput = new MoneyTextField(NumberFormat.getNumberInstance());
    fraisAgenceInput.getDocument().addDocumentListener(this);
    apportPersonnelLabel = new JLabel("Apport personnel");
    apportPersonnelInput = new MoneyTextField(NumberFormat.getNumberInstance());
    apportPersonnelInput.getDocument().addDocumentListener(this);
    fraisNotaireLabel = new JLabel("Frais notaire");
    fraisNotaireInput = new MoneyTextField(NumberFormat.getNumberInstance());
    fraisNotaireInput.getDocument().addDocumentListener(this);
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
    add(prixNetAcheteurLabel);
    add(prixNetAcheteurInput);
    add(fraisAgenceLabel);
    add(fraisAgenceInput);
    add(apportPersonnelLabel);
    add(apportPersonnelInput);
    add(fraisNotaireLabel);
    add(fraisNotaireInput);
  }

  @Override
  protected void resizeWidgets() {
    setLabelSize(prixNetAcheteurLabel);
    setTextFieldSize(prixNetAcheteurInput, 8);

    setLabelSize(fraisAgenceLabel);
    setTextFieldSize(fraisAgenceInput, 5);

    setLabelSize(apportPersonnelLabel);
    setTextFieldSize(apportPersonnelInput, 6);

    setLabelSize(fraisNotaireLabel);
    setTextFieldSize(fraisNotaireInput, 5);
  }

  @Override
  public Integer getRequiredHeight() {
    return vertical_margin_from_component_and_first_widgets * 2 + widget_height;
  }

  private void placeWidgetsWithoutLayout() {
    int nombreElements = getComponentCount();
    int nombreChampsARemplir = nombreElements / 2;
    int margeRestante = getWidth() - 2 * horizontal_margin_from_component_and_first_widgets - nombreChampsARemplir * marginBetweenLabelAndValue - getTotalComponentWidth();
    int margeEntreInputs = margeRestante / nombreChampsARemplir;

    prixNetAcheteurLabel.setLocation(horizontal_margin_from_component_and_first_widgets, vertical_margin_from_component_and_first_widgets);
    prixNetAcheteurInput.setLocation(prixNetAcheteurLabel.getX() + prixNetAcheteurLabel.getWidth() + marginBetweenLabelAndValue, vertical_margin_from_component_and_first_widgets);

    fraisAgenceLabel.setLocation(prixNetAcheteurInput.getX() + prixNetAcheteurInput.getWidth() + margeEntreInputs, vertical_margin_from_component_and_first_widgets);
    fraisAgenceInput.setLocation(fraisAgenceLabel.getX() + fraisAgenceLabel.getWidth() + marginBetweenLabelAndValue, vertical_margin_from_component_and_first_widgets);

    apportPersonnelLabel.setLocation(fraisAgenceInput.getX() + fraisAgenceInput.getWidth() + margeEntreInputs, vertical_margin_from_component_and_first_widgets);
    apportPersonnelInput.setLocation(apportPersonnelLabel.getX() + apportPersonnelLabel.getWidth() + marginBetweenLabelAndValue, vertical_margin_from_component_and_first_widgets);

    fraisNotaireLabel.setLocation(apportPersonnelInput.getX() + apportPersonnelInput.getWidth() + margeEntreInputs, vertical_margin_from_component_and_first_widgets);
    fraisNotaireInput.setLocation(fraisNotaireLabel.getX() + fraisNotaireLabel.getWidth() + marginBetweenLabelAndValue, vertical_margin_from_component_and_first_widgets);
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

  private void onFraisAgenceModified(int fraisAgence) {
    loanViewsMediator.onFraisAgenceModified(fraisAgence);
  }

  private void onApportPersonnelModified(int apportPersonnel) {
    loanViewsMediator.onApportPersonnelModified(apportPersonnel);
  }

  private void onPrixNetAcheteurModified(int prixNetAcheteur) {
    loanViewsMediator.onPrixNetAcheteurModified(prixNetAcheteur);
  }

  private void onFraisNotaireModified(int fraisNotaire) {
    loanViewsMediator.onFraisNotaireModified(fraisNotaire);
  }

  private void onTextFieldChanged(DocumentEvent event) {
    JFormattedTextField textField = getTextFieldFromEvent(event);
    if (textField == prixNetAcheteurInput) {
      int prixNetAcheteur = prixNetAcheteurInput.getTextAsInt();
      onPrixNetAcheteurModified(prixNetAcheteur);
    } else if (textField == apportPersonnelInput) {
      int apportPersonnel = apportPersonnelInput.getTextAsInt();
      onApportPersonnelModified(apportPersonnel);
    } else if (textField == fraisAgenceInput) {
      int fraisAgence = fraisAgenceInput.getTextAsInt();
      onFraisAgenceModified(fraisAgence);
    } else if (textField == fraisNotaireInput) {
      int fraisNotaire = fraisNotaireInput.getTextAsInt();
      onFraisNotaireModified(fraisNotaire);
    }
  }

  private JFormattedTextField getTextFieldFromEvent(DocumentEvent event) {
    Document eventDocument = event.getDocument();
    if (eventDocument == prixNetAcheteurInput.getDocument()) {
      return prixNetAcheteurInput;
    } else if (eventDocument == apportPersonnelInput.getDocument()) {
      return apportPersonnelInput;
    } else if (eventDocument == fraisAgenceInput.getDocument()) {
      return fraisAgenceInput;
    } else if (eventDocument == fraisNotaireInput.getDocument()) {
      return fraisNotaireInput;
    }
    return null;
  }

  public void afterPrixNetAcheteurModified() {
    updateFraisNotaireValue();
  }

  public void afterFraisAgenceModified() {
    updateFraisNotaireValue();
  }

  public void afterFraisNotaireModified() {
    //   updateFraisNotaireValue();
  }

  private void updateFraisNotaireValue() {
    fraisNotaireInput.getDocument().removeDocumentListener(this);
    double fraisNotaire = projetImmobilier.getRealEstate().getFraisNotaire();
    String fraisNotairesAffiches = String.valueOf(fraisNotaire);
    fraisNotaireInput.setText(fraisNotairesAffiches);
    fraisNotaireInput.getDocument().addDocumentListener(this);
  }

  @Override
  public void componentResized(ComponentEvent event) {
    if (event.getComponent() == this) {
      replaceWidgets();
    }
  }

}
